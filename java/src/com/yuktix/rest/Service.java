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

import com.yuktix.dto.DataPoint;
import com.yuktix.dto.ResponseBean;
import com.yuktix.dto.ResultBean;
import com.yuktix.dto.SensorParam;
import com.yuktix.tsdb.Query;
import com.yuktix.tsdb.Store ;


@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Service {

	@POST
	@Path("/datapoint")
	public ResponseBean addDataPoint(@QueryParam("token") String token,DataPoint dp) {
		Store tsdb = new Store() ;
		tsdb.addDataPoint(dp);
		ResponseBean bean = new ResponseBean(200,"success");
		return bean ;
	}
	
	// query/sensor/latest (size in sensorParam)
	// query/sensor/time/ (assume frequency * N points)
	// query/alerts
	// query/inactive
	
	@POST
	@Path("/query/sensor/latest")
	public ResultBean query(SensorParam param) {
		Query tsdb = new Query() ;
		List<HashMap<String,String>> response = tsdb.getLatest(param);
		ResultBean bean = new ResultBean(200,response);
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
