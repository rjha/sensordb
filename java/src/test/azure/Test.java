
package test.azure;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.bind.DatatypeConverter;

import com.microsoft.windowsazure.services.core.storage.*;
import com.microsoft.windowsazure.services.table.client.*;


public class Test {

	public static void main(String [] args) {
		Test test = new Test();
		// test.setup();
		test.query() ;
	}
	
	public void query() {
		String accountName = "sensordb" ;
		String accountKey = "n8lAH0pvJKUl25jHoSSWO25SKxwq7m+V0lvoao8gIy1Q0HalVhIP9FGuVU/7+yaQ+aKL3IzPfKkVdSJmNYRgMA=="  ;
		
		try {
			 System.out.println("point1 :: ");
			 StorageCredentials credentials = new StorageCredentialsAccountAndKey(accountName,accountKey);
			 CloudStorageAccount storageAccount = new CloudStorageAccount(credentials);
			 CloudTableClient tableClient = storageAccount.createCloudTableClient();
			 
			 TableQuery<DynamicTableEntity> myQuery = TableQuery.from("test", DynamicTableEntity.class)
					    .where("(PartitionKey eq 'rjha94@gmail.com')") ;
			 
			 Iterator<DynamicTableEntity> rows = tableClient.execute(myQuery).iterator();
			 while(rows.hasNext()) {
				 HashMap<String,EntityProperty> map = rows.next().getProperties();
				 System.out.println(map) ;
				  
			 }
			 
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setup() {
		String accountName = "sensordb" ;
		String accountKey = "n8lAH0pvJKUl25jHoSSWO25SKxwq7m+V0lvoao8gIy1Q0HalVhIP9FGuVU/7+yaQ+aKL3IzPfKkVdSJmNYRgMA=="  ;
		
		try {
			 System.out.println("point1 :: ");
			 StorageCredentials credentials = new StorageCredentialsAccountAndKey(accountName,accountKey);
			 CloudStorageAccount storageAccount = new CloudStorageAccount(credentials);
			 CloudTableClient tableClient = storageAccount.createCloudTableClient();
			 CloudTable table = new CloudTable("test",tableClient);
			 table.createIfNotExist();
			  
			 System.out.println("point2 :: ");
			 Iterator<String> tables = tableClient.listTables().iterator();
			 while(tables.hasNext()) {
				 System.out.println("Table :: "+ tables.next());
			 }
			 
			 System.out.println("point3 :: ");
			 
			 HashMap<String,EntityProperty> data = new HashMap<String,EntityProperty>();
			 data.put("name", new EntityProperty("Rajeev"));
			 data.put("email", new EntityProperty("rjha94@gmail.com"));
			 data.put("address", new EntityProperty("Bangalore"));
			 
			 TableEntity entity = new DynamicTableEntity(data);
			 entity.setPartitionKey("rjha94@gmail.com");
			 entity.setRowKey("Rajeev");
			 
			 
			 TableOperation top = TableOperation.insert(entity);
			 tableClient.execute("test", top);
			 
			 
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	
	}
	
}
