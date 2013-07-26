package com.yuktix.model;

public class Variable {
	
	public static final int NUMERIC = 1 ;
	public static final int BINARY = 2 ;
	public static final int STRING = 3 ;
	
	private String name ;
	private String unit ;
	private int type ;
	private String symbol ;
	
	public Variable(String name, String unit, int type, String symbol) {
		//TODO input check
		this.name = name ;
		this.unit = unit ;
		this.type = type ;
		this.symbol = symbol ;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public int getType() {
		return type;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
}
