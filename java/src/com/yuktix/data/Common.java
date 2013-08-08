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
import com.yuktix.dto.query.ResultSet;
import com.yuktix.dto.query.ScrollingParam;
import com.yuktix.util.BeanUtil;


public class Common {
	
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
	
	public static ResultSet getSegmentedResultSet(
			TableQuery<DynamicTableEntity> myQuery,
			ScrollingParam param) throws Exception {
			
			
			ResultContinuation continuationToken = BeanUtil.getContinuationToken(param);
			PaginationParam pagination = new PaginationParam();
			
			if(continuationToken != null) {
				pagination.setPrevious_partition(param.getPartition_key());
				pagination.setPrevious_row(param.getRow_key());
				
			}
			
			CloudTableClient client = Table.getInstance();
			// segmented query
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
	}
}
