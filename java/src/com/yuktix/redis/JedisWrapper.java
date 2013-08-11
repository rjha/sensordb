package com.yuktix.redis;

import java.util.Locale;
import java.util.ResourceBundle;

import com.yuktix.rest.exception.ServiceIOException;
import com.yuktix.util.Log;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisWrapper {
	
	// thread safe jedis pool
	private static JedisPool pool ;
	
	static {
		
		try {
			
			ResourceBundle bundle = ResourceBundle.getBundle("sensordb",Locale.US);
			String host = bundle.getString("redis.host.name");
			pool =  new JedisPool(host,6379);
			
		} catch(Exception ex) {
			String message = "error reading redis host name property" ;
			Log.error(message, ex);
			
		}
	}
	
	public static Jedis getConnection() throws ServiceIOException {
		// error during initialization
		if (pool == null ) {
			throw new ServiceIOException("Error initializing redis connection");
		}
		
		return pool.getResource() ;
	}
	
}
