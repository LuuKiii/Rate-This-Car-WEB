package userAdvanced;



import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import javax.faces.view.ViewScoped;



import jsf.dao.UserDAO;
import jsf.dao.UserRatesVehicleDAO;
import jsf.dao.VehicleDAO;
import jsf.entities.UserRatesVehicle;


@Named
@ViewScoped
public class AdvancedBB implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String PAGE_STAY_AT_THE_SAME = null;


	private UserRatesVehicle rate = new UserRatesVehicle();
	
	private UserRatesVehicle loaded = null;
	
	private int overall =  (int) Math.round(rate.getRateOverall());
	private int comfort =  (int) Math.round(rate.getRateComfort());
	private int perf =  (int) Math.round(rate.getRatePerformance());
	private int style = (int) Math.round(rate.getRateExteriorStyling());
	private int rel =  (int) Math.round(rate.getRateReliability());
	
	
	
	
	public int getOverall() {
		return overall;
	}
	

	public void setOverall(int overall) {
		this.overall = overall;
	}

	public int getComfort() {
		return comfort;
	}
	public void setComfort(int comfort) {
		this.comfort = comfort;
	}

	public int getPerf() {
		return perf;
	}
	public void setPerf(int perf) {
		this.perf = perf;
	}

	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}

	public int getRel() {
		return rel;
	}
	public void setRel(int rel) {
		this.rel = rel;
	}


	public UserRatesVehicle getRate() {
		return rate;
	}


	public void setRate(UserRatesVehicle rate) {
		this.rate = rate;
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
	
	public void onLoad() throws IOException {

		loaded = (UserRatesVehicle) flash.get("user_rates_vehicle");
		
		if (loaded != null) {
			rate = loaded;
			

		} else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));			
		}
	}
	
	public int intPars(double val) {
		int transval = (int) val;
		return transval;
	}
	
	public String onRateOverall() {
		rate.setRateOverall(overall);
		return onRate();
	}
	public String onRateComfort() {
		rate.setRateComfort(comfort);
		return onRate();
	}
	public String onRatePerf() {
		rate.setRatePerformance(perf);
		return onRate();
	}
	public String onRateStyle() {
		rate.setRateExteriorStyling(style);
		return onRate();
	}
	public String onRateRel() {
		rate.setRateReliability(rel);
		return onRate();
	}
	
	public String onRate() {
		
			try {
				rateDAO.merge(rate);
			}catch(Exception e) {
				e.printStackTrace();
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ""));
				return PAGE_STAY_AT_THE_SAME;
			}
			


		return PAGE_STAY_AT_THE_SAME;
	}

}
