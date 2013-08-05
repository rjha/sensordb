package com.yuktix.model.provision;

import java.util.HashMap;

public class SensorDevice {
	private Device device ;
	private HashMap<String,String> metaData ;
	private String projectId ;
	
	public SensorDevice(Device device) {
		this.device = device ;
		this.metaData = new HashMap<String,String>() ;
	}
	
	public Device getDevice() {
		return device;
	}
	
	public HashMap<String, String> getMetaData() {
		return metaData;
	}
	
	public void setMetaData(HashMap<String, String> metaData) {
		this.metaData = metaData;
	}
	
	public void addMetaData(String key, String value) {
		this.metaData.put(key, value);
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	
}
