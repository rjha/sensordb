package model;

import java.util.HashMap ;

public class SensorReading {
	private String serialNumber ;
	private String projectId ;
	private HashMap<String,String> readings ;
	// Unix epoch timestamp
	private String timestamp ;
	
	public SensorReading(String projectId, String serialNumber) {
		this.projectId = projectId ;
		this.serialNumber = serialNumber ;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getProjectId() {
		return projectId;
	}

	public HashMap<String, String> getReadings() {
		return readings;
	}

	public void setReadings(HashMap<String, String> readings) {
		this.readings = readings;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
