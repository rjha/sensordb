package test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Hello {
	public static void main(String[] args) {
		System.out.println("Hello world!");
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR)
		 .entity("some message")
		 .type("text/plain")
		 .build() ;
		 
	}
}

