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

import com.yuktix.data.Account;
import com.yuktix.data.Device;
import com.yuktix.data.Project;
import com.yuktix.dto.response.* ;
import com.yuktix.dto.provision.* ;
import com.yuktix.dto.query.* ;
import com.yuktix.tsdb.*;
import com.yuktix.util.BeanUtil;

@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Service {

	@POST
	@Path("/account/add")
	public MapResponseBean addAccount(AccountParam param) {
		BeanUtil.null_check(param);
		String accountId = Account.add(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		bean.add("accountId", accountId);
		return bean ;
	}
	
	@GET
	@Path("/account/{accountId}")
	public ResponseBean getAccountOnId(@PathParam("accountId") String param) {
		BeanUtil.null_check(param);
		HashMap<String,String> map = Account.getOnId(param);
		ResponseBean bean = new ResponseBean(200,map);
		return bean ;
	}
	
	@POST
	@Path("/account/list")
	public ResponseBean getAccounts(ScrollingParam param) {
		
		ResultSet data = Account.list(param) ;
		ResponseBean bean = new ResponseBean(200,data);
		return bean ;
	}
	
	@POST
	@Path("/project/add")
	public MapResponseBean addProject(ProjectParam param) {
		BeanUtil.null_check(param);
		String projectId = Project.add(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		bean.add("projectId", projectId);
		return bean ;
	}
	
	@GET
	@Path("/project/{projectId}")
	public ResponseBean getProjectOnId(@PathParam("projectId") String param) {
		BeanUtil.null_check(param);
		HashMap<String,String> map = Project.getOnId(param);
		ResponseBean bean = new ResponseBean(200,map);
		return bean ;
	}
	
	@POST
	@Path("/project/list")
	public ResponseBean getProjects(AccountScrollingParam param) {
		ResultSet data = Project.list(param.getAccountId(),param.getScrolling()) ;
		ResponseBean bean = new ResponseBean(200,data);
		return bean ;
	}
	
	@POST
	@Path("/device/add")
	public MapResponseBean addDevice(DeviceParam param) {
		BeanUtil.null_check(param);
		String deviceId = Device.add(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		bean.add("deviceId", deviceId);
		return bean ;
	}
	
	@GET
	@Path("/device/{deviceId}")
	public ResponseBean getDeviceOnId(@PathParam("deviceId") String param) {
		BeanUtil.null_check(param);
		HashMap<String,String> map = Device.getOnId(param);
		ResponseBean bean = new ResponseBean(200,map);
		return bean ;
	}
	
	@POST
	@Path("/device/list")
	public ResponseBean getDevices(AccountScrollingParam param) {
		ResultSet data = Device.list(param.getAccountId(),param.getScrolling()) ;
		ResponseBean bean = new ResponseBean(200,data);
		return bean ;
	}
	
	@POST
	@Path("/sensor/add")
	public void addSensor(SensorQueryParam param) {
		BeanUtil.null_check(param);
	}
	
	@POST
	@Path("/datapoint")
	public MapResponseBean addDataPoint(@QueryParam("token") String token,DataPointParam param) {
		BeanUtil.null_check(param);
		Store tsdbStore = new Store() ;
		tsdbStore.addDataPoint(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		return bean ;
	}
	
	@POST
	@Path("/sensor/latest")
	public ResponseBean getSensorDataPoint(SensorQueryParam param) {
		BeanUtil.null_check(param);
		Query tsdbQuery = new Query() ;
		List<HashMap<String,String>> response = tsdbQuery.getDataPoint(param);
		ResponseBean bean = new ResponseBean(200,response);
		return bean ;
	}
	
	@POST
	@Path("/sensor/query")
	public ResponseBean getSensorDataInTimeSlice(SensorQueryParam param) {
		BeanUtil.null_check(param);
		Query tsdbQuery = new Query() ;
		List<HashMap<String,String>> data = tsdbQuery.getInTimeSlice(param);
		ResponseBean bean = new ResponseBean(200,data);
		return bean ;
	}
	
	@GET
	@Path("/echo/{input}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String echo(@PathParam("input") String echo) {
		return echo ;
	}
	
}
