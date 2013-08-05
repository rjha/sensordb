package com.yuktix.dto.provision;

public class ProjectParam {
	private String name ;
	private String accountId ;
	
	public ProjectParam(String name) {
		this.name = name ;
	}

	public String getName() {
		return name;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}
