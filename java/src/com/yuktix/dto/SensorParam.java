package com.yuktix.dto;

// @todo add json input validation using json-schema
// or a bean validation framework like hibernate validator

public class SensorParam {
	private String serialNumber ;
	private String projectId ;
	private int size ;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getProjectId() {
		return projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
}
