package com.yuktix.rest;



import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.yuktix.dto.response.* ;
import com.yuktix.dto.provision.* ;
import com.yuktix.dto.query.* ;
import com.yuktix.tsdb.*;



@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Service {

	@POST
	@Path("/datapoint")
	public ResponseBean addDataPoint(@QueryParam("token") String token,DataPointParam dp) {
		Store tsdbStore = new Store() ;
		tsdbStore.addDataPoint(dp);
		ResponseBean bean = new ResponseBean(200,"success");
		return bean ;
	}
	
	@POST
	@Path("/query/sensor/latest")
	public ResultBean getSensorDataPoint(SensorParam param) {
		Query tsdbQuery = new Query() ;
		List<HashMap<String,String>> response = tsdbQuery.getDataPoint(param);
		ResultBean bean = new ResultBean(200,response);
		return bean ;
	}
	
	@POST
	@Path("/query/sensor/time")
	public ResultBean getSensorDataInTimeSlice(SensorParam param) {
		Query tsdbQuery = new Query() ;
		List<HashMap<String,String>> data = tsdbQuery.getInTimeSlice(param);
		ResultBean bean = new ResultBean(200,data);
		return bean ;
	}
	
	@GET
	@Path("/echo/{input}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String echo(@PathParam("input") String echo) {
		return echo ;
	}
	
	@POST
	@Path("/account/add")
	public void addAccount(AccountParam param) {
		 
	}
	
	@POST
	@Path("/project/add")
	public void addProject(SensorParam param) {
	 
	}
	
	@POST
	@Path("/device/add")
	public void addDevice(SensorParam param) {
		 
	}
	
	@POST
	@Path("/sensor/add")
	public void addSensor(SensorParam param) {
		
	}
	
}
