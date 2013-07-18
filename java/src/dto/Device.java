package dto;

import java.util.ArrayList;
import java.util.List;
import dto.Variable;

public class Device {
	
	private String name ;
	private String manufacturer ;
	private String version;
	private String description ;
	private String deviceId ;
	private List<Variable> variables ;
	
	public Device() {
		
	}
	
	/*
	public Device(String deviceId, String name) {
		this.deviceId = deviceId ;
		this.name = name ;
	} */
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public List<Variable> getVariables() {
		return variables;
	}
	
	public void setVariables(ArrayList<dto.Variable> variables) {
		this.variables = variables;
	}
	
}