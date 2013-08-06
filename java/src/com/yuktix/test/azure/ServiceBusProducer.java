package com.yuktix.test.azure;

import com.microsoft.windowsazure.services.serviceBus.*;
import com.microsoft.windowsazure.services.serviceBus.models.*; 
import com.microsoft.windowsazure.services.core.*; 

// dependens on mail.jar;commons-logging;jersey-core-1.x
// jersey-client-1.x;jaxb-impl-2.2.3.1

public class ServiceBusProducer {
	
	public static void main(String[] args) {
		Configuration config = 
			    ServiceBusConfiguration.configureWithWrapAuthentication(
			      "yuktix-ns",
			      "owner",
			      "bwb8rfwIX/oAD8Wgh5LNNNuWBwxIo/CsgQidCoJOajw=",
			      ".servicebus.windows.net",
			      "-sb.accesscontrol.windows.net/WRAPv0.9");
	
			ServiceBusContract service = ServiceBusService.create(config);
			
			try {     
			    // testa created via azure management interface
			    BrokeredMessage message = new BrokeredMessage("test message 3");
			    message.setProperty("command", "bobo");
			    service.sendQueueMessage("testq", message);
			    System.out.println("message sent!");
			    
			} catch (ServiceException e){
				
			    System.out.print("ServiceException encountered: ");
			    System.out.println(e.getMessage());
			    System.exit(-1);
			}
	}
	
}
