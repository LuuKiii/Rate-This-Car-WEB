package addVehicle;



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
import jsf.entities.Truck;
import jsf.dao.MotorDAO;
import jsf.entities.Motor;

@Named
@ViewScoped
public class AddVehicleBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_MAIN = "/pages/public/carList?faces-redirect=true";
	private static final String PAGE_PRODUCER = "/pages/admin/producer?faces-redirect=true";

	
	private int prod;
	
	public int getProd() {
		return prod;
	}
	public void setProd(int prod) {
		this.prod = prod;
	}
	
	
	
	
	


	private Producer producer = new Producer();
	
	private Vehicle vehicle = new Vehicle();
	
	private Car car = new Car();
	
	private Truck truck = new Truck();
	
	private Motor motor = new Motor();
	


	public Vehicle getVehicle() {
		return vehicle;
	}
	

	
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Truck getTruck() {
		return truck;
	}
	public void setTruck(Truck truck) {
		this.truck = truck;
	}
	public Motor getMotor() {
		return motor;
	}
	public void setMotor(Motor motor) {
		this.motor = motor;
	}
	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}


	@EJB
	ProducerDAO producerDAO;
	
	@EJB
	VehicleDAO vehicleDAO;
	
	
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
		vehicle.setVehicleType("Car");

	}
		
	public List<Vehicle> getFullList(){
		return vehicleDAO.getAllVehicles();
	}
	
	public String createVeh() {
		producer = producerDAO.find(prod);
		vehicle.setProducer(producer);
		String typeV = vehicle.getVehicleType();
	
			
		if(typeV.equals("Car")) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CAR " + vehicle.getVehicleName(), ""));
			
			try {
				vehicleDAO.create(vehicle);
				car.setVehicle(vehicle);
				carDAO.create(car);
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully added vehicle: " + vehicle.getVehicleName(), ""));
				
			}catch(Exception e){
				e.printStackTrace();
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ""));
				return PAGE_STAY_AT_THE_SAME;
			}
			
			}else if(typeV.equals("Truck")) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TRUCK " + vehicle.getVehicleName(), ""));
				
				try {
					vehicleDAO.create(vehicle);
					truck.setVehicle(vehicle);
					truckDAO.create(truck);
					ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully added vehicle: " + vehicle.getVehicleName(), ""));
					
				}catch(Exception e){
					e.printStackTrace();
					ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ""));
					return PAGE_STAY_AT_THE_SAME;
				}

			}else if(typeV.equals("Motorcycle")){
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "MOTOR " + vehicle.getVehicleName(), ""));
				
				try {
					vehicleDAO.create(vehicle);
					motor.setVehicle(vehicle);
					motorDAO.create(motor);
					ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully added vehicle: " + vehicle.getVehicleName(), ""));
					
				}catch(Exception e){
					e.printStackTrace();
					ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ""));
					return PAGE_STAY_AT_THE_SAME;
				}
				

			}else {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error passing vehicle type", ""));
				return PAGE_STAY_AT_THE_SAME;
			}


		
		
		return PAGE_MAIN;
	}
	



}
