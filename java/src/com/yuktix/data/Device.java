package com.yuktix.data;

import java.util.HashMap;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import com.microsoft.windowsazure.services.table.client.TableQuery;
import com.yuktix.cloud.azure.Table;
import com.yuktix.data.resolver.DeviceResolver;
import com.yuktix.dto.provision.DeviceParam;
import com.yuktix.dto.query.ScrollingParam;
import com.yuktix.dto.response.ResultSet;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.BeanUtil;
import com.yuktix.util.Log;
import com.yuktix.util.StringUtil;

public class Device {
	
	public static String add(DeviceParam param) {
		try {

			StringUtil.null_check("name",param.getName()) ;
			StringUtil.null_check("accountId",param.getAccountId()) ;
			
			String guidPartitionKey = "sensordb;device;guid";
			String namePartitionKey = "sensordb;device;name" ;
			String accountPartitionKey = "sensordb;device;account" ;
			
			
			String guid = Common.getGUID();
			String canonicalName = StringUtil.getCanonicalName(param.getName()) ;
			
			// azure stuff
			TableEntity entity1, entity2, entity3 ;
			
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			data.put("manufacturer", new EntityProperty(param.getManufacturer()));
			data.put("description", new EntityProperty(param.getDescription()));
			
			
			// Entity property can handle only few data types
			// we need to flatten the variables as "string" before
			// pushing them to azure tables
			
			String variables = BeanUtil.jsonEncode(param.getVariables()) ;
			data.put("variables", new EntityProperty(variables));
			
			data.put("deviceId",new EntityProperty(guid));
			data.put("accountId",new EntityProperty(param.getAccountId()));
			
			entity1 = new DynamicTableEntity(data);
			entity1.setPartitionKey(guidPartitionKey);
			entity1.setRowKey(guid);
			TableOperation operation1 = TableOperation.insert(entity1);
			
			entity2 = new DynamicTableEntity(data);
			entity2.setPartitionKey(namePartitionKey);
			entity2.setRowKey(String.format("%s;%s",canonicalName,param.getAccountId()));
			TableOperation operation2 = TableOperation.insert(entity2);
			
			entity3 = new DynamicTableEntity(data);
			entity3.setPartitionKey(accountPartitionKey);
			entity3.setRowKey(String.format("%s;%s",param.getAccountId(),canonicalName));
			TableOperation operation3 = TableOperation.insert(entity3);
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation1);
			client.execute("test", operation2);
			client.execute("test", operation3);
			
			return guid ;
			
		} catch(RestException rex) {
			throw rex ;
		} catch (Exception ex) {
			Log.error(ex);
			throw new RestException("error adding new device");
		}
	}
	
	public static HashMap<String,Object> getOnId(String guid) {
		
		try{
			
			String partitionKey = "sensordb;device;guid";
			IDataResolver resolver = new DeviceResolver();
			HashMap<String,Object> map = Common.getEntity("test",partitionKey,guid,resolver);
			
			return map ;
			
		} catch(RestException rex) {
			throw rex ;
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving device");
		}
	}
	
	public static ResultSet list(String accountId,ScrollingParam param) {
		
		try {
			
			String partitionKey = "sensordb;device;account" ;
			// @todo check .NET lexographical order
			String startKey = accountId + ";" ;
			String endKey = accountId + "=" ;
			
			String where_condition = String.format("(PartitionKey eq '%s') and (RowKey ge '%s') and (RowKey le '%s') ",partitionKey,startKey,endKey);
			
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(10);
			
			IDataResolver resolver = new DeviceResolver();
			ResultSet result = Common.getSegmentedResultSet(myQuery,param,resolver);
			return result ;
			
		} catch(RestException rex) {
			throw rex ;
			
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving devices");
		}
	}
	
	
}
