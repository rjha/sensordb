package com.yuktix.data;

import java.util.HashMap;
import java.util.Iterator;

import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.microsoft.windowsazure.services.table.client.DynamicTableEntity;
import com.microsoft.windowsazure.services.table.client.EntityProperty;
import com.microsoft.windowsazure.services.table.client.TableBatchOperation;
import com.microsoft.windowsazure.services.table.client.TableEntity;
import com.microsoft.windowsazure.services.table.client.TableQuery;
import com.yuktix.dto.provision.Reading;
import com.yuktix.dto.query.SensorQueryParam;
import com.yuktix.dto.response.ResultSet;
import com.yuktix.dto.tsdb.DataPointParam;
import com.yuktix.rest.exception.RestException;

import com.yuktix.util.Log;
import com.yuktix.util.AzureUtil;
import com.yuktix.cloud.azure.Table;


/* 
 * class to model TSDB queries
 * @see http://stackoverflow.com/questions/12623271/windows-azure-table-storage-take-issue
 * 
 */

public class SensorTSDB {

	
	public static void add(DataPointParam dp) {
		
		try {

			// @todo projectId + serial number check
			// partition key is fixed
			String partitionKey = dp.getProjectId() + ";"+ dp.getSerialNumber();
			String rowKey = null ;
			
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
			
			while (readings.hasNext()) {
				reading = readings.next();
				data = new HashMap<String, EntityProperty>();
				
				// @todo - Add to alerts table if above threshold
				// @todo - Add to rollup table
				data.put(reading.getName(), new EntityProperty(reading.getValue()));
				data.put("client_ts", new EntityProperty(reading.getTimestamp()));
				
				rowKey = AzureUtil.ticks();
				entity = new DynamicTableEntity(data);
				entity.setPartitionKey(partitionKey);
				entity.setRowKey(rowKey);
				// batch operation constraints
				// # same PK 
				// # 100 entities / 4MB 
				// # one entity - can include once only
				operation.insert(entity);
				
			}
			
			CloudTableClient client = Table.getInstance();
			client.execute("test", operation);
			
		} catch(RestException rex) {
			throw rex ;
		
		} catch (Exception ex) {
			Log.error("error in tsdb store",ex);
			throw new RestException("error adding new data point");
		}
	}
	
	public static ResultSet getLatest(SensorQueryParam param) {
		
		try {
			
			// @todo fetch meta data for this sensor
			String partitionKey = param.getProjectId() + ";"+ param.getSerialNumber();
			String where_condition = String.format("(PartitionKey eq '%s')",partitionKey);
			int size = (param.getSize() <= 0) ? 1 : param.getSize();
			
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(size);
			
			
			ResultSet result = DataHelper.getResultSet(myQuery,size);
			return result ;
			
		} catch(RestException rex) {
			throw rex ;
			
		} catch (Exception ex) {
			Log.error("error in tsdb store",ex);
			throw new RestException("error in data query");
		}
	}
	
	public static ResultSet query(SensorQueryParam param) {
		
		try {
		
			String startRowKey = AzureUtil.ticks(param.getTimeSlice().getStartTS());
			String endRowKey = AzureUtil.ticks(param.getTimeSlice().getEndTS());
			int size = 100 ;
			
			String partitionKey = param.getProjectId() + ";"+ param.getSerialNumber();
			// end row key is smaller (recent ticks are smaller)
			String where_condition = 
					String.format("(PartitionKey eq '%s') and (RowKey le '%s') and (RowKey ge '%s') ",partitionKey,startRowKey,endRowKey);
			
			TableQuery<DynamicTableEntity> myQuery = TableQuery
					.from("test", DynamicTableEntity.class)
					.where(where_condition).take(size);
			
			ResultSet result = DataHelper.getResultSet(myQuery,size);
			return result ;
			
		} catch(RestException rex) {
			throw rex ;

		} catch (Exception ex) {
			
			Log.error("error in tsdb query", ex);
			throw new RestException("error in data query");
		}
		
	}
}
