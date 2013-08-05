package com.yuktix.tsdb;

import java.util.ArrayList;
import java.util.List;

import com.yuktix.dto.NameValuePair;

public class RollupOperation {
	
	private List<NameValuePair> series ;
	
	public List<NameValuePair> getSeries() {
		return series;
	}

	public RollupOperation() {
		this.series = new ArrayList<NameValuePair>();
	}
	
	public void insert(String name, String value) {
		series.add(new NameValuePair(name,value));
	}
	
	
}
