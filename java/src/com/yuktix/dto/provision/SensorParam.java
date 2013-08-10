package com.yuktix.dto.provision;

import java.util.List;

import com.yuktix.dto.NameValuePair;

public class SensorParam {
	
	String serialNumber ;
	// sensor belongs to a project
	String projectId ;
	// device belongs to an account
	String  deviceId ;
	
	private List<NameValuePair> metaData ;
	private List<String> groupKeys ;
	
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
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public List<NameValuePair> getMetaData() {
		return metaData;
	}

	public void setMetaData(List<NameValuePair> metaData) {
		this.metaData = metaData;
	}

	public List<String> getGroupKeys() {
		return groupKeys;
	}

	public void setGroupKeys(List<String> groupKeys) {
		this.groupKeys = groupKeys;
	}

}
