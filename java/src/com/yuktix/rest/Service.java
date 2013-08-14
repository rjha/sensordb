package com.yuktix.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.yuktix.data.Account;
import com.yuktix.data.SensorTSDB;
import com.yuktix.data.Device;
import com.yuktix.data.Project;
import com.yuktix.data.Sensor;
import com.yuktix.dto.response.* ;
import com.yuktix.dto.tsdb.DataPointParam;
import com.yuktix.dto.provision.* ;
import com.yuktix.dto.query.* ;
import com.yuktix.rest.exception.ArgumentException;
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
		HashMap<String,Object> map = Account.getOnId(param);
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
		HashMap<String,Object>  map = Project.getOnId(param);
		ResponseBean bean = new ResponseBean(200,map);
		return bean ;
	}
	
	@POST
	@Path("/project/list")
	public ResponseBean getProjects(MapScrollingParam param) {
		String accountId = BeanUtil.getSafeMapParam(param.getParameters(),"accountId");
		ResultSet data = Project.list(accountId,param.getScrolling()) ;
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
		HashMap<String,Object> map = Device.getOnId(param);
		ResponseBean bean = new ResponseBean(200,map);
		return bean ;
	}
	
	@POST
	@Path("/device/list")
	public ResponseBean getDevices(MapScrollingParam param) {
		String accountId = BeanUtil.getSafeMapParam(param.getParameters(),"accountId");
		ResultSet data = Device.list(accountId,param.getScrolling()) ;
		ResponseBean bean = new ResponseBean(200,data);
		return bean ;
	}
	
	@POST
	@Path("/sensor/add")
	public MapResponseBean addSensor(SensorParam param) throws Exception {
		BeanUtil.null_check(param);
		Sensor.add(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		return bean ;
	}
	
	@GET
	@Path("/sensor/{sensorId}")
	public ResponseBean getSensorOnId(@PathParam("sensorId") String param) {
		BeanUtil.null_check(param);
		String[] tokens = StringUtils.split(param, ',');
		
		if(tokens.length != 2) {
			throw new ArgumentException("sensorId is not in required format");
		}
		
		String projectId = tokens[0];
		String serialNumber = tokens[1];
		
		HashMap<String,Object> map = Sensor.getOnId(projectId,serialNumber);
		ResponseBean bean = new ResponseBean(200,map);
		return bean ;
	}
	
	@POST
	@Path("/sensor/list")
	public ResponseBean getSensors(MapScrollingParam param) {
		String projectId = BeanUtil.getSafeMapParam(param.getParameters(),"projectId");
		ResultSet data = Sensor.list(projectId,param.getScrolling()) ;
		ResponseBean bean = new ResponseBean(200,data);
		return bean ;
	}
	
	@POST
	@Path("/datapoint")
	public MapResponseBean addDataPoint(DataPointParam param) {
		BeanUtil.null_check(param);
		SensorTSDB.add(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		return bean ;
	}
	
	@POST
	@Path("/sensor/latest")
	public ResponseBean getLatestDataPoint(SensorQueryParam param) {
		BeanUtil.null_check(param);
		ResultSet response = SensorTSDB.getLatest(param);
		ResponseBean bean = new ResponseBean(200,response);
		return bean ;
	}
	
	@POST
	@Path("/sensor/query")
	public ResponseBean queryDataPoint(SensorQueryParam param) {
		BeanUtil.null_check(param);
		ResultSet data = SensorTSDB.query(param);
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
	
	@GET
	@Path("/sms/{data}")
	@Consumes(MediaType.TEXT_PLAIN)
	public MapResponseBean parseSms(@PathParam("data") String data) {
		
		String[] tokens = StringUtils.split(data, ',') ;
		if(tokens.length != 3) {
			throw new ArgumentException("") ;
		}
		
		String projectId = tokens[0] ;
		String serialNumber = tokens[1] ;
		String value = tokens[2] ;
		
		DataPointParam param = new DataPointParam();
		param.setProjectId(projectId);
		param.setSerialNumber(serialNumber);
		Reading reading = new Reading();
		reading.setName("state");
		reading.setValue(value);
		String ts = String.format("%d", new Date().getTime());
		reading.setTimestamp(ts);
		List<Reading> readings = new ArrayList<Reading>();
		readings.add(reading);
		param.setReadings(readings);
		 
		SensorTSDB.add(param);
		MapResponseBean bean = new MapResponseBean(200,"success");
		return bean ;
		
	}
	
}
