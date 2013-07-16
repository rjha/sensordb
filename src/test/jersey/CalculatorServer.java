package test.jersey;


import java.io.IOException;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class CalculatorServer {

	static final String BASE_URI = "http://localhost:9099/calculator/";

	public static void main(String[] args) {
		
		try {
			// base_uri is for this server only
			// and has no relationship with ROOT resource class
			
			HttpServer server = HttpServerFactory.create(BASE_URI);
			server.start();
			System.out.println("Press Enter to stop the server. ");
			System.in.read();
			server.stop(0);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}