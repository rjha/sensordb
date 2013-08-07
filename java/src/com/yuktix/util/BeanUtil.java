package com.yuktix.util;

import org.apache.commons.lang3.StringUtils;

import com.microsoft.windowsazure.services.core.storage.ResultContinuation;
import com.microsoft.windowsazure.services.core.storage.ResultContinuationType;
import com.yuktix.dto.query.ScrollingParam;
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
	
	public static ResultContinuation getContinuationToken(ScrollingParam param) {
		ResultContinuation token = null ;
		if(param == null ) {
			return null ;
		} else {
			token = new ResultContinuation();
			token.setContinuationType(ResultContinuationType.TABLE);
		}
		
		if(param != null && !StringUtils.isBlank(param.getPartition_key())) {
			token.setNextPartitionKey(param.getPartition_key());
		}
		
		if(param != null && !StringUtils.isBlank(param.getPartition_key())) {
			token.setNextRowKey(param.getRow_key());
		}
		
		return token ;
	}
}
