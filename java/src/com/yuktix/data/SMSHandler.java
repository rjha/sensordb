package com.yuktix.data;

import com.yuktix.data.parser.BencodeParser;
import com.yuktix.dto.tsdb.DataPointParam;
import com.yuktix.rest.exception.RestException;
import com.yuktix.util.Log;

public class SMSHandler {
	
	public static void addDataPoint(String data) {
		BencodeParser parser = null ;
		
		try {
			parser = new BencodeParser() ;
			parser.parse(data);
			DataPointParam param = new DataPointParam();
			param.setProjectId(parser.getProjectId());
			param.setSerialNumber(parser.getSerialNumber());
			param.setReadings(parser.getReadings());
			SensorTSDB.add(param);
			
		} catch(RestException rex) {
			throw rex ;
		} catch(Exception ex) {
			Log.error(ex);
			throw new RestException("error storing sensor data received in SMS") ;
		}
		
	}
}
