package editProducer;



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

@Named
@ViewScoped
public class EditProducerBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PRODUCER = "/pages/admin/producer?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Producer producer = new Producer();
	private Producer loaded = null;

	@EJB
	ProducerDAO producerDAO;

	@Inject
	FacesContext ctx;

	@Inject
	Flash flash;

	public Producer getProducer() {
		return producer;
	}

	public void onLoad() throws IOException {

		loaded = (Producer) flash.get("producer");

		if (loaded != null) {
			producer = loaded;
		} else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));			
		}

	}

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		try {
				producerDAO.merge(producer);
		
		} catch (Exception e) {
			e.printStackTrace();
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PRODUCER;
	}
}
