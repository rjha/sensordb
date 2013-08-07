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
import com.microsoft.windowsazure.services.table.client.TableQuery;
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.provision.AccountParam;
import com.yuktix.dto.query.PaginationParam;
import com.yuktix.dto.query.ResultSet;
import com.yuktix.dto.query.ScrollingParam;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.BeanUtil;
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
			
			String partitionKey = "sensordb;account";
			String guid = UUID.randomUUID().toString();
			String guidRowKey = "guid;" + guid ;
			String nameRowKey = "name;" + StringUtil.getCanonicalName(param.getName()) ;
			
			// azure stuff
			TableEntity entity1, entity2 ;
			
			HashMap<String, EntityProperty> data =  new HashMap<String, EntityProperty>();
			data.put("name", new EntityProperty(param.getName()));
			data.put("accountId",new EntityProperty(guid));
			
			entity1 = new DynamicTableEntity(data);
			entity1.setPartitionKey(partitionKey);
			entity1.setRowKey(nameRowKey);
			
			entity2 = new DynamicTableEntity(data);
			entity2.setPartitionKey(partitionKey);
			entity2.setRowKey(guidRowKey);
			
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
	
	public static HashMap<String,String> getOnId(String guid) {
		
		try{
			
			String partitionKey = "sensordb;account";
			String rowKey = "guid;" + guid ;
			HashMap<String,String> map = Common.getEntity("test",partitionKey,rowKey);
			return map ;
			
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving account");
		}
	}
	
	public static ResultSet list(ScrollingParam param) {
		
		try {
			
			// segmented query
			CloudTableClient client = Table.getInstance();
			String partitionKey = "sensordb;account" ;
			String where_condition = String.format("(PartitionKey eq '%s') and (RowKey gt '%s') ",partitionKey, "guid;");
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(10);
			
			ResultContinuation continuationToken = BeanUtil.getContinuationToken(param);
			PaginationParam pagination = new PaginationParam();
			
			if(continuationToken != null) {
				pagination.setPrevious_partition(param.getPartition_key());
				pagination.setPrevious_row(param.getRow_key());
				
			}
			
			ResultSegment<DynamicTableEntity> response = client.executeSegmented(myQuery, new MyEntityResolver(), continuationToken) ;
			// next continuation token
			continuationToken = response.getContinuationToken() ;
			
			if(continuationToken != null) {
				
				pagination.setNext_partition(continuationToken.getNextPartitionKey());
				pagination.setNext_row(continuationToken.getNextRowKey());
			}
			
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
			
			return new ResultSet(series,pagination) ;
			
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error retrieving accounts");
		}
	}
	
}
