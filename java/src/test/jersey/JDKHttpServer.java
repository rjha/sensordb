package test.jersey;



import java.net.URI;
import com.sun.net.httpserver.HttpServer;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory ;
import org.glassfish.jersey.server.ResourceConfig;

public class JDKHttpServer {

	static final String BASE_URI = "http://localhost:9099/calculator";

	public static void main(String[] args) throws Exception {
		HttpServer server = null ;
		ResourceConfig rc = new ResourceConfig()
		.register(test.jersey.Calculator.class)
		.register(JacksonFeature.class);
		
		URI endpoint = new URI(BASE_URI);
		
		server = JdkHttpServerFactory.createHttpServer(endpoint,rc);
		System.out.println("JDK Http server : calculator service started... ");
		System.in.read();
		server.stop(0);
		 
	}
}
