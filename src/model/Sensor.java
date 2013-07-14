package model;

import java.util.HashMap ;

public class Sensor {
	
	private String projectId ;
	private SensorDevice sensorDevice ;
	private String serialNumber ;
	private HashMap<String,String> metaData ;
	
	public Sensor(String projectId, SensorDevice sensorDevice, String serialNumber) {
		this.projectId = projectId ;
		this.sensorDevice = sensorDevice ;
		this.serialNumber = serialNumber ;
	}

	public String getProjectId() {
		return projectId;
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
	
}
