package com.yuktix.cloud.azure;

import java.util.Locale;
import java.util.ResourceBundle;

import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount;
import com.microsoft.windowsazure.services.core.storage.StorageCredentials;
import com.microsoft.windowsazure.services.core.storage.StorageCredentialsAccountAndKey;
import com.microsoft.windowsazure.services.table.client.CloudTableClient;
import com.yuktix.util.Log;

public class Table {

	private static final Table _instance = new Table();
	private CloudTableClient client;

	public static Table getInstance() {
		return _instance;
	}

	public CloudTableClient getConnection() throws Exception {
		// is client null?
		if(client == null) {
			// @todo - when client is null, all subsequent requests will return error
			// we would like to build a retry-logic inside this client after a timeout
			// @todo read error messages from a bundle
			throw new Exception("error :: azure cloud table client is not initialized") ;
		}
		
		return client;
	}

	public Table() {
		boolean done = false ;
		
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("sensordb",Locale.US);
			String accountName = bundle.getString("azure.account.name");
			String accountKey = bundle.getString("azure.account.key");
			StorageCredentials credentials = new StorageCredentialsAccountAndKey(accountName, accountKey);
			CloudStorageAccount storageAccount = new CloudStorageAccount(credentials);
			this.client = storageAccount.createCloudTableClient();
			done = true ;
			
		} catch (Exception ex) {
			this.client = null; 
			// log exceptions
			String message  = "Error initializing Azure table service" ;
			Log.error(message, ex);

		} finally {
			if(!done)
				this.client = null;
		}
	}

}
