package com.yuktix.console;

import java.io.IOException;
import java.net.URI;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;


public class GrizzlyHttpServer {
	
	private static final URI BASE_URI = URI.create("http://localhost:9090/sensordb/");
	
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
    	
	    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createApp());
	    System.out.println(String.format("Grizzly http server started.%nHit enter to stop it..."));
	    System.in.read();
	    server.stop();
    }
    
    public static ResourceConfig createApp() {
    	ResourceConfig rc = new ResourceConfig()
		.packages("rest")
		.register(JacksonFeature.class);
        return rc;
        
    }
        
}
