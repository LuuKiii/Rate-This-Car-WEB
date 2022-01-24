package carDetail;



import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

import jsf.dao.ProducerDAO;
import jsf.entities.Producer;
import jsf.dao.VehicleDAO;
import jsf.entities.Vehicle;



import jsf.dao.CarDAO;
import jsf.entities.Car;
import jsf.dao.TruckDAO;
import jsf.dao.UserRatesVehicleDAO;
import jsf.entities.Truck;
import jsf.entities.UserRatesVehicle;
import jsf.dao.MotorDAO;
import jsf.entities.Motor;

@Named
@ViewScoped
public class CarDetailBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_ADD_VEHICLE = "/pages/admin/addVehicle?faces-redirect=true";


	
	private int prod;
	
	private Producer producer = new Producer();
	
	private Vehicle vehicle = new Vehicle();
	private Vehicle loaded = null;
	
	private Car car = new Car();
	private Truck truck = new Truck();
	private Motor motor = new Motor();
	
	
	private UserRatesVehicle rates = new UserRatesVehicle();
	


	public Vehicle getVehicle() {
		return vehicle;
	}
	

	public Car getCar() {
		return car;
	}

	public Truck getTruck() {
		return truck;
	}

	public Motor getMotor() {
		return motor;
	}





	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}



	@EJB
	VehicleDAO vehicleDAO;
	
	@EJB
	ProducerDAO producerDAO;
	
	@EJB
	UserRatesVehicleDAO rateDAO;
	
	@EJB
	CarDAO carDAO;
	
	@EJB 
	TruckDAO truckDAO;
	
	@EJB 
	MotorDAO motorDAO;
	
	
	@Inject
	FacesContext ctx;
		
	@Inject
	Flash flash;
	
	
	
	public void onLoad() throws IOException {

		loaded = (Vehicle) flash.get("vehicle");
		if (loaded != null) {
			vehicle = loaded;
			producer = producerDAO.find(vehicle.getProducer().getIdproducer());
			extension();
			
		} else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));			
		}

	}
	
	public void extension() {
		String type = vehicle.getVehicleType();
		car = null;
		truck = null;
		motor = null;
		
		if(type.equals("Car")) {
			car = carDAO.getOriginVeh(vehicle);
		}else if (type.equals("Truck")) {
			truck = truckDAO.getOriginVeh(vehicle);
		}else if (type.equals("Motorcycle")) {
			motor = motorDAO.getOriginVeh(vehicle);
		}else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error getting detailed specs", null));		
		}
	}
	


}
