package com.yuktix.dto.response;

import java.util.HashMap;

public class MapResponseBean {
	
	private int code ;
	private HashMap<String,String> response ;
	
	public MapResponseBean() {
		this.code = 200 ;
		response = new HashMap<String,String>();
	}
	
	public MapResponseBean(int code, String message) {
		this.code = code ;
		response = new HashMap<String,String>();
		response.put("message", message);
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public HashMap<String,String> getResponse() {
		return response;
	}
	
	public void setResponse(HashMap<String, String> map) {
		this.response = map;
	}
	
	public void add(String key, String value) {
		this.response.put(key, value);
	}
	
}