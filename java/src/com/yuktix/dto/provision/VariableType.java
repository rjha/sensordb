package com.yuktix.dto.provision;

public enum VariableType {
	BINARY(1, "BINARY"),
	NUMERIC(2, "NUMERIC") ,
	STRING(3, "STRING") ;
	
	
	private final int value ;
	private final String label ;
	
	private VariableType(int value,String label) {
		this.value = value ;
		this.label = label ;
	}

	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return label ;
	}
}
