package com.yuktix.dto.tsdb;

import java.util.Iterator;
import java.util.List;

import com.yuktix.dto.provision.Reading;

// @todo add json input validation using json-schema
// or a bean validation framework like hibernate validator

public class DataPointParam {
	
	private String serialNumber ;
	private String projectId ;
	private List<Reading> readings ;
	
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
	
	public List<Reading> getReadings() {
		return readings;
	}
	
	public void setReadings(List<Reading> readings) {
		this.readings = readings;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("\n Data point: \n ");
		sb.append("\tprojectId:" + projectId + "\n");
		sb.append("\tserial number:" + serialNumber);
		
		Iterator<Reading> iter = readings.iterator() ;
		while(iter.hasNext()) {
			sb.append(iter.next().toString());
		}
		
		return sb.toString();
	}
}
