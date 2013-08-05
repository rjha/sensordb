package com.yuktix.data;

import java.util.HashMap;
import java.util.UUID;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.provision.AccountParam;
import com.yuktix.dto.provision.ProjectParam;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;

public class Provision {
	
	public static String addAccount(AccountParam param) {
		try {

			// fixed partition key for accounts
			String partitionKey = "sensordb;account";
			String rowKey = param.getName() ;
			
			// azure stuff
			TableEntity entity ;
			
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			String guid = UUID.randomUUID().toString();
			data.put("accountId",new EntityProperty(guid));
			
			// @todo push GUID
			entity = new DynamicTableEntity(data);
			entity.setPartitionKey(partitionKey);
			entity.setRowKey(rowKey);
			TableOperation operation = TableOperation.insert(entity);
			
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation);
			
			return guid ;
			
		} catch (Exception ex) {
			Log.error("error in account provisioning", ex);
			throw new RestException("error in account provisioning");
		}
	}
	
	public static String addProject(ProjectParam param) {
		try {

			// fixed partition key for accounts
			String partitionKey = "sensordb;project";
			String rowKey = param.getName() ;
			
			// azure stuff
			TableEntity entity ;
			
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			data.put("accountId", new EntityProperty(param.getAccountId()));
			String guid = UUID.randomUUID().toString();
			data.put("projectId",new EntityProperty(guid));
			
			entity = new DynamicTableEntity(data);
			entity.setPartitionKey(partitionKey);
			entity.setRowKey(rowKey);
			TableOperation operation = TableOperation.insert(entity);
			
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation);
			
			return guid ;
		} catch (Exception ex) {
			Log.error("error in account provisioning", ex);
			throw new RestException("error in account provisioning");
		}
	}
	
}
