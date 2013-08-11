package com.yuktix.rest.exception;

import javax.ws.rs.core.Response.Status;

public class ServiceIOException   extends RestException {
	
	private static final long serialVersionUID = 1L;
	
	public ServiceIOException(String message){
		super(Status.SERVICE_UNAVAILABLE,message) ;
    }
	
}
