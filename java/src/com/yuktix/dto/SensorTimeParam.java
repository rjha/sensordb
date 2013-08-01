package com.yuktix.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import com.yuktix.rest.exception.ArgumentException;

public class SensorTimeParam {
	
	private String serialNumber ;
	private String projectId ;
	private String astart ;
	private String aend ;
	private TimeWindow rstart ;
	private TimeWindow rend ;

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
	
	public String getAstart() {
		return astart;
	}

	public void setAstart(String astart) {
		this.astart = astart;
	}

	public String getAend() {
		return aend;
	}

	public void setAend(String aend) {
		this.aend = aend;
	}

	public TimeWindow getRstart() {
		return rstart;
	}

	public void setRstart(TimeWindow rstart) {
		this.rstart = rstart;
	}

	public TimeWindow getRend() {
		return rend;
	}

	public void setRend(TimeWindow rend) {
		this.rend = rend;
	}

	public long getStartTS() {
		long start = 1L ;
		if(StringUtils.isBlank(this.astart) && (this.rstart == null)) {
			throw new ArgumentException("no start time specified") ;
		}
		
		// if absolute time is specified - use that
		if(StringUtils.isNotBlank(this.astart)) {
			start = Long.parseLong(this.astart);
			return start ;
		} else {
			start = this.rstart.getMilliTS();
			return start ;
		}
		
	}
	
	public long getEndTS() {
		long end = 1L ;
		if(StringUtils.isBlank(this.astart) && (this.rstart == null)) {
			// return now()
			// Date() defaults to GMT
			end = new Date().getTime();
			return end ;
		}
		
		// if absolute time is specified - use that
		if(StringUtils.isNotBlank(this.aend)) {
			end = Long.parseLong(this.aend);
			return end ;
		} else {
			end = this.rend.getMilliTS();
			return end ;
		}
		
	}
}
