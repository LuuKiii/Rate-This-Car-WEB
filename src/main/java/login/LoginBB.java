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
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;


import jsf.dao.UserDAO;
import jsf.entities.User;

@Named
@RequestScoped
public class LoginBB {
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
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
	
	public void loginAction() {
		
		List<User> users = userDAO.userExists(mail , pass);
		
		if(users.isEmpty()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong E-mail or Password",null));
		} else {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok",null));
		}
	}
		

}
