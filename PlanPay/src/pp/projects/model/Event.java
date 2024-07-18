package pp.projects.model;

public interface Event extends Data{
	/**
	 * @return dello stato
	 */
	State getState();
	
	/**
	 * modifica lo stato
	 * 
	 * @param s = stato
	 */
	void setState(State s);
	
	/**
	 * 
	 * @return l'evento in stringa da visualizzare sul calendario.
	 */
	String getInfoEventToString();
	
	/**
	 * 
	 * @return l'orario impostato sull'evento.
	 */
	String getDaOra();
	
	/**
	 * 
	 * @return l'orario impostato sull'evento.
	 */
	String getAOra();
	
	/**
	 * 
	 * @return il formato per la scrittura nel file
	 */
	String getInfoEventToFile();
	
	/**
	 * 
	 * @return l'identificatore, usato per identificare un evento su più giornate.
	 */
	String getIdentifier();
	
	/**
	 * 
	 * @param identifier
	 */
	void setIdentifier(String identifier);
	
	/**
	 * 
	 * @param daOra
	 */
	void setDaOra(String daOra);
	
	/**
	 * 
	 * @param aOra
	 */
	void setAOra(String aOra);
}
