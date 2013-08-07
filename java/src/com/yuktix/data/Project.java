package com.yuktix.data;

import java.util.HashMap;
import java.util.UUID;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableBatchOperation;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.provision.ProjectParam;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;
import com.yuktix.util.StringUtil;

public class Project {
	
	public static String add(ProjectParam param) {
		try {

			// fixed partition key for accounts
			String partitionKey = "sensordb;project";
			String guid = UUID.randomUUID().toString();
			String guidRowKey = "guid;" + guid ;
			String nameRowKey = "name;" + StringUtil.getCanonicalName(param.getName());
			String accountRowKey = "account;" + param.getAccountId();
			
					
			// azure stuff
			TableEntity entity1,entity2,entity3 ;
						
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			data.put("accountId", new EntityProperty(param.getAccountId()));
			data.put("projectId",new EntityProperty(guid));
			
			entity1 = new DynamicTableEntity(data);
			entity1.setPartitionKey(partitionKey);
			entity1.setRowKey(guidRowKey);
			
			entity2 = new DynamicTableEntity(data);
			entity2.setPartitionKey(partitionKey);
			entity2.setRowKey(nameRowKey);
			
			entity3 = new DynamicTableEntity(data);
			entity3.setPartitionKey(partitionKey);
			entity3.setRowKey(accountRowKey);
			
			TableBatchOperation operation = new TableBatchOperation();
			operation.insert(entity1);
			operation.insert(entity2);
			operation.insert(entity3);
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation);
			
			return guid ;
			
		} catch (Exception ex) {
			
			Log.error(ex);
			throw new RestException("error adding new project");
		}
	}
	
	public static HashMap<String,String> getOnId(String guid) {
		
		try{
			
			String partitionKey = "sensordb;project";
			String rowKey = "guid;" + guid ;
			
			HashMap<String,String> map = Common.getEntity("test",partitionKey,rowKey);
			return map ;
			
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving project");
		}
	}
}
