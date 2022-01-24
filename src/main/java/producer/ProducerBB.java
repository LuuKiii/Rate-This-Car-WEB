package producer;



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
import jsf.dao.VehicleDAO;
import jsf.entities.Producer;
import jsf.entities.Vehicle;

@Named
@RequestScoped
public class ProducerBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_EDIT_PRODUCER = "/pages/admin/editProducer?faces-redirect=true";
	private static final String PAGE_PRODUCER_DETAIL = "/pages/public/producerDetail?faces-redirect=true";


	
	private Producer producer = new Producer();
	private Producer loaded = null;



	public Producer getProducer() {
		return producer;
	}

	@EJB
	ProducerDAO producerDAO;
	
	@EJB
	VehicleDAO vehicleDAO;
	
	@Inject
	FacesContext ctx;
	
	@Inject
	ExternalContext extcontext;
		
	@Inject
	Flash flash;
	

	public String deletePro(Producer producer) {
		if(vehicleDAO.getProducersVeh(producer).isEmpty()) {
			producerDAO.remove(producer);
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Deletion Successful", null));			

		}else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Can't remove a producer with vehicles assigned to.", null));			

		}
		
		
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String editProducer(Producer producer){
		flash.put("producer", producer);
		
		return PAGE_EDIT_PRODUCER;
	}
	
	public String detProducer(Producer producer){
		flash.put("producer", producer);
		
		return PAGE_PRODUCER_DETAIL;
	}
	
	public void onLoadDet() throws IOException {

		loaded = (Producer) flash.get("producer");
		if (loaded != null) {
			producer = loaded;
		} else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));			
		}

	}
	
	public List<Vehicle> getProducersVehicles(){
		return vehicleDAO.getProducersVeh(producer);
	}



}
