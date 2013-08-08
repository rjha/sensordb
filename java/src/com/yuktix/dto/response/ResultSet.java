package com.yuktix.dto.response;

import java.util.HashMap;
import java.util.List;

import com.yuktix.dto.query.PaginationParam;

public class ResultSet {
	List<HashMap<String, String>> series ;
	PaginationParam pagination ;
	
	public ResultSet() {
		
	}
	
	public ResultSet(List<HashMap<String, String>> series, PaginationParam pagination) {
		this.series = series ;
		this.pagination = pagination ;
	}
	
	public List<HashMap<String, String>> getSeries() {
		return series;
	}

	public void setSeries(List<HashMap<String, String>> series) {
		this.series = series;
	}

	public PaginationParam getPagination() {
		return pagination;
	}

	public void setPagination(PaginationParam pagination) {
		this.pagination = pagination;
	}
	
}
