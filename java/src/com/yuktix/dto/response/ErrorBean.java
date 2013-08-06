package com.yuktix.dto.response;


public class ErrorBean {
	
	private int code ;
	private String error ;
	
	public ErrorBean() {
		this.code = 500 ;
	}
	
	public ErrorBean(int code, String message) {
		this.code = code ;
		this.setError(message) ;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
