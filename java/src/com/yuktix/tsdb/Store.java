package com.yuktix.tsdb;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import com.yuktix.dto.DataPoint;
import com.yuktix.dto.NameValuePair;
import com.yuktix.dto.Reading;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;
import com.yuktix.cloud.azure.Table;

public class Store {

	public void addDataPoint(DataPoint dp) {
		try {
			// store DataPoint in azure table
			long d_max = 9223372036854775807L;
			// Date() defaults to GMT
			long d_now = new Date().getTime();

			String rowKey = String.format("%019d", (d_max - d_now));
			String partitionKey = dp.getProjectId() + ";"+ dp.getSerialNumber();
			
			HashMap<String, EntityProperty> data = new HashMap<String, EntityProperty>();
			Iterator<Reading> readings = dp.getReadings().iterator();
			Reading reading ;
			
			//readings
			while (readings.hasNext()) {
				reading = readings.next();
				data.put(reading.getName(), new EntityProperty(reading.getValue()));
				data.put("timestamp", new EntityProperty(reading.getTimestamp()));
			}

			//meta data
			Iterator<NameValuePair> nvps = dp.getMetaData().iterator();
			NameValuePair nvp ;
			while (nvps.hasNext()) {
				nvp = nvps.next();
				data.put(nvp.getName(), new EntityProperty(nvp.getValue()));
			}

			TableEntity entity = new DynamicTableEntity(data);
			entity.setPartitionKey(partitionKey);
			entity.setRowKey(rowKey);

			TableOperation top = TableOperation.insert(entity);
			CloudTableClient client = Table.getInstance().getConnection();
			client.execute("test", top);

		} catch (Exception ex) {
			Log.error("error in tsdb store",ex);
			throw new RestException("error adding new data point");
		}
	}
}
