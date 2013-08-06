package com.yuktix.test.azure;

import com.microsoft.windowsazure.services.core.Configuration;
import com.microsoft.windowsazure.services.core.ServiceException;
import com.microsoft.windowsazure.services.serviceBus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.serviceBus.ServiceBusContract;
import com.microsoft.windowsazure.services.serviceBus.ServiceBusService;
import com.microsoft.windowsazure.services.serviceBus.models.BrokeredMessage;
import com.microsoft.windowsazure.services.serviceBus.models.ReceiveMessageOptions;
import com.microsoft.windowsazure.services.serviceBus.models.ReceiveMode;
import com.microsoft.windowsazure.services.serviceBus.models.ReceiveQueueMessageResult;

public class ServiceBusConsumer {
	
	public static void main(String[] args) throws ServiceException {
		
		Configuration config = 
			    ServiceBusConfiguration.configureWithWrapAuthentication(
			      "yuktix-ns",
			      "owner",
			      "bwb8rfwIX/oAD8Wgh5LNNNuWBwxIo/CsgQidCoJOajw=",
			      ".servicebus.windows.net",
			      "-sb.accesscontrol.windows.net/WRAPv0.9");
	
			ServiceBusContract service = ServiceBusService.create(config);
		
		ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
		opts.setReceiveMode(ReceiveMode.PEEK_LOCK);
		
		while(true)
		{ 
		     ReceiveQueueMessageResult resultQM = 
		        service.receiveQueueMessage("testq", opts);
		     BrokeredMessage message = resultQM.getValue(); 
		     if (message != null && message.getMessageId() != null)
		     {
		        try 
		        {
		           System.out.println("Body: " + message.toString());
		           System.out.println("command: " + message.getProperty("command"));
		           System.out.println("MessageID: " + message.getMessageId());
		           System.out.println("Custom Property: " + 
		                message.getProperty("TestProperty"));
		           // Remove message from queue
		           System.out.println("Deleting this message.");
		           service.deleteMessage(message);
		        }
		        catch (Exception ex)
		        {
		           // Indicate a problem, unlock message in queue
		           System.out.println("Inner exception encountered!");
		           service.unlockMessage(message);
		        }
		     }
		     else
		     {
		        System.out.println("Finishing up - no more messages.");
		        break; 
		        // Added to handle no more messages in the queue.
		        // Could instead wait for more messages to be added.
		     }
		}
	}
}
