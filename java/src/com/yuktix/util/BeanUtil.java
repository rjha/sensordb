package com.yuktix.util;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

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
			throw new ArgumentException("wrong input; object is null");
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
		
		if(param != null && !StringUtils.isBlank(param.getPartition())) {
			token.setNextPartitionKey(param.getPartition());
		}
		
		if(param != null && !StringUtils.isBlank(param.getPartition())) {
			token.setNextRowKey(param.getRow());
		}
		
		return token ;
	}
	
	public static String jsonEncode(Object object) throws Exception {
		String output = null ;
		ObjectMapper mapper = new ObjectMapper();
		output = mapper.writeValueAsString(object);
		return output ;
	}
	
	public static String getSafeMapParam(HashMap<String,String> map, String key) {
		if(map == null || (map.size() == 0)) {
			return null ;
		}
		
		String value = map.get(key);
		return value ;
	}
}
