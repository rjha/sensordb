package com.yuktix.dto;

/* 
 * @todo add json input validation using 
 * 1) json-schema or
 * 2) a bean validation framework like hibernate validator
 *    or codehaus bean validator
 * 
 */

public class SensorParam {
	
	private String serialNumber ;
	private String projectId ;
	private int size ;
	private TimeParam time_slice ;

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

	public TimeParam getTime_slice() {
		return time_slice;
	}

	public void setTime_slice(TimeParam time_slice) {
		this.time_slice = time_slice;
	}
	
}
