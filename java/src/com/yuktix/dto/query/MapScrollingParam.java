package com.yuktix.dto.query;

import java.util.HashMap;

public class MapScrollingParam {
	
	private HashMap<String,String> parameters ;
	private ScrollingParam scrolling ;

	public ScrollingParam getScrolling() {
		return scrolling;
	}

	public void setScrolling(ScrollingParam scrolling) {
		this.scrolling = scrolling;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

}
