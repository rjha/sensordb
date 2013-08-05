package com.yuktix.dto.response;

import java.util.HashMap;
import java.util.List;


public class ResultBean {
	
	private int code ;
	private List<HashMap<String,String>> response ;
	
	public ResultBean() {
		
	}
	
	public ResultBean(int code, List<HashMap<String,String>> response) {
		this.code = code ;
		this.response = response ;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public List<HashMap<String,String>> getResponse() {
		return response;
	}
	
	public void setResponse(List<HashMap<String,String>> response) {
		this.response = response;
	}
	
}
