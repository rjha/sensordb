package com.yuktix.dto.provision;

import java.util.ArrayList;
import java.util.List;
import com.yuktix.dto.Variable;

public class DeviceParam {
	
	private String name ;
	private String manufacturer ;
	private String description ;
	
	// A device is attached to an account
	private String accountId ;
	
	// the point of storing variables during provisioning 
	// is that we can verify that a sensor is sending back
	// the same variables during first handshake.
	private List<Variable> variables ;

	public DeviceParam() {
		
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Variable> getVariables() {
		return variables;
	}
	
	public void setVariables(ArrayList<com.yuktix.dto.Variable> variables) {
		this.variables = variables;
	}
	
	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}
}
