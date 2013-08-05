package com.yuktix.tsdb;

import java.util.Arrays;

import redis.clients.jedis.Jedis;

import com.yuktix.exception.ServiceIOException;
import com.yuktix.redis.JedisWrapper;

public class Rollup {
	
	private String projectId ;
	private int[] buckets ;
	private String variable ;
	
	public void init(String projectId, String variable, int[] buckets) throws Exception {
		// we need atleast one bucket
		// otherwise throw error
		this.projectId = projectId ;
		if(buckets == null || buckets.length == 0) {
			throw new Exception("Rollup needs atleast one bucket!");
		}
		
		//sort input buckets size
		Arrays.sort(buckets);
		this.buckets = buckets ;
		this.variable = variable;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	public void write(String value) throws ServiceIOException {
		// lowest rollup
		int bucket = this.buckets[0] ;
		String key = String.format("%s:%s:rollup:%d",this.projectId,this.variable,bucket);
		Jedis jedis = JedisWrapper.getConnection();
		jedis.rpush(key, value);
	}
	
	public void consolidate() {
		// look @ first bucket
		// size > what we need?
		
	}
	
}
