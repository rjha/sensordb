package com.yuktix.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.yuktix.dto.ErrorBean;
	/**
	 * mapper class to catch jersey runtime errors
	 * @author rjha
	 *
	 */
	@Provider
	public class ThrowableMapper implements ExceptionMapper<Throwable> {
		
	    @Override
	    public Response toResponse(Throwable ex) {
	    	// @todo log stack trace
	    	ex.printStackTrace();
	    	String message = "Internal service error" ;
	    	
	    	// jackson json parsing exception
	    	if(ex instanceof org.codehaus.jackson.JsonParseException) {
	    		message = String.format("Json parsing error : %s ", ex.getMessage());
	    	}
	    	
	    	if(ex instanceof org.glassfish.jersey.server.ParamException) {
	    		message = String.format("Bad request parameter : %s ", ((org.glassfish.jersey.server.ParamException) ex).getParameterName());
	    	}
	    	
	        return Response.status(Status.INTERNAL_SERVER_ERROR)
	        		.entity(new ErrorBean(Status.INTERNAL_SERVER_ERROR.getStatusCode(),message))
	        		 .type(MediaType.APPLICATION_JSON)
	        		.build();
	    }
	}

