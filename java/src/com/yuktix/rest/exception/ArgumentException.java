package com.yuktix.rest.exception;
import javax.ws.rs.core.Response.Status;

public class ArgumentException extends RestException  {
	
	private static final long serialVersionUID = 1L;
	
	public ArgumentException(String message) {
		super(Status.BAD_REQUEST,message);
     }
 
     public ArgumentException(String param,String message) {
    	 super(Status.BAD_REQUEST,String.format("Bad input : %s : %s ",param,message)) ;
     }
}
