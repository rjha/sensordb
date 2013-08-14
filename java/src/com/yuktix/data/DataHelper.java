package com.yuktix.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.microsoft.windowsazure.services.core.storage.ResultContinuation;
import com.microsoft.windowsazure.services.core.storage.ResultSegment;
import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import com.microsoft.windowsazure.services.table.client.TableQuery;
import com.microsoft.windowsazure.services.table.client.TableResult;
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.query.PaginationParam;
import com.yuktix.dto.query.ScrollingParam;
import com.yuktix.dto.response.ResultSet;
import com.yuktix.util.BeanUtil;


public class DataHelper {
	
	public static String getGUID() throws Exception {

		String guid = UUID.randomUUID().toString();
		if(StringUtils.isBlank(guid)) {
			throw new Exception("GUID generation error: null");
		}
		
		// GUID width fixed at 36,left pad if less 
		String token = StringUtils.leftPad(guid, 36, "0");
		token = StringUtils.substring(guid,0,36);
		return token ;
	}
	
	public static HashMap<String,Object> getEntity(
			String table, 
			String partitionKey, 
			String rowKey,
			IDataResolver resolver) throws Exception {
		
		TableOperation operation = TableOperation.retrieve(partitionKey, rowKey, DynamicTableEntity.class);
		CloudTableClient client = Table.getInstance();
		TableResult result = client.execute(table, operation);
		 
		if( (result.getHttpStatusCode() == 404) 
				|| result.getProperties() == null
				|| result.getProperties().keySet().isEmpty()) {
			return null ;
		}
		
		HashMap<String,EntityProperty> columns = result.getProperties() ;
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Iterator<String> keys = columns.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			map.put(key, columns.get(key).getValueAsString());
		}
	
		if(resolver != null) {
			map = resolver.resolve(map);
		}
		
		return map ;
		
	}
	
	public static HashMap<String,Object> getEntity(String table, String partitionKey, String rowKey) throws Exception {
		return getEntity(table,partitionKey,rowKey,null); 
	}
	
	public static ResultSet getSegmentedResultSet(
			TableQuery<DynamicTableEntity> myQuery,
			ScrollingParam param) throws Exception {
		
		return getSegmentedResultSet(myQuery, param,null) ;
	}
	
	public static  ResultSet getResultSet(TableQuery<DynamicTableEntity> query, int size) throws Exception {
		
		HashMap<String, Object> datum;
		List<HashMap<String, Object>> series = new ArrayList<HashMap<String, Object>>();
		EntityProperty ep;
		int counter = 0;
		
		CloudTableClient client = Table.getInstance();
		Iterator<DynamicTableEntity> rows = client.execute(query).iterator();

		while (rows.hasNext()) {
			
			DynamicTableEntity row = rows.next();
			HashMap<String, EntityProperty> map = row.getProperties();

			datum = new HashMap<String, Object>();
			for (String key : map.keySet()) {
				ep = map.get(key);
				datum.put(key, ep.getValueAsString());
			}
			
			datum.put("server_ts", Long.toString(row.getTimestamp().getTime()));
			datum.put("row_key", row.getRowKey());
			series.add(datum);
			counter++;

			// local counter to mitigate azure lib iterator.next issue
			// azure lib will keep issuing the next requests for
			// rows.hasNext() call - effectively iterating over the
			// whole partition.

			if (counter >= size) {
				break;
			}

		}

		return new ResultSet(series,null) ;
	}
	
	
	public static ResultSet getSegmentedResultSet(
			TableQuery<DynamicTableEntity> myQuery,
			ScrollingParam param, IDataResolver resolver) throws Exception {
			
			
			ResultContinuation continuationToken = BeanUtil.getContinuationToken(param);
			PaginationParam pagination = new PaginationParam();
			
			if(continuationToken != null) {
				pagination.setLastPartition(param.getPartition());
				pagination.setLastRow(param.getRow());
				
			}
			
			CloudTableClient client = Table.getInstance();
			// segmented query
			ResultSegment<DynamicTableEntity> response = client.executeSegmented(myQuery, new MyEntityResolver(), continuationToken) ;
			// next continuation token
			continuationToken = response.getContinuationToken() ;
			
			if(continuationToken != null) {
				
				pagination.setNextPartition(continuationToken.getNextPartitionKey());
				pagination.setNextRow(continuationToken.getNextRowKey());
			}
			
			HashMap<String, Object> datum;
			List<HashMap<String, Object>> series = new ArrayList<HashMap<String, Object>>();
			DynamicTableEntity row;
			EntityProperty ep;
			
			Iterator<DynamicTableEntity>rows = response.getResults().iterator() ;
			
			while(rows.hasNext()) {
				row = rows.next() ;
				HashMap<String, EntityProperty> map = row.getProperties();

				datum = new HashMap<String, Object>();
				for (String key : map.keySet()) {
					ep = map.get(key);
					datum.put(key, ep.getValueAsString());
				}
				
				if(resolver != null) {
					datum = resolver.resolve(datum);
				}
				series.add(datum);
			}
			
			return new ResultSet(series,pagination) ;
	}
}
