package com.yuktix.util;

import com.yuktix.dto.response.MapResponseBean;

public class BeanUtil {
	
	public static MapResponseBean getSuccessBean() {
		MapResponseBean bean = new MapResponseBean();
		bean.setCode(200);
		bean.add("message", "success");
		return bean ;
	}
}
