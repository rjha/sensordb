package com.yuktix.model;

import java.util.List;

public class Device {

	private String name ;
	private String manufacturer ;
	private String version;
	private String description ;
	private String deviceId ;
	private List<Variable> variables ;
	
	public Device(String deviceId, String name) {
		this.deviceId = deviceId ;
		this.name = name ;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public List<Variable> getVariables() {
		return variables;
	}
	
	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}
	
	public String getName() {
		return name;
	}
	
	      
}
