package com.yuktix.dto.query;

public class ScrollingParam {
	
	private String partition ;
	private String row ;
	
	public String getPartition() {
		return partition;
	}
	
	public void setPartition(String partition) {
		this.partition = partition;
	}
	
	public String getRow() {
		return row;
	}
	
	public void setRow(String row) {
		this.row = row;
	}
	
}
