package com.yuktix.dto.query;

public class ScrollingParam {
	
	private String partition_key ;
	private String row_key ;
	
	public String getPartition_key() {
		return partition_key;
	}
	
	public void setPartition_key(String partition_key) {
		this.partition_key = partition_key;
	}
	
	public String getRow_key() {
		return row_key;
	}
	
	public void setRow_key(String row_key) {
		this.row_key = row_key;
	}
	
	
}
