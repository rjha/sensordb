package com.yuktix.dto.query;

public class ScrollingParam {
	
	private String partition ;
	private String row ;
	private int size ;
	
	public ScrollingParam() {
		this.size = 25 ;
	}
	
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
