package test.sensor;
import java.util.ArrayList;

import com.yuktix.model.*;


public class Provision {
	
	public void createProject() {
		
		// create account
		// load devices to an account
		Account rajeev = new Account("Rajeev Jha");
		
		Device indrionPTHSensor = new Device("d001", "indrion PTH sensor");
		indrionPTHSensor.setManufacturer("indrion in bangalore");
		indrionPTHSensor.setDescription("single board capable of reading P/T/H");
		
		// device variables
		ArrayList<Variable> variables = new ArrayList<Variable>();
		variables.add(new Variable("pressure", "lb", Variable.NUMERIC,"P"));
		variables.add(new Variable("temperature", "k", Variable.NUMERIC,"T"));
		variables.add(new Variable("humidity", "percentage", Variable.STRING,"H"));
		
		indrionPTHSensor.setVariables(variables);
		
		// create project for account
		Project tweetPlant = new Project(rajeev,"pt001","plants sends tweet");
		// add sensor device to project
		SensorDevice sd1 = new SensorDevice(indrionPTHSensor);
		tweetPlant.addSensorDevice(sd1);
		
		// create sensor using sensor device
		Sensor s1 = new Sensor(sd1,"s001");
		s1.addMetaData("location", "indoor") ;
		tweetPlant.addSensor(s1);
		
		Sensor s2 = new Sensor(sd1,"s002");
		s2.addMetaData("location", "outdoor") ;
		tweetPlant.addSensor(s2);
		
		
	}
}
