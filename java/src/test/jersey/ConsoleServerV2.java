package test.jersey;



import java.net.URI;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory ;
import org.glassfish.jersey.server.ResourceConfig;

public class ConsoleServerV2 {

	static final String BASE_URI = "http://localhost:9099/sensordb";

	public static void main(String[] args) throws Exception {
		HttpServer server = null ;
		
		ResourceConfig rc = new ResourceConfig(rest.Service.class);
		URI endpoint = new URI(BASE_URI);
		
		server = JdkHttpServerFactory.createHttpServer(endpoint,rc);
		System.out.println("console v2.0 : Press Enter to stop the server. ");
		System.in.read();
		server.stop(0);
		 
	}
}
