package com.yuktix.util.time;


public class HumanTime {
	private int value ;
	private HumanTimeUnit unit ;
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public HumanTimeUnit getUnit() {
		return unit;
	}
	
	public void setUnit(HumanTimeUnit unit) {
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
