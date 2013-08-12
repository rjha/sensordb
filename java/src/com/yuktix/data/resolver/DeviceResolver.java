package com.yuktix.data.resolver;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.yuktix.data.IDataResolver;
import com.yuktix.dto.provision.Variable;

public class DeviceResolver implements IDataResolver{
	
	public HashMap<String,Object> resolve(HashMap<String,Object> map) throws Exception {
		if(map == null || (map.size() == 0)) {
			return map ;
		}
		
		// variables is stored as a string in azure table
		// we need to put it back into an object before
		// serializing the result.
		String variables = (String) map.get("variables");
		 
		if(StringUtils.isBlank(variables)) {
			// no variables for this device.
			return map ;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		List<Variable> varObj = mapper.readValue(variables, new TypeReference<List<Variable>>() {}) ;
		map.put("variables", varObj);
		return map ;
	}
}
