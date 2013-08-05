package com.yuktix.model.provision;

import java.util.HashMap ;

public class Sensor {
	
	private SensorDevice sensorDevice ;
	private String serialNumber ;
	private HashMap<String,String> metaData ;
	private String projectId ;
	
	public Sensor(SensorDevice sensorDevice, String serialNumber) {
		this.sensorDevice = sensorDevice ;
		this.serialNumber = serialNumber ;
		this.metaData = new HashMap<String,String>() ;
	}
	
	public SensorDevice getSensorDevice() {
		return sensorDevice;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public HashMap<String, String> getMetaData() {
		return metaData;
	}

	public void setMetaDta(HashMap<String, String> metaData) {
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
