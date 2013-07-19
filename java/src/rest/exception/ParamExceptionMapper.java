package rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ParamException;

	 
	@Provider
	public class ParamExceptionMapper implements ExceptionMapper<ParamException> {
	    @Override
	    public Response toResponse(ParamException exception) {
	        return Response.status(Status.BAD_REQUEST)
	        		.entity(exception.getParameterName() + " incorrect type")
	        		.build();
	    }
	}

