package pp.projects.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import pp.projects.model.Account;
import pp.projects.model.CalendarModel;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.EventNotFoundException;
import pp.projects.model.IllegalOperationException;
import pp.projects.model.InvalidParameterException;
import pp.projects.model.Objective;
import pp.projects.model.OperationType;
import pp.projects.model.State;
import pp.projects.model.Transaction;
import pp.projects.view.CalendarView;

public interface ConsoleController {
	
	/**
	 * 
	 * @param importo
	 * @param tipo
	 * @param nome
	 * @param op = operazione (servzio/obbiettivo)
	 * @return risultato dell'operazione di update del conto
	 * @throws IllegalOperationException
	 */
	boolean updateConto(double importo, boolean tipo, String nome, OperationType op) throws IllegalOperationException;
	
	/**
	 * 
	 * @return la lista di tutti gli obbiettivi salvati (usata nella view consolle obbiettivi)
	 */
	List<Objective> getObjectiveList();
	
	/**
	 * 
	 * @param name
	 * @return l'bbiettivo con il nome specificato.
	 */
	Optional<Objective> getObjective(String name);
	
	/**
	 * 
	 * @param bNew = identifica se si è in modifica/aggiunta
	 * @param nameObjective
	 * @param newDescrOb
	 * @param savingTarget
	 * @throws IllegalStateException
	 */
	void saveObjective(boolean bNew, String nameObjective, String newDescrOb, double savingTarget) throws IllegalStateException;
	
	/**
	 * elimina l'obbiettivo che gli viene passato 
	 * 
	 */
	void removeObjective(String name) throws IllegalStateException;
	
	/**
	 * 
	 * @return tutte le transazioni (obbiettivi e operazioni)
	 */
	List<Transaction> getAllTransactions();
	
	/**
	 * 
	 * @return la stringa usata nella consolle controller per visualizzare le transazioni effettuate.
	 */
	List<String> getDatiTransazione();
	
	/**
	 * 
	 * @return l'account.
	 */
	Account getAccount();
	
	/**
	 * 
	 * @return il nuovo nome del controller.
	 */
	String setNameController();
	
	/**
	 * 
	 * @return il riferimento alla classe CalendarModel. (x interfaccia)
	 */
	CalendarModel getCalendarModel();
	
	/**
	 * 
	 * @return il riferimento alla classe CalendarView (x interfaccia).
	 */
	CalendarView drawCalendar();
	
	/**
	 * 
	 * @return tutti gli eventi caricati dal file.
	 */
	Set<Event> loadEvents();
	
	/**
	 * 
	 * @param bNew = mi tiene traccia per sapere se creare l'evento o modificarlo
	 * @param name = nome dell'evento
	 * @param desc = descrizione dell'evento
	 * @param daData = Da data dell'evento
	 * @param aData = A data dell'evento
	 * @param daOra = da ora dell'evento
	 * @param aOra = a ora dell'evento
	 * @param s = stato dell'evento
	 * @param newName = nome dell'evento in modifica
	 * @param newdesc = descrizione dell'evento in modifica
	 * @param newDaOra = da ora dell'evento in modifica
	 * @param newAora = a ora dell'evento in modifica
	 * @param stato = stato dell'evento in modifica
	 * @param identifier = identificatore per eventi collegati
	 * @throws EventNotFoundException 
	 * @throws EventAlreadyExistsException 
	 * @throws InvalidParameterException 
	 * 
	 * @return tutti gli eventi dopo la modifica / aggiunta.
	 */
	 Set<Event> saveEvent(boolean bNew, String name, String desc, LocalDate daData, LocalDate aData, String daOra, String aOra, 
					    String newName, String newdesc, String newDaOra, String newAora, State stato, String identifier) throws EventAlreadyExistsException, EventNotFoundException, InvalidParameterException;
	
	/**
	 * 
	 * @param name
	 * @param date
	 * @param daOra
	 * @param aOra
	 * @return l'evento singolo rimosso.
	 * @throws EventNotFoundException
	 */
	Event removeActivity(String name, LocalDate date, String daOra, String aOra) throws EventNotFoundException;
	
	/**
	 * 
	 * @param name
	 * @param date
	 * @throws EventNotFoundException
	 * 
	 * @return il set di tutti gli eventi rimossi.
	 */
	Set<Event> removeEvents(String name, LocalDate date, String daOra, String aOra) throws EventNotFoundException;

	/**
	 * aggiorna la UI della consolle con gli eventi presenti nel calendario.
	 */
	void updateUIevents();
	
	/**
	 * 
	 * @return tutti gli eventi presenti nel file.
	 */
	Set<Event> getAllEventToFile();
}
