package rating;



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
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

import jsf.dao.VehicleDAO;
import jsf.entities.Vehicle;
import jsf.dao.UserDAO;
import jsf.entities.User;

import jsf.dao.UserRatesVehicleDAO;
import jsf.entities.UserRatesVehicle;




@Named
@RequestScoped
public class RateBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_ADD_VEHICLE = "/pages/admin/addVehicle?faces-redirect=true";


	

	private Vehicle vehicle = new Vehicle();
	
	
	private User user = new User();
	
	private UserRatesVehicle rates = new UserRatesVehicle();

	private RemoteClient<User> userTemp = new RemoteClient<User>();

	
	private int rating = 0;
	


	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	





	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}







	@EJB
	VehicleDAO vehicleDAO;
	
	@EJB
	UserRatesVehicleDAO rateDAO;
	
	@EJB
	UserDAO userDAO;
	
	@Inject
	FacesContext ctx;
		
	@Inject
	Flash flash;
	

	
	public int overallrating(Vehicle vehicle) {
		double ratingTemp = 0;
		List<UserRatesVehicle> list = rateDAO.getRatedVeh(vehicle);
		
		for(UserRatesVehicle rate:list) {
			ratingTemp += rate.getRateOverall();
		}
		
		ratingTemp /= list.size();
		
		return (int) Math.round(ratingTemp);
		
	}
	
	public User getCurrentUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		userTemp = (RemoteClient<User>)session.getAttribute("remoteClient");
		user = userDAO.find(userTemp.getDetails().getIduser());
		
		return user;
	}
	
	public String onRate(Vehicle vehicle) {

		user = getCurrentUser();
			
		rates.setVehicle(vehicle);
		rates.setUser(user);
		rates.setRateOverall(rating);
			
			
		if(rateDAO.getRating(vehicle, user) != null) {
			try {
				rates.setIduserRates(rateDAO.getRating(vehicle, user).getIduserRates());
				rateDAO.merge(rates);
			}catch(Exception e) {
				e.printStackTrace();
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ""));
				return PAGE_STAY_AT_THE_SAME;
			}
			
		}else {

			try {
				rateDAO.create(rates);
			}catch(Exception e) {
				e.printStackTrace();
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ""));
				return PAGE_STAY_AT_THE_SAME;
			}
		}
			
		//}

		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String deleteRate(Vehicle vehicle) {
		user = getCurrentUser();
		
		if(rateDAO.getRating(vehicle, user) != null) {
			rateDAO.remove(rateDAO.getRating(vehicle, user));
		}
		
		return PAGE_STAY_AT_THE_SAME;
	}


}
