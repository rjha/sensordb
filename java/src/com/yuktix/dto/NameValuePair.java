package com.yuktix.dto;

public class NameValuePair {
	private String name ;
	private String value ;
	
	public NameValuePair(String name, String value) {
		this.name = name ;
		this.value = value ;
	}
	
	public NameValuePair() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
