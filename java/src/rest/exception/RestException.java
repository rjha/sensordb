package rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dto.ErrorBean;

public class RestException extends WebApplicationException  {
	
	private static final long serialVersionUID = 1L;
	private static final String message = "internal service error" ;
	
	public RestException() {
		super(Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(RestException.message)
				.type("text/plain")
				.build());
		 
     }
 
     public RestException(String message) {
         super(Response.status(Status.INTERNAL_SERVER_ERROR)
        		 .entity(message)
        		 .type("text/plain")
        		 .build());
     }
     
     public RestException(Status status, String message) {
    	 /*
         super(Response.status(status)
        		 .entity(message)
        		 .type("text/plain")
        		 .build()); */
    	 super(Response.status(status)
        		 .entity(new ErrorBean(status.getStatusCode(),message))
        		 .type("text/plain")
        		 .build());
    	 
     }
}
