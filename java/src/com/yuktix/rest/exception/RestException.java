package com.yuktix.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.yuktix.dto.ErrorBean;

public class RestException extends WebApplicationException  {
	
	private static final long serialVersionUID = 1L;
	private static final String message = "internal service error" ;
	
	public RestException() {
		super(Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorBean(Status.INTERNAL_SERVER_ERROR.getStatusCode(),RestException.message))
       		    .type(MediaType.APPLICATION_JSON)
				.build());
		 
     }
 
     public RestException(String message) {
         super(Response.status(Status.INTERNAL_SERVER_ERROR)
        		 .entity(new ErrorBean(Status.INTERNAL_SERVER_ERROR.getStatusCode(),message))
        		 .type(MediaType.APPLICATION_JSON)
        		 .build());
     }
     
     public RestException(Status status, String message) {
    	 
    	 super(Response.status(status)
        		 .entity(new ErrorBean(status.getStatusCode(),message))
        		 .type(MediaType.APPLICATION_JSON)
        		 .build()); 
    	 
     }
}
