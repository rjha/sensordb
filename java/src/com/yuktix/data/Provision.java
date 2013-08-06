package com.yuktix.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.microsoft.windowsazure.services.core.storage.ResultContinuation;
import com.microsoft.windowsazure.services.core.storage.ResultSegment;
import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableBatchOperation;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import com.microsoft.windowsazure.services.table.client.TableQuery;
import com.microsoft.windowsazure.services.table.client.TableResult;
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.provision.AccountParam;
import com.yuktix.dto.provision.ProjectParam;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;

public class Provision {
	
	public static String addAccount(AccountParam param) {
		try {

			// fixed partition key for accounts
			// azure will raise error for duplicate
			// [partition key + row key]
			// so guid as well as name is guaranteed to be unique
			
			String partitionKey = "sensordb;account";
			String guid = UUID.randomUUID().toString();
			
			// azure stuff
			TableEntity entity1, entity2 ;
			
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			data.put("accountId",new EntityProperty(guid));
			
			entity1 = new DynamicTableEntity(data);
			entity1.setPartitionKey(partitionKey);
			entity1.setRowKey(param.getName());
			
			entity2 = new DynamicTableEntity(data);
			entity2.setPartitionKey(partitionKey);
			entity2.setRowKey(guid);
			
			TableBatchOperation operation = new TableBatchOperation();
			operation.insert(entity1);
			operation.insert(entity2);
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation);
			
			return guid ;
			
		} catch (Exception ex) {
			Log.error(ex);
			throw new RestException("error adding new account");
		}
	}
	
	public static String addProject(ProjectParam param) {
		try {

			// fixed partition key for accounts
			String partitionKey = "sensordb;project";
			String guid = UUID.randomUUID().toString();
			
			// azure stuff
			TableEntity entity1,entity2 ;
						
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			data.put("accountId", new EntityProperty(param.getAccountId()));
			data.put("projectId",new EntityProperty(guid));
			
			entity1 = new DynamicTableEntity(data);
			entity1.setPartitionKey(partitionKey);
			entity1.setRowKey(param.getName());
			
			entity2 = new DynamicTableEntity(data);
			entity2.setPartitionKey(partitionKey);
			entity2.setRowKey(guid);
			
			TableBatchOperation operation = new TableBatchOperation();
			operation.insert(entity1);
			operation.insert(entity2);
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation);
			
			return guid ;
			
		} catch (Exception ex) {
			
			Log.error(ex);
			throw new RestException("error adding new project");
		}
	}
	
	public static HashMap<String,String> getEntity(String table, String partitionKey, String rowKey) throws Exception {
		
		TableOperation operation = TableOperation.retrieve(partitionKey, rowKey, DynamicTableEntity.class);
		CloudTableClient client = Table.getInstance();
		TableResult result = client.execute(table, operation);
		 
		if( (result.getHttpStatusCode() == 404) 
				|| result.getProperties() == null
				|| result.getProperties().keySet().isEmpty()) {
			return null ;
		}
		
		HashMap<String,EntityProperty> columns = result.getProperties() ;
		HashMap<String,String> map = new HashMap<String,String>();
		
		Iterator<String> keys = columns.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			map.put(key, columns.get(key).getValueAsString());
		}
	
		return map ;
		
	}
	
	public static HashMap<String,String> getProjectOnId(String guid) {
		
		try{
			
			String partitionKey = "sensordb;project";
			HashMap<String,String> map = getEntity("test",partitionKey,guid);
			return map ;
			
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving project details");
		}
	}
	
	public static HashMap<String,String> getAccountOnId(String guid) {
		
		try{
			
			String partitionKey = "sensordb;account";
			HashMap<String,String> map = getEntity("test",partitionKey,guid);
			return map ;
			
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving account details");
		}
	}

	public static List<HashMap<String, String>> getProjects() {
		
		try {
			
			// segmented query to fetch 25 at a time
			CloudTableClient client = Table.getInstance();
			String partitionKey = "sensordb;project" ;
			String where_condition = String.format("(PartitionKey eq '%s')",partitionKey);
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(25);
			
			ResultContinuation continuationToken = new ResultContinuation();
			ResultSegment<DynamicTableEntity> response = client.executeSegmented(myQuery, null) ;
			
			
			continuationToken = response.getContinuationToken() ;
			if(continuationToken != null)
				System.out.println("marker :" + continuationToken.getNextMarker());
			
			HashMap<String, String> datum;
			List<HashMap<String, String>> series = new ArrayList<HashMap<String, String>>();
			DynamicTableEntity row;
			EntityProperty ep;
			
			Iterator<DynamicTableEntity>rows = response.getResults().iterator() ;
			
			while(rows.hasNext()) {
				row = rows.next() ;
				HashMap<String, EntityProperty> map = row.getProperties();

				datum = new HashMap<String, String>();
				for (String key : map.keySet()) {
					ep = map.get(key);
					datum.put(key, ep.getValueAsString());
				}
				
				series.add(datum);
			}
			
			return series ;
			
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving project list");
		}
	}
	
	public static List<HashMap<String, String>> getAccounts() {
		
		try {
			
			// segmented query to fetch 25 at a time
			CloudTableClient client = Table.getInstance();
			String partitionKey = "sensordb;account" ;
			String where_condition = String.format("(PartitionKey eq '%s')",partitionKey);
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(25);
			
			ResultContinuation continuationToken = new ResultContinuation();
			ResultSegment<DynamicTableEntity> response = client.executeSegmented(myQuery, null) ;
			
			
			continuationToken = response.getContinuationToken() ;
			if(continuationToken != null)
				System.out.println("marker :" + continuationToken.getNextMarker());
			
			HashMap<String, String> datum;
			List<HashMap<String, String>> series = new ArrayList<HashMap<String, String>>();
			DynamicTableEntity row;
			EntityProperty ep;
			
			Iterator<DynamicTableEntity>rows = response.getResults().iterator() ;
			
			while(rows.hasNext()) {
				row = rows.next() ;
				HashMap<String, EntityProperty> map = row.getProperties();

				datum = new HashMap<String, String>();
				for (String key : map.keySet()) {
					ep = map.get(key);
					datum.put(key, ep.getValueAsString());
				}
				
				series.add(datum);
			}
			
			return series ;
			
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving project list");
		}
	}
}
