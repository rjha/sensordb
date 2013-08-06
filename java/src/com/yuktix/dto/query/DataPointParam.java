package com.yuktix.dto.query;

import java.util.List;

import com.yuktix.dto.NameValuePair;
import com.yuktix.dto.Reading;


// @todo add json input validation using json-schema
// or a bean validation framework like hibernate validator

public class DataPointParam {
	private String serialNumber ;
	private String projectId ;
	private List<Reading> readings ;
	private List<NameValuePair> metaData ;
	
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
	
	public List<NameValuePair> getMetaData() {
		return metaData;
	}
	
	public void setMetaData(List<NameValuePair> metaData) {
		this.metaData = metaData;
	}
	
}