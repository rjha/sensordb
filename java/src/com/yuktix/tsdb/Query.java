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
import com.yuktix.dto.Sensor;

public class Query {

	public List<HashMap<String,String>> getLatest(Sensor sensor) {
		
		List<HashMap<String,String>> series ;
		
		try {
			
			CloudTableClient client = Table.getInstance().getConnection();
			String partitionKey = sensor.getProjectId() + ";"+ sensor.getSerialNumber();
			String where_condition = String.format("(PartitionKey eq '%s')",partitionKey);
			
			 TableQuery<DynamicTableEntity> myQuery = 
					 TableQuery.from("test", DynamicTableEntity.class)
					 .where(where_condition).take(5) ;
			 
			 Iterator<DynamicTableEntity> rows = client.execute(myQuery).iterator();
			 
			 HashMap<String,String> datum ;
			 series = new ArrayList<HashMap<String,String>>();
			 EntityProperty  ep ;
			 
			 while(rows.hasNext()) {
				 HashMap<String,EntityProperty> map = rows.next().getProperties();
				 datum = new HashMap<String,String>();
				 for (String key  : map.keySet())  {
					 ep = map.get(key);
					 datum.put(key, ep.getValueAsString());
				 }
				 
				 series.add(datum);
				  
			 }
			 
			 return series ;

		} catch (Exception ex) {
			series = null ;
			Log.error("error in tsdb query",ex);
			throw new RestException("error in sensordb query");
		}
	}
}
