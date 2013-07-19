package rest.exception;
import javax.ws.rs.core.Response.Status;

public class ArgumentException extends RestException  {
	
	private static final long serialVersionUID = 1L;
	private static final String message = "bad request : check input data" ;
	
	public ArgumentException() {
		super(Status.BAD_REQUEST,ArgumentException.message);
     }
 
     public ArgumentException(String message) {
         super(Status.BAD_REQUEST,message);
     }
}
