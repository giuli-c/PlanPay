package pp.projects.controller;

import java.io.IOException;

import pp.projects.model.AuthenticationException;
import pp.projects.model.Login;
import pp.projects.model.LoginImpl;
import pp.projects.model.RegistrationException;
import pp.projects.view.ConsolleView;
import pp.projects.view.LoginView;
import pp.projects.view.SignupView;

public class LoginControllerImpl implements LoginController{
	private Login loginModel;
	private LoginView loginView;
	private SignupView signupView;
	private String userName;
	private ConsolleView consolleView;
	private ConsoleController controller;
	
	public LoginControllerImpl() throws IOException {
		this.loginModel = new LoginImpl();
		this.loginView = new LoginView(this);
		this.signupView = new SignupView(this);
		this.userName = "";
		this.controller = new ConsoleControllerImpl(this);
		this.consolleView = new ConsolleView(controller, controller.getAccount());
	}
	
	@Override
	public boolean loginButtonClick(String user, String passw) throws IllegalArgumentException, AuthenticationException{
		boolean isAuthenticated = false;
		if (isValidString(user) && isValidString(passw) && !user.contains(" ")) {
            isAuthenticated = loginModel.valideAuthenticate(user, passw);
            if (isAuthenticated) {
                this.userName = loginModel.getAccountName();
                consolleView.setVisible(true);
                return true;
            } else {
                throw new AuthenticationException("Credenziali non valide.");
            }
        } else {
            throw new IllegalArgumentException("Nome utente e password non possono essere vuoti o contenere spazi.");
        }
	}
	
	@Override
	public void newSignupButtonClick() {
        signupView.setVisible(true);
        loginView.setVisible(false);
	}
	
	@Override
	public boolean signupButtonClick(String user, String passw, String name) throws IllegalArgumentException, RegistrationException {
		boolean isRegistred = false;
		
		if (isValidString(user) && isValidString(passw) && isValidString(name) && !user.contains(" ")) {
            isRegistred = loginModel.registration(user, passw, name);
            if (isRegistred) {
                return true;
            } else {
                throw new RegistrationException("Registrazione non riuscita.");
            }
        } else {
            throw new IllegalArgumentException("Nome utente, password e nome non possono essere vuoti o contenere spazi.");
        }
	}
	
	@Override
	public String getUserName() {
		return this.userName;
	}
	
	@Override
	public ConsolleView getConsolleView() {
		return this.consolleView;
	}
	
	private boolean isValidString(String str) {
	     return str != null && !str.trim().isEmpty();
	}
	
	public Login getLoginModel() {
		return this.loginModel;
	}
}
