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
import com.yuktix.cloud.azure.Table;
import com.yuktix.dto.SensorParam;

/* 
 * class to model TSDB queries
 * @see http://stackoverflow.com/questions/12623271/windows-azure-table-storage-take-issue
 * 
 */

public class Query {

	public List<HashMap<String,String>> getLatest(SensorParam param) {
		
		List<HashMap<String,String>> series ;
		
		try {
			
			String partitionKey = param.getProjectId() + ";"+ param.getSerialNumber();
			String where_condition = String.format("(PartitionKey eq '%s')",partitionKey);
			int size = (param.getSize() <= 0) ? 1 : param.getSize();
			
			CloudTableClient client = Table.getInstance().getConnection();
			TableQuery<DynamicTableEntity> myQuery = 
					 TableQuery.from("test", DynamicTableEntity.class)
					 .where(where_condition).take(size) ;
			 
			 Iterator<DynamicTableEntity> rows = client.execute(myQuery).iterator();
			 
			 HashMap<String,String> datum ;
			 series = new ArrayList<HashMap<String,String>>();
			 EntityProperty  ep ;
			
			 int counter = 0 ;
			 
			 while(rows.hasNext()) {
				 DynamicTableEntity row = rows.next();
				 HashMap<String,EntityProperty> map = row.getProperties();
				 
				 datum = new HashMap<String,String>();
				 for (String key  : map.keySet())  {
					 ep = map.get(key);
					 datum.put(key, ep.getValueAsString());
				 }
				 
				 series.add(datum); counter++ ;
				 
				 // local counter to mitigate azure lib iterator.next issue
				 // azure lib will keep issuing the next requests for 
				 // rows.hasNext() call - effectively iterating over the 
				 // whole partition.
				 
				 if(counter >= size ) { break ; }
				  
			 }
			 
			 return series ;

		} catch (Exception ex) {
			series = null ;
			Log.error("error in tsdb query",ex);
			throw new RestException("error in sensordb query");
		}
	}
}
