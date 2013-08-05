package com.yuktix.cloud.azure;

import java.util.Locale;
import java.util.ResourceBundle;

import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount;
import com.microsoft.windowsazure.services.core.storage.StorageCredentials;
import com.microsoft.windowsazure.services.core.storage.StorageCredentialsAccountAndKey;
import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.yuktix.exception.ServiceIOException;
import com.yuktix.util.Log;

public class Table {
	
	/*
	 * As per MSDN - instance members of CloudStorageAccount and CloudTableClient
	 * are not thread safe. We should not share an static instance of resources.
	 * The downside is performance overhead of creation on each thread
	 * (which is said to be negligible as each request will open an HTTP connection
	 * anyway)
	 * 
	 * 
	 */
	public static CloudTableClient getInstance() throws ServiceIOException {
		
		try {
			
			ResourceBundle bundle = ResourceBundle.getBundle("sensordb",Locale.US);
			String accountName = bundle.getString("azure.account.name");
			String accountKey = bundle.getString("azure.account.key");
			StorageCredentials credentials = new StorageCredentialsAccountAndKey(accountName, accountKey);
			CloudStorageAccount storageAccount = new CloudStorageAccount(credentials);
			CloudTableClient client = storageAccount.createCloudTableClient();
			return client ;
			
		} catch (Exception ex) {
			
			// log exceptions
			String message  = "Error initializing Azure table service" ;
			Log.error(message, ex);
			throw new ServiceIOException(message);

		}
	}
	
}
