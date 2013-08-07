package com.yuktix.test.azure;

import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import com.microsoft.windowsazure.services.core.storage.*;
import com.microsoft.windowsazure.services.table.client.*;

// depends on common-lang;azure-api-0.4.4.jar
public class Table {

	public static void main(String[] args) {
		
		// Table.createTestTable();
		 Table.listTables();
		// Table.deleteTestTable();
	}

	public static void deleteTestTable() {

		try {

			ResourceBundle bundle = ResourceBundle.getBundle("sensordb",Locale.US);
			String accountName = bundle.getString("azure.account.name");
			String accountKey = bundle.getString("azure.account.key");

			StorageCredentials credentials = new StorageCredentialsAccountAndKey(accountName, accountKey);
			CloudStorageAccount storageAccount = new CloudStorageAccount(credentials);
			CloudTableClient tableClient = storageAccount.createCloudTableClient();
		
			CloudTable table = tableClient.getTableReference("test");
			table.deleteIfExists();
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void listTables() {
		try {

			ResourceBundle bundle = ResourceBundle.getBundle("sensordb",Locale.US);
			String accountName = bundle.getString("azure.account.name");
			String accountKey = bundle.getString("azure.account.key");

			StorageCredentials credentials = new StorageCredentialsAccountAndKey(accountName, accountKey);
			CloudStorageAccount storageAccount = new CloudStorageAccount(credentials);
			CloudTableClient tableClient = storageAccount.createCloudTableClient();	
			Iterator<String> tables = tableClient.listTables().iterator();
			
			System.out.println(" -------- Tables ------- " );
			while (tables.hasNext()) {
				System.out.println(" Table :: " + tables.next());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void createTestTable() {

		try {

			ResourceBundle bundle = ResourceBundle.getBundle("sensordb",Locale.US);
			String accountName = bundle.getString("azure.account.name");
			String accountKey = bundle.getString("azure.account.key");

			StorageCredentials credentials = new StorageCredentialsAccountAndKey(accountName, accountKey);
			CloudStorageAccount storageAccount = new CloudStorageAccount(credentials);
			CloudTableClient tableClient = storageAccount.createCloudTableClient();
			CloudTable table = tableClient.getTableReference("test");
			table.createIfNotExist();
			
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
