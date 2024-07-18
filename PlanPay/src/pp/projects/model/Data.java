package pp.projects.model;

import java.time.LocalDate;

public interface Data {
	/**
	 * @return del nome
	 */
	String getName();
	
	/**
	 * modifica il nome
	 * 
	 * @param name = nome da modificare
	 */
	void setName(String name);
	
	/**
	 * @return della descrizione 
	 */
	String getDescription();
	
	/**
	 * modifica la descrizione
	 * 
	 * @param d = descrizione da modificare
	 */
	void setDescription(String d);
	
	/**
	 * @return della data 
	 */
	LocalDate getDate();
}
