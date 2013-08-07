package com.yuktix.data;

import java.util.HashMap;
import java.util.Iterator;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import com.microsoft.windowsazure.services.table.client.TableResult;
import com.yuktix.cloud.azure.Table;

public class Common {
	
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
}
