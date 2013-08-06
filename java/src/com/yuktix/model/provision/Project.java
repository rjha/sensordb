package com.yuktix.model.provision;

import java.util.ArrayList;
import java.util.List;

public class Project {
	
	private Account account ;
	private String projectId ;
	private String name ;
	
	public Project(Account account,String projectId, String name) {
		this.projectId = projectId ;
		this.name = name ;
		this.account = account ;
		
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
	
}
