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

import com.yuktix.data.Provision;
import com.yuktix.dto.response.* ;
import com.yuktix.dto.provision.* ;
import com.yuktix.dto.query.* ;
import com.yuktix.rest.exception.RestException;
import com.yuktix.tsdb.*;

@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Service {

	@POST
	@Path("/datapoint")
	public MapResponseBean addDataPoint(@QueryParam("token") String token,DataPointParam dp) {
		null_check(dp);
		Store tsdbStore = new Store() ;
		tsdbStore.addDataPoint(dp);
		MapResponseBean bean = new MapResponseBean(200,"success");
		return bean ;
	}
	
	@POST
	@Path("/query/sensor/latest")
	public ResponseBean getSensorDataPoint(SensorParam param) {
		null_check(param);
		Query tsdbQuery = new Query() ;
		List<HashMap<String,String>> response = tsdbQuery.getDataPoint(param);
		ResponseBean bean = new ResponseBean(200,response);
		return bean ;
	}
	
	@POST
	@Path("/query/sensor/time")
	public ResponseBean getSensorDataInTimeSlice(SensorParam param) {
		null_check(param);
		Query tsdbQuery = new Query() ;
		List<HashMap<String,String>> data = tsdbQuery.getInTimeSlice(param);
		ResponseBean bean = new ResponseBean(200,data);
		return bean ;
	}
	
	
	@POST
	@Path("/account/add")
	public MapResponseBean addAccount(AccountParam param) {
		null_check(param);
		String accountId = Provision.addAccount(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		bean.add("accountId", accountId);
		return bean ;
	}
	
	@POST
	@Path("/project/add")
	public MapResponseBean addProject(ProjectParam param) {
		null_check(param);
		String projectId = Provision.addProject(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		bean.add("projectId", projectId);
		return bean ;
	}
	
	@POST
	@Path("/device/add")
	public void addDevice(SensorParam param) {
		null_check(param);
	}
	
	@POST
	@Path("/sensor/add")
	public void addSensor(SensorParam param) {
		null_check(param);
	}
	
	private void null_check(Object param) {
		// empty POST data means null param
		if(param == null) {
			throw new RestException("wrong input; parameter is null");
		}
	}
	
	@GET
	@Path("/echo/{input}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String echo(@PathParam("input") String echo) {
		return echo ;
	}
	
	@GET
	@Path("/project/{projectId}")
	public ResponseBean getProjectOnId(@PathParam("projectId") String projectId) {
		null_check(projectId);
		HashMap<String,String> map = Provision.getProjectOnId(projectId);
		ResponseBean bean = new ResponseBean(200,map);
		return bean ;
	}
	
	@GET
	@Path("/account/{accountId}")
	public ResponseBean getAccountOnId(@PathParam("accountId") String param) {
		null_check(param);
		HashMap<String,String> map = Provision.getAccountOnId(param);
		ResponseBean bean = new ResponseBean(200,map);
		return bean ;
	}
	
	@GET
	@Path("/project/list")
	public ResponseBean getProjects() {
		ResponseBean bean = new ResponseBean(200,new Object());
		return bean ;
	}
}
