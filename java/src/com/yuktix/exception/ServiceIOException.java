package com.yuktix.exception;

public class ServiceIOException   extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ServiceIOException(String serviceName){
		super(String.format("%s is unavailable", serviceName)) ;
    }
 
	public ServiceIOException(String serviceName, Throwable ex){
		super(String.format("%s is unavailable", serviceName),ex) ;
     }
}
