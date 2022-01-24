package login;



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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import jsf.dao.UserDAO;
import jsf.entities.User;
import passhash.BCrypt;

@Named
@RequestScoped
public class LoginBB {
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_HOME = "/pages/public/homePage?faces-redirect=true";

	private String mail;
	private String pass;
	
	
	


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	@Inject
	ExternalContext extcontext;
	
	@Inject
	FacesContext ctx;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
	

	public List<User> getFullList(){
		return userDAO.getAllUsers();
	}
	
	public String loginAction() {
		ctx = FacesContext.getCurrentInstance();
		List<User> users = userDAO.mailExists(mail);
		
		
		if(users.isEmpty()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong E-mail or Password",null));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		User user = users.get(0);
		if (!BCrypt.checkpw(pass, user.getPassword())) return PAGE_STAY_AT_THE_SAME;
		
		RemoteClient<User> client = new RemoteClient<User>();
		
		client.setDetails(user);
		
		client.getRoles().add(user.getRole());
		
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);
		
		return PAGE_HOME;
		
		
	}
	
	public String logoutAction(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		session.invalidate();
		return PAGE_HOME;
	}
		

}
