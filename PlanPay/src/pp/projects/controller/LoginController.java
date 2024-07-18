package pp.projects.controller;

import pp.projects.model.AuthenticationException;
import pp.projects.model.RegistrationException;
import pp.projects.view.ConsolleView;

public interface LoginController {
	
	/**
	 * Gestione del login e controllo dei dati.
	 * 
	 * @param user
	 * @param passw
	 * @return
	 * @throws IllegalArgumentException
	 * @throws AuthenticationException
	 */
	boolean loginButtonClick(String user, String passw) throws IllegalArgumentException, AuthenticationException;
	
	/**
	 * Gestione della visibilità delle form. Ho già un riferimento a tutte le form.
	 */
	void newSignupButtonClick();
	
	/**
	 * Gestione della registrazione e controllo dei dati
	 * 
	 * @param user
	 * @param passw
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 * @throws RegistrationException
	 */
	boolean signupButtonClick(String user, String passw, String name) throws IllegalArgumentException, RegistrationException;
	
	/**
	 * 
	 * @return il nome dell'intestatario del conto.
	 */
	String getUserName();
	
	/**
	 * 
	 * @return la consolleView.
	 */
	ConsolleView getConsolleView();
}
