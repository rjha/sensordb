package com.yuktix.test;

import redis.clients.jedis.Jedis;

import com.yuktix.redis.JedisWrapper;

public class Hello {
	public static void main(String[] args)  throws Exception{
		System.out.println("Hello world!");
		Jedis jedis = JedisWrapper.getConnection();
		jedis.set("name", "rajeev jha");
	} 
}