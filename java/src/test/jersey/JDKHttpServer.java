package test.jersey;



import java.net.URI;
import com.sun.net.httpserver.HttpServer;
import com.yuktix.rest.MyApplication;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory ;

public class JDKHttpServer {

	static final String BASE_URI = "http://localhost:9099/sensordb";

	public static void main(String[] args) throws Exception {
		HttpServer server = null ;
		URI endpoint = new URI(BASE_URI);
		
		server = JdkHttpServerFactory.createHttpServer(endpoint,new MyApplication());
		System.out.println("JDK Http server : calculator service started... ");
		System.in.read();
		server.stop(0);
		 
	}
}
