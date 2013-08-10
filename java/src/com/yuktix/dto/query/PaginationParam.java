package com.yuktix.dto.query;

public class PaginationParam {
	String lastPartition ;
	String lastRow ;
	
	String nextPartition ;
	String nextRow ;
	
	public String getLastPartition() {
		return lastPartition;
	}
	
	public void setLastPartition(String lastPartition) {
		this.lastPartition = lastPartition;
	}
	
	public String getLastRow() {
		return lastRow;
	}
	
	public void setLastRow(String lastRow) {
		this.lastRow = lastRow;
	}
	
	public String getNextPartition() {
		return nextPartition;
	}
	
	public void setNextPartition(String nextPartition) {
		this.nextPartition = nextPartition;
	}
	
	public String getNextRow() {
		return nextRow;
	}
	
	public void setNextRow(String nextRow) {
		this.nextRow = nextRow;
	}
	
}
