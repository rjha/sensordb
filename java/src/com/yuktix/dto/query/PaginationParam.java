package com.yuktix.dto.query;

public class PaginationParam {
	String previous_partition ;
	String previous_row ;
	
	String next_partition ;
	String next_row ;
	
	public String getNext_partition() {
		return next_partition;
	}
	
	public void setNext_partition(String next_partition) {
		this.next_partition = next_partition;
	}
	
	public String getNext_row() {
		return next_row;
	}
	
	public void setNext_row(String next_row) {
		this.next_row = next_row;
	}

	public String getPrevious_partition() {
		return previous_partition;
	}

	public void setPrevious_partition(String previous_partition) {
		this.previous_partition = previous_partition;
	}

	public String getPrevious_row() {
		return previous_row;
	}

	public void setPrevious_row(String previous_row) {
		this.previous_row = previous_row;
	}
	
}
