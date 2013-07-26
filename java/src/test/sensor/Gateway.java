package test.sensor;

import com.yuktix.model.*;

// a gateway is project specific
public class Gateway {
	
	private Project project ;
	public Gateway(Project project) {
		this.project = project ;
	}
	
	public void parse(String rawData) {
		// parse rawData to get 
		// 1. sensor serial number
		// 2. projectId 
		
		// from projectId + sensor serial number - get variables
		// push data into allowed variables
		
	}
}
