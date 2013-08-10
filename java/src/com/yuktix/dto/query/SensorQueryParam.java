package com.yuktix.dto.query;


/* 
 * @todo add json input validation using 
 * 1) json-schema or
 * 2) a bean validation framework like hibernate validator
 *    or codehaus bean validator
 * 
 */

public class SensorQueryParam {
	
	private String serialNumber ;
	private String projectId ;
	private int size ;
	private TimeParam timeSlice ;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

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

	public TimeParam getTimeSlice() {
		return timeSlice;
	}

	public void setTimeSlice(TimeParam timeSlice) {
		this.timeSlice = timeSlice;
	}
	
}
