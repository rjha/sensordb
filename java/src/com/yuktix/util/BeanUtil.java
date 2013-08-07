package com.yuktix.util;

import com.yuktix.dto.response.MapResponseBean;
import com.yuktix.rest.exception.ArgumentException;

public class BeanUtil {
	
	public static MapResponseBean getSuccessBean() {
		MapResponseBean bean = new MapResponseBean();
		bean.setCode(200);
		bean.add("message", "success");
		return bean ;
	}
	
	public static void null_check(Object param) {
		// empty POST data means null param
		if(param == null) {
			throw new ArgumentException("wrong input; parameter is null");
		}
	}
	
}
