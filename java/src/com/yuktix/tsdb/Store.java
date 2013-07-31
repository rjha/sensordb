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
import com.yuktix.cloud.azure.Table;

public class Store {

	public void addDataPoint(DataPoint dp) {
		try {
			// store DataPoint in azure table
			long d_max = 9223372036854775807L;
			// Date() returns in UTC only
			long d_now = new Date().getTime();

			String rowKey = String.format("%019d", (d_max - d_now));
			String partitionKey = dp.getProjectId() + ";"+ dp.getSerialNumber();
			
			HashMap<String, EntityProperty> data = new HashMap<String, EntityProperty>();
			Iterator<Reading> readings = dp.getReadings().iterator();

			while (readings.hasNext()) {
				data.put(readings.next().getName(), new EntityProperty(readings
						.next().getValue()));
				data.put("timestamp", new EntityProperty(readings.next()
						.getTimestamp()));
			}

			Iterator<NameValuePair> nvps = dp.getMetaData().iterator();
			while (nvps.hasNext()) {
				data.put(nvps.next().getName(), new EntityProperty(nvps.next()
						.getValue()));
			}

			TableEntity entity = new DynamicTableEntity(data);
			entity.setPartitionKey(partitionKey);
			entity.setRowKey(rowKey);

			TableOperation top = TableOperation.insert(entity);
			CloudTableClient client = Table.getInstance().getConnection();
			client.execute("test", top);

		} catch (Exception ex) {
			throw new RestException("error adding new data point");
		}
	}
}
