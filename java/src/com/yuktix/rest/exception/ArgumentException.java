package com.yuktix.rest.exception;
import javax.ws.rs.core.Response.Status;

public class ArgumentException extends RestException  {
	
	private static final long serialVersionUID = 1L;
	
	public ArgumentException(String message) {
		super(Status.BAD_REQUEST,message);
     }
		
}
