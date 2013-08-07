package com.yuktix.data;

import java.util.Date;
import java.util.HashMap;

import com.microsoft.windowsazure.services.core.storage.StorageException;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.EntityResolver;

public class MyEntityResolver implements EntityResolver<DynamicTableEntity>{
	
	
	public DynamicTableEntity resolve(String partitionKey,
	        String rowKey,
	        Date timeStamp,
	        HashMap<String,EntityProperty> properties,
	        String etag)
	          throws StorageException {
		
		DynamicTableEntity entity = new DynamicTableEntity();
		entity.setProperties(properties);
		return entity ; 
	}
}
