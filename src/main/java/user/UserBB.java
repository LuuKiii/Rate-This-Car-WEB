package user;



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
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;


import jsf.dao.UserDAO;
import jsf.dao.UserRatesVehicleDAO;
import jsf.dao.VehicleDAO;
import jsf.entities.Producer;
import jsf.entities.User;
import jsf.entities.UserRatesVehicle;
import jsf.entities.Vehicle;

@Named
@RequestScoped
public class UserBB {
	private static final String PAGE_ADVANCED = "/pages/user/advanced?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User user = new User();
	private UserRatesVehicle rates = new UserRatesVehicle();
	private RemoteClient<User> userTemp = new RemoteClient<User>();
	
	private Vehicle vehicle = new Vehicle();
	
	
	
	public Vehicle getVehicle() {
		return vehicle;
	}


	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


	@Inject
	ExternalContext extcontext;
	
	@Inject
	FacesContext ctx;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
	
	@EJB
	VehicleDAO vehicleDAO;
	
	@EJB
	UserRatesVehicleDAO rateDAO;
	
	public User getCurrentUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		userTemp = (RemoteClient<User>)session.getAttribute("remoteClient");
		user = userDAO.find(userTemp.getDetails().getIduser());
		
		return user;
	}
	
	
	public List<UserRatesVehicle> getUsersList(){
		return rateDAO.getUsersList(getCurrentUser());
	}
		
	

	
	public Vehicle getVeh(int idv) {
		Vehicle veh = vehicleDAO.find(idv);
		return veh;
	}
	
	public int intPars(double val) {
		int transval = (int) val;
		return transval;
	}
	
	public String advanced(UserRatesVehicle instance){
		flash.put("user_rates_vehicle", instance);

		return PAGE_ADVANCED;
	}
	
	public String deleteRate(UserRatesVehicle instance) {
		rateDAO.remove(instance);
		
		return PAGE_STAY_AT_THE_SAME;
	}

}
