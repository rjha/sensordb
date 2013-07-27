package com.yuktix.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig{
	public MyApplication() {
		super();
		register(test.jersey.Calculator.class);
		register(com.yuktix.rest.Service.class);
		register(JacksonFeature.class);
		register(com.yuktix.rest.exception.ThrowableMapper.class);
		
	}
}
