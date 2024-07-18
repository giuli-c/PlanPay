package pp.projects.model;

import java.util.Map;

public interface Login {
	
	/**
	 *  Metodo usato per caricare le credenziali da un file.
	 */
	void loadCredential();			
	
	/**
	 * 
	 * @param u
	 * @param p
	 * @return 	di un booleano per sapere se le credenziali sono valide.
	 */
	boolean valideAuthenticate(String u, String p);		
	
	/**
	 * 
	 * @param u
	 * @param p
	 * @param n
	 * @returndi un booleano per sapere se l'utente è stato registrato. Il metodo viene usato anche per la registrazione dello stesso.
	 */
	boolean registration(String u, String p, String n);		
	
	/**
	 * Metodo usato per salvare le credenziali su un file. Richiamato ad ogni nuova registrazione.
	 * 
	 * @param u
	 * @param p
	 * @param n
	 */
	void saveCredential(String u, String p, String n);
	
	/**
	 * 
	 * @return il nome dell'intestatario del conto.
	 */
	String getAccountName();
	
	/**
	 * 
	 * @return le credenziali dell'utente (metodo usato per i test)
	 */
	Map<String, UserCredentials> getCredential();
	
	/**
	 * setta il file di testo per i test. > Viene usato un altro file: test_events.
	 * @param tmp
	 */
	void setPath(String tmp);
	
	/**
	 * 
	 * @param user
	 * @return il percorso del file collegato all'utente.
	 */
	String getEventsFilePath(String user);
}
