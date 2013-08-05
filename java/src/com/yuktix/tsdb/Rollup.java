package com.yuktix.tsdb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import redis.clients.jedis.Jedis;

import com.yuktix.dto.NameValuePair;
import com.yuktix.exception.ServiceIOException;
import com.yuktix.redis.JedisWrapper;

// @todo - fix design of rollups

public class Rollup {
	
	private String projectId ;
	private int[] buckets ;
	private int[] scales ;
	
	
	public Rollup(String projectId) throws Exception {
		
		this.projectId = projectId ;
		// @todo load project rollup configuration
		int[] buckets = new int[] {60,300,7200,86400} ;
		if(buckets == null || buckets.length == 0) {
			throw new Exception("Rollup needs atleast one bucket!");
		}
		
		//sort input buckets size
		Arrays.sort(buckets);
		this.buckets = buckets ;
		
		//scales for rollup lists
		this.scales = new int[this.buckets.length] ;
		this.scales[0] = this.buckets[0] ;
				
		for(int i = 1 ; i < this.buckets.length ; i++) {
			scales[i] = (this.buckets[i] / this.buckets[i-1]) ;
			
		}
	}
	
	public void execute(RollupOperation operation) throws ServiceIOException {
		// lowest resolution of rollup
		int bucket1 = this.buckets[0] ;
		List<NameValuePair> nvps = operation.getSeries();
		Jedis jedis = JedisWrapper.getConnection();
		List<String> variables = new ArrayList<String>();
		
		for(NameValuePair nvp : nvps) {
			String list1 = String.format("%s:%s:rollup:%d",this.projectId,nvp.getName(),bucket1);
			variables.add(nvp.getName());
			//pipeline?
			jedis.rpush(list1, nvp.getValue());
		}
		
	}
	
}
