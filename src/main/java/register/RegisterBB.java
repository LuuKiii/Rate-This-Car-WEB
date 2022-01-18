package register;



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


import jsf.dao.UserDAO;
import jsf.entities.User;

@Named
@RequestScoped
public class RegisterBB implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_MAIN = "/pages/public/carList?faces-redirect=true";

	
	private String name;
	private String mail;
	private String pass;
	
	private User user = new User();
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


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
	

	public String createUser() {
		List<User> usernames = userDAO.usernameExists(name);
		List<User> mails = userDAO.mailExists(mail);
		
		if(!usernames.isEmpty()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This username is taken",null));
		}else if(!mails.isEmpty()){
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This E-mail address is already in use",null));

		}else {
			user.setUserName(name);
			user.setEmail(mail);
			user.setPassword(pass);
			user.setRole("user");
			
			
			try {
				userDAO.create(user);
				return PAGE_MAIN;
			}catch(Exception e) {
				e.printStackTrace();
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
				

			}
			
		}
		
		return PAGE_STAY_AT_THE_SAME;

	}
	
		

}
