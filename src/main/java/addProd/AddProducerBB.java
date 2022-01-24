package addProd;



import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;



import jsf.dao.ProducerDAO;
import jsf.entities.Producer;

@Named
@RequestScoped
public class AddProducerBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_PRODUCER = "/pages/public/producer?faces-redirect=true";

	

	
	private Producer producer = new Producer();
	


	public Producer getProducer() {
		return producer;
	}

	@EJB
	ProducerDAO producerDAO;
	
	@Inject
	FacesContext ctx;
		
	@Inject
	Flash flash;
	

	
	public List<Producer> getFullList(){
		return producerDAO.getAllProducers();
	}
	

	
	public String createPro() {
		
		if(producer.getProducerDescription() == "") {
			producer.setProducerDescription(null);
		}
		
		if(!(producerDAO.producerExists(producer.getProducerName()).isEmpty())) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This producer already Exists", ""));
		}else {
			try {
				producerDAO.create(producer);
				return PAGE_PRODUCER;
			}catch(Exception e){
				e.printStackTrace();
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ""));
				return PAGE_STAY_AT_THE_SAME;
			}
		}
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String cancel() {
		return PAGE_PRODUCER;
	}


}
