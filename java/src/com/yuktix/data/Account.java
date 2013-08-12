package com.yuktix.data;

import java.util.HashMap;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import com.microsoft.windowsazure.services.table.client.TableQuery;
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.provision.AccountParam;
import com.yuktix.dto.query.ScrollingParam;
import com.yuktix.dto.response.ResultSet;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;
import com.yuktix.util.StringUtil;

public class Account {
	
	public static String add(AccountParam param) {
		try {

			StringUtil.null_check("name",param.getName()) ;
			
			// fixed partition key for accounts
			// azure will raise error for duplicate
			// [partition key + row key]
			// insert 2 entities 
			// first is optimized for query by guid
			// second is optimized for query by name
			
			String guidPartitionKey = "sensordb;account;guid";
			String namePartitionKey = "sensordb;account;name" ;
			
			String guid = Common.getGUID();
			String canonicalName = StringUtil.getCanonicalName(param.getName()) ;
			
			// azure stuff
			TableEntity entity1, entity2 ;
			
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			data.put("accountId",new EntityProperty(guid));
			
			entity1 = new DynamicTableEntity(data);
			entity1.setPartitionKey(namePartitionKey);
			entity1.setRowKey(canonicalName);
			TableOperation operation1 = TableOperation.insert(entity1);
			
			entity2 = new DynamicTableEntity(data);
			entity2.setPartitionKey(guidPartitionKey);
			entity2.setRowKey(guid);
			TableOperation operation2 = TableOperation.insert(entity2);
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation1);
			client.execute("test", operation2);
			
			return guid ;
			
		} catch(RestException rex) {
			throw rex ;
		} catch (Exception ex) {
			Log.error(ex);
			throw new RestException("error adding new account");
		}
	}
	
	public static HashMap<String,Object> getOnId(String guid) {
		
		try{
			
			String partitionKey = "sensordb;account;guid";
			HashMap<String,Object> map = Common.getEntity("test",partitionKey,guid);
			return map ;
			
		} catch(RestException rex) {
			throw rex ;
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving account");
		}
	}
	
	public static ResultSet list(ScrollingParam param) {
		
		try {
			
			String partitionKey = "sensordb;account;name" ;
			String where_condition = String.format("(PartitionKey eq '%s') ",partitionKey);
			
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(10);
			
			ResultSet result = Common.getSegmentedResultSet(myQuery,param);
			return result ;
			
		} catch(RestException rex) {
			throw rex ;
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving accounts");
		}
	}
	
}
