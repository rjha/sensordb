package rest;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import dto.Variable;

/**
 * 
 * @author rjha
 * @todo eclipse author name
 * @todo github version string
 * 
 * class providing implementation of device service interface.
 * this class throws errors known to service exception mapper.
 * Any unknown error should be treated as internal server error (500)
 * 
 * 
 * 
 */
public class DeviceServiceImpl {
	 
	public String create(String json) throws Exception{
		String response = null;
		
		List<Variable> variables = null ;
		// parse json
		
		ObjectMapper mapper = new ObjectMapper();
		dto.Device device = mapper.readValue(json, dto.Device.class) ;
		// @todo input check
		variables = device.getVariables() ;
		// serialize variables
		response = mapper.writeValueAsString(variables);
		
		return response ;
	}
}
