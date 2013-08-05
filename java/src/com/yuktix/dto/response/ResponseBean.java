package com.yuktix.dto.response;

import java.util.HashMap;

public class ResponseBean {
	
	private int code ;
	private HashMap<String,String> response ;
	
	public ResponseBean() {
		response = new HashMap<String,String>();
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