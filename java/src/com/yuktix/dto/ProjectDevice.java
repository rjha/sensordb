package com.yuktix.dto;

public class ProjectDevice extends Device{
	
	private String projectId ;
	
	public ProjectDevice() {
		super();
	}

	public ProjectDevice(String projectId) {
		super() ;
		this.projectId = projectId ;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
