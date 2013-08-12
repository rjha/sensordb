package com.yuktix.dto.response;

import java.util.HashMap;

public class MapResponseBean {
	
	private int code ;
	private HashMap<String,Object> response ;
	
	public MapResponseBean() {
		this.code = 200 ;
		response = new HashMap<String,Object>();
	}
	
	public MapResponseBean(int code, String message) {
		this.code = code ;
		response = new HashMap<String,Object>();
		response.put("message", message);
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public HashMap<String,Object> getResponse() {
		return response;
	}
	
	public void setResponse(HashMap<String, Object> map) {
		this.response = map;
	}
	
	public void add(String key, String value) {
		this.response.put(key, value);
	}
	
}