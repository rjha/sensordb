package com.yuktix.dto;

import javax.ws.rs.QueryParam;

public class SmsBeanParam {
	     
		@QueryParam("from")
	    private String from;
		@QueryParam("body")
	    private String body ;
		
		public String getFrom() {
			return from;
		}
		
		public void setFrom(String from) {
			this.from = from;
		}
		
		public String getBody() {
			return body;
		}
		
		public void setBody(String body) {
			this.body = body;
		}
	      
}
