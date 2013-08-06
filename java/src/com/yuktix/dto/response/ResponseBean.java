package com.yuktix.dto.response;

public class ResponseBean {
	
	private int code ;
	private Object response ;
	
	public ResponseBean() {
		this.code = 200 ;
	}
	
	public ResponseBean(int code, Object object) {
		this.code = code ;
		this.response = object ;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	
}