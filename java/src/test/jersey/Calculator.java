package test.jersey;


import javax.ws.rs.core.Application ;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

// the jersey built in http server
// will look for ROOT resource class in classpath
// class Calculator qualifies as root resource class as 
// it is annotated with @Path
// org.glassfish.jersey.servlet.ServletContainer


@Path("/v1")
public class Calculator extends Application{
	
	@GET
	@Path("/echo")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String echoText(@QueryParam("x") String input) {
		return input ;
	}
	
	// curl -i -H "Content-Type: application/json" -X POST -d "firstName=james" http://localhost:9099/calculator/apiv1/test1?token=1234
	
	@POST
	@Path("/test1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addJsonText(@QueryParam("token") String token, String json) {
		// map json to json object 
		return token + "::" + json + "\n";
	}
	
	// curl -i -H "Content-Type: application/json" -X POST -d "{\"name\":\"james\"}" http://localhost:9099/calculator/apiv1/test2?token=1234
	
	@POST
	@Path("/test2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addJsonObject(@QueryParam("token") String token, test.jersey.dto.Account account) {
		// map json to json object 
		return token + "::" + account.getName() + "::" + account.getLocation() + "\n";
	}
	
	// curl -i -H "Content-Type: application/x-www-form-urlencoded" -X POST -d "name=james" http://localhost:9099/calculator/apiv1/test3?token=1234
	
	@POST
	@Path("/test3")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addForm(@QueryParam("token") String token, String formData) {
		// map json to json object 
		return token + "::" + formData + "\n";
	}
	
	// curl -i -H "Content-Type: application/x-www-form-urlencoded" -X POST -d "name=james&location=london" http://localhost:9099/calculator/apiv1/test4?token=1234
		
	@POST
	@Path("/test4")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addFormObject(@QueryParam("token") String token,
			@FormParam("name") String name,
			@FormParam("location") String location) {
		// map json to json object 
		return token + "::" + name + "::" + location + "\n";
	}
	
	@GET
	@Path("/add/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlainText(@PathParam("a") double a, @PathParam("b") double b) {
		return (a + b) + "";
	}
	

    @GET
	@Path("/sub/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	public String subPlainText(@PathParam("a") double a, @PathParam("b") double b) {
		return (a - b) + "";
	}
    
	@GET
	@Path("/add/{a}/{b}")
	@Produces(MediaType.TEXT_XML)
	public String add(@PathParam("a") double a, @PathParam("b") double b) {
		return "<?xml version=\"1.0\"?>" + "<result>" +  (a + b) + "</result>";
	}

	@GET
	@Path("/sub/{a}/{b}")
	@Produces(MediaType.TEXT_XML)
	public String sub(@PathParam("a") double a, @PathParam("b") double b) {
		return "<?xml version=\"1.0\"?>" + "<result>" +  (a - b) + "</result>";
	}
}
