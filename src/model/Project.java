package model;

public class Project {
	
	private String projectId ;
	private String name ;
	
	public Project(String projectId, String name) {
		this.projectId = projectId ;
		this.name = name ;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getName() {
		return name;
	}
	
}
