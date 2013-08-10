package com.yuktix.tsdb;

import java.util.HashMap;
import java.util.Iterator;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableBatchOperation;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.yuktix.dto.NameValuePair;
import com.yuktix.dto.provision.Reading;
import com.yuktix.dto.tsdb.DataPointParam;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;
import com.yuktix.util.AzureUtil;
import com.yuktix.cloud.azure.Table;

public class Store {

	
	public void addDataPoint(DataPointParam dp) {
		
		try {

			// partition key is fixed
			String partitionKey = dp.getProjectId() + ";"+ dp.getSerialNumber();
			String rowKey = null ;
			
			// meta data
			NameValuePair nvp ;
			Iterator<NameValuePair> nvps ;
			// sensor readings
			Iterator<Reading> readings ;
			
			// DataPoint has multiple readings
			// store each reading in table
			readings = dp.getReadings().iterator();
			Reading reading ;
			
			// azure stuff
			HashMap<String, EntityProperty> data ;
			TableEntity entity ;
			TableBatchOperation operation = new TableBatchOperation();
			
			// get rollup buckets for this project
			Rollup rollup = new Rollup(dp.getProjectId());
			RollupOperation oprollup = new RollupOperation();
			
			while (readings.hasNext()) {
				reading = readings.next();
				data = new HashMap<String, EntityProperty>();
				
				// @todo - Add to alerts table if above threshold
				// @todo - Add to rollup table
				data.put(reading.getName(), new EntityProperty(reading.getValue()));
				data.put("client_ts", new EntityProperty(reading.getTimestamp()));
				
				// fixed meta data for one batch of sensor readings
				nvps = dp.getMetaData().iterator();
				while (nvps.hasNext()) {
					nvp = nvps.next();
					data.put(nvp.getName(), new EntityProperty(nvp.getValue()));
				}
				
				rowKey = AzureUtil.ticks();
				entity = new DynamicTableEntity(data);
				entity.setPartitionKey(partitionKey);
				entity.setRowKey(rowKey);
				// batch operation constraints
				// # same PK 
				// # 100 entities / 4MB 
				// # one entity - can include once only
				operation.insert(entity);
				
				// Add to rollup operation
				oprollup.insert(reading.getName(),reading.getValue());
				
			}
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation);
			rollup.execute(oprollup);
			
		} catch (Exception ex) {
			Log.error("error in tsdb store",ex);
			throw new RestException("error adding new data point");
		}
	}
}
