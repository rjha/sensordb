package com.yuktix.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.yuktix.rest.exception.ArgumentException;
import com.yuktix.util.time.RelativeTime;

/**
 * time slice parameter for REST services
 * 1) time slice can be defined in terms of absolute start 
 * and end. (unix timestamp in millis, GMT) 
 * 
 * 2) time slice can also be supplied as Human readable 
 * value and units, e.g. 5 HOUR or 21 MINUTE.
 * 
 * when no end timestamp is specified - it defaults to now()
 * 
 *
 */
public class TimeParam {
	
	private String astart ;
	private String aend ;
	private RelativeTime rstart ;
	private RelativeTime rend ;
	
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

	public RelativeTime getRstart() {
		return rstart;
	}

	public void setRstart(RelativeTime rstart) {
		this.rstart = rstart;
	}

	public RelativeTime getRend() {
		return rend;
	}

	public void setRend(RelativeTime rend) {
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
			// relative time unit, subtract from now() to get
			// the absolute timestamp for this relative time window
			start = new Date().getTime() - this.rstart.getMilliTS();
			return start ;
		}
		
	}
	
	public long getEndTS() {
		long end = 1L ;
		if(StringUtils.isBlank(this.aend) && (this.rend == null)) {
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
			// relative
			// subtract from now()
			end = new Date().getTime() - this.rend.getMilliTS();
			return end ;
		}
		
	}
}
