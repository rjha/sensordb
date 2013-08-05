package com.yuktix.model.provision;

import java.util.ArrayList;
import java.util.List;

public class Project {
	
	private Account account ;
	private String projectId ;
	private String name ;
	private List<SensorDevice> devices ;
	private List<Sensor> sensors ;
	
	public Project(Account account,String projectId, String name) {
		this.projectId = projectId ;
		this.name = name ;
		this.account = account ;
		this.devices = new ArrayList<SensorDevice>() ;
		this.sensors = new ArrayList<Sensor>() ;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public String getName() {
		return name;
	}
	
	public Account getAccount() {
		return account ;
	}
	
	public void addSensorDevice(SensorDevice sd) {
		sd.setProjectId(projectId);
		this.devices.add(sd) ;
		
	}
	
	public void addSensor(Sensor s) {
		s.setProjectId(projectId);
		this.sensors.add(s) ;
	}
	
}
