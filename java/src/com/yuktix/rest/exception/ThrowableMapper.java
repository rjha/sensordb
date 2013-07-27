package com.yuktix.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.yuktix.dto.ErrorBean;

	@Provider
	public class ThrowableMapper implements ExceptionMapper<Throwable> {
		
		
	    @Override
	    public Response toResponse(Throwable ex) {
	    	// @todo log stack trace
	    	ex.printStackTrace();
	    	String message = "internal service error" ;
	    	
	    	// jackson json parsing exception
	    	if(ex instanceof org.codehaus.jackson.JsonParseException) {
	    		message = String.format("json error : %s ", ex.getMessage());
	    	}
	    	
	        return Response.status(Status.INTERNAL_SERVER_ERROR)
	        		.entity(new ErrorBean(Status.INTERNAL_SERVER_ERROR.getStatusCode(),message))
	        		 .type(MediaType.APPLICATION_JSON)
	        		.build();
	    }
	}

