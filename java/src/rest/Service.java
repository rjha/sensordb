package rest;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application ;
import javax.ws.rs.core.MediaType;

import dto.Device ;


@Path("/apiv1")
public class Service extends Application {

	@POST
	@Path("/device/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addDevice(@QueryParam("token") String token,String json) {
		// @todo use token for authentication
		String response = null ;
		DeviceServiceImpl device = new DeviceServiceImpl() ;
		response = device.create(json) ;
		return response ;
	}
	
	@GET
	@Path("/device/{deviceId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Device getDevice(@QueryParam("token") String token, @PathParam("deviceId") String deviceId) {
		//return a device
		
		Device indrionPTHSensor = new Device();
		indrionPTHSensor.setManufacturer("indrion in bangalore");
		indrionPTHSensor.setDescription("single board capable of reading P/T/H");
		
		return indrionPTHSensor ;
	}
	
	@GET
	@Path("/echo/{input}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String echo(@PathParam("input") String echo) {
		return echo ;
	}
	
}
