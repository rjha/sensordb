package com.yuktix.test;

import com.yuktix.dto.SensorParam;
import com.yuktix.dto.TimeParam;
import com.yuktix.model.time.HumanTime;
import com.yuktix.model.time.HumanTimeUnit;

public class Hello {
	public static void main(String[] args)  throws Exception{
		System.out.println("Hello world!");
		SensorParam param = new SensorParam();
		param.setSerialNumber("s001");
		param.setProjectId("p001");
		
		TimeParam time_slice = new TimeParam();
		HumanTime t1 = new HumanTime();
		t1.setUnit(HumanTimeUnit.MINUTE);
		t1.setValue(1);
		time_slice.setRstart(t1);
		param.setTime_slice(time_slice);
		
		long ts1 = param.getTime_slice().getStartTS();
		System.out.println("ts1 =>" + ts1);
		
		long ts2 = param.getTime_slice().getEndTS();
		System.out.println("ts2 =>" + ts2);
		
	} 
}