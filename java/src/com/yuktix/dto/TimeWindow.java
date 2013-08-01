package com.yuktix.dto;

public class TimeWindow {
	private int value ;
	private TimeWindowUnit unit ;
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public TimeWindowUnit getUnit() {
		return unit;
	}
	
	public void setUnit(TimeWindowUnit unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return this.value + " " + this.unit.toString() ;
	}
	
	public long getTS() {
		long multiplier = 1L ;
		
		switch(unit) {
			case MINUTE :
				multiplier = 60L ; break ;
			case HOUR :
				multiplier = 3600L ; break ;
			case DAY :
				multiplier = 86400L ; break ;
			case MONTH:
				multiplier = 2592000L ; break ;
			case YEAR :
				multiplier = 31536000L ; break ;
			default:
				multiplier = 1L ; break ;
		}
		
		return multiplier * value;
	}
	
	public long getMilliTS() {
		return getTS()*1000L ;
	}
}
