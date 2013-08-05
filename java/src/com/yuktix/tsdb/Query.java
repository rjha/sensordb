package com.yuktix.tsdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableQuery;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;
import com.yuktix.util.TimeUtil;
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.query.SensorParam;

/* 
 * class to model TSDB queries
 * @see http://stackoverflow.com/questions/12623271/windows-azure-table-storage-take-issue
 * 
 */

public class Query {

	private List<HashMap<String, String>> getRows(TableQuery<DynamicTableEntity> query, int size) throws Exception {
		HashMap<String, String> datum;
		List<HashMap<String, String>> series = new ArrayList<HashMap<String, String>>();
		EntityProperty ep;
		int counter = 0;
		
		CloudTableClient client = Table.getInstance();
		Iterator<DynamicTableEntity> rows = client.execute(query).iterator();

		while (rows.hasNext()) {
			
			DynamicTableEntity row = rows.next();
			HashMap<String, EntityProperty> map = row.getProperties();

			datum = new HashMap<String, String>();
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

		return series;
	}
	
	public List<HashMap<String, String>> getDataPoint(SensorParam param) {

		List<HashMap<String, String>> series;

		try {
			
			String partitionKey = param.getProjectId() + ";"+ param.getSerialNumber();
			String where_condition = String.format("(PartitionKey eq '%s')",partitionKey);
			int size = (param.getSize() <= 0) ? 1 : param.getSize();
			
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(size);

			
			series = getRows(myQuery,size);
			return series ;

		} catch (Exception ex) {
			series = null;
			Log.error("error in tsdb query", ex);
			throw new RestException("error in sensordb query");
		}
	}

	public List<HashMap<String, String>> getInTimeSlice(SensorParam param) {
		
		List<HashMap<String, String>> series;
		
		try {
		
			String startRowKey = TimeUtil.ticks(param.getTime_slice().getStartTS());
			String endRowKey = TimeUtil.ticks(param.getTime_slice().getEndTS());
			int size = 100 ;
			
			String partitionKey = param.getProjectId() + ";"+ param.getSerialNumber();
			// end row key is smaller (recent ticks are smaller)
			String where_condition = 
					String.format("(PartitionKey eq '%s') and (RowKey le '%s') and (RowKey ge '%s') ",partitionKey,startRowKey,endRowKey);
			
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(size);
			
			series = getRows(myQuery,size);
			return series ;

		} catch (Exception ex) {
			series = null;
			Log.error("error in tsdb query", ex);
			throw new RestException("error in sensordb query");
		}
		
	}

}
