package carList;



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
@RequestScoped
public class CarListBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_ADD_VEHICLE = "/pages/admin/addVehicle?faces-redirect=true";


	
	private int prod;
	
	private Producer producer = new Producer();
	
	private Vehicle vehicle = new Vehicle();
	
	private UserRatesVehicle rates = new UserRatesVehicle();
	


	public Vehicle getVehicle() {
		return vehicle;
	}
	
	


	public int getProd() {
		return prod;
	}


	public void setProd(int prod) {
		this.prod = prod;
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
	
	public List<Vehicle> getVehicleList(){
		return vehicleDAO.getAllVehicles();
	}
	
	public String getProducerName(Producer producer) {
		Producer p = producerDAO.find(producer.getIdproducer());
		return  p.getProducerName();
	}
	
	public String addVehicle(){
		
		return PAGE_ADD_VEHICLE;
	}
	
	
	public String deleteVeh(Vehicle vehicle) {
		String typeveh = vehicle.getVehicleType();
		List<UserRatesVehicle> ratedvehicles = rateDAO.getRatedVeh(vehicle);
		
		while(!ratedvehicles.isEmpty()) {
			rateDAO.remove(ratedvehicles.get(0));
			ratedvehicles = rateDAO.getRatedVeh(vehicle);
		}
		
		
		
		if(typeveh.equals("Car")) {
			carDAO.remove(carDAO.getOriginVeh(vehicle));
			vehicleDAO.remove(vehicle);			
			
		}else if(typeveh.equals("Truck")) {
			truckDAO.remove(truckDAO.getOriginVeh(vehicle));
			vehicleDAO.remove(vehicle);		
		}else {
			motorDAO.remove(motorDAO.getOriginVeh(vehicle));
			vehicleDAO.remove(vehicle);		
		}
		return PAGE_STAY_AT_THE_SAME;
	}


}
