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

@Named
@RequestScoped
public class CarListBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_MAIN = "/pages/public/carList?faces-redirect=true";
	private static final String PAGE_PRODUCER = "/pages/admin/producer?faces-redirect=true";
	private static final String PAGE_ADD_VEHICLE = "/pages/admin/addVehicle?faces-redirect=true";


	
	private int prod;
	
	private Producer producer = new Producer();
	
	private Vehicle vehicle = new Vehicle();
	


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
	
	@Inject
	FacesContext ctx;
		
	@Inject
	Flash flash;
	
	public String addVehicle(){
		
		return PAGE_ADD_VEHICLE;
	}


}
