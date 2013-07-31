package com.yuktix.dto;


public class ResponseBean {
	
	private int code ;
	private String response ;
	
	public ResponseBean() {
		
	}
	
	public ResponseBean(int code, String response) {
		this.code = code ;
		this.response = response ;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
}
