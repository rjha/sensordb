package com.yuktix.dto.provision;

public class Reading {
	
	private String name ;
	private String value ;
	private String timestamp ;
	
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
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("\nReading: \n ");
		sb.append("\tname: " + name );
		sb.append("\tvalue: " + value);
		sb.append("\tts: " + timestamp + "\n");
		
		return sb.toString();
	}
	
}
