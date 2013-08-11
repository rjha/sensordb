package com.yuktix.data;

import java.util.HashMap;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import com.microsoft.windowsazure.services.table.client.TableQuery;
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.provision.ProjectParam;
import com.yuktix.dto.query.ScrollingParam;
import com.yuktix.dto.response.ResultSet;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;
import com.yuktix.util.StringUtil;

public class Project {
	
	public static String add(ProjectParam param) {
		
		try {

			StringUtil.null_check("name",param.getName()) ;
			StringUtil.null_check("accountId",param.getAccountId()) ;
			
			String guidPartitionKey = "sensordb;project;guid";
			String namePartitionKey = "sensordb;project;name" ;
			String accountPartitionKey = "sensordb;project;account" ;
			
			String guid = Common.getGUID();
			String canonicalName = StringUtil.getCanonicalName(param.getName());
			
			TableEntity entity1,entity2,entity3 ;
						
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			data.put("accountId", new EntityProperty(param.getAccountId()));
			data.put("projectId",new EntityProperty(guid));
			
			entity1 = new DynamicTableEntity(data);
			entity1.setPartitionKey(guidPartitionKey);
			// guid should be unique
			entity1.setRowKey(guid);
			
			entity2 = new DynamicTableEntity(data);
			entity2.setPartitionKey(namePartitionKey);
			// two different accounts can have same project name
			// search by project name across accounts
			entity2.setRowKey(String.format("%s;%s",canonicalName,param.getAccountId()));
			
			entity3 = new DynamicTableEntity(data);
			entity3.setPartitionKey(accountPartitionKey);
			// search by accountId, sorted on name
			// 2 different projects can have same accountId
			entity3.setRowKey(String.format("%s;%s",param.getAccountId(),canonicalName));
			
			TableOperation operation1 = TableOperation.insert(entity1);
			TableOperation operation2 = TableOperation.insert(entity2);
			TableOperation operation3 = TableOperation.insert(entity3);
			
			CloudTableClient client = Table.getInstance();
			// @todo - how to pipeline multiple operations across partitions?
			client.execute("test", operation1);
			client.execute("test", operation2);
			client.execute("test", operation3);
			
			return guid ;
			
		} catch(RestException rex) {
			throw rex ;
		} catch (Exception ex) {
			
			Log.error(ex);
			throw new RestException("error adding new project");
		}
	}
	
	public static HashMap<String,String> getOnId(String guid) {
		
		try{
			
			String partitionKey = "sensordb;project;guid";
			HashMap<String,String> map = Common.getEntity("test",partitionKey,guid);
			return map ;
			
		} catch(RestException rex) {
			throw rex ;
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving project");
		}
	}
	
	public static ResultSet list(String accountId,ScrollingParam param) {
		
		try {
			
			String partitionKey = "sensordb;project;account" ;
			// @todo check .NET lexographical order
			String startKey = accountId + ";" ;
			String endKey = accountId + "=" ;
			
			String where_condition = String.format("(PartitionKey eq '%s') and (RowKey ge '%s') and (RowKey le '%s') ",partitionKey,startKey,endKey);
			
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(10);
			
			ResultSet result = Common.getSegmentedResultSet(myQuery,param);
			return result ;
			
		} catch(RestException rex) {
			throw rex ;
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving projects");
		}
	}
	
	
}
