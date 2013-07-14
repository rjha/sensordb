package model;

import java.util.HashMap;

public class SensorDevice {
	private String projectId ;
	private Device device ;
	private HashMap<String,String> metaData ;
	
	public SensorDevice(String projectId,Device device) {
		this.projectId = projectId ;
		this.device = device ;
	}
	
	public String getProjectId() {
		return projectId;
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
	
}
