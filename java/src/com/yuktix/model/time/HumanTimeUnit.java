package com.yuktix.model.time;

public enum HumanTimeUnit {
	
	SECOND("second"),
	MINUTE("minute"),
	HOUR("hour"),
	DAY("day"),
	WEEK ("week"),
	MONTH("month"),
	YEAR("year");
	
	private String value ;
	
	private HumanTimeUnit(String value) {
		this.value = value ;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value ;
	}
	
	@Override
	public String toString() {
		return value ;
	}
}
