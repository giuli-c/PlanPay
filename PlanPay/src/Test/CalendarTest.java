package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.CalendarImpl;
import pp.projects.model.CalendarModel;
import pp.projects.model.CalendarP;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.EventNotFoundException;
import pp.projects.model.InvalidParameterException;
import pp.projects.model.State;

class CalendarTest {

	private CalendarP calendario;
	private CalendarModel calendarModel;
	private Event meeting1;
	private Event meeting2;
	private Event meeting3;
	private Event presentaz1;
	private Event presentaz2;
	private Event viaggio1;
	private Event viaggio2;
	private Event viaggio3;
	private Event eventTest;
	private Event eventTest2;
	
	@BeforeEach
	 public void setUp() throws EventAlreadyExistsException, InvalidParameterException {
		// rimuovo tutti gli eventi che ho precdentemente salvato
		calendario = new CalendarImpl(0, "");
		calendarModel = new CalendarModel(2024, 7);
		CalendarImpl cale = (CalendarImpl) calendario;
		cale.deleteAll();
		
		meeting1 = calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Meeting lavorativo", "Metting di 3 giorni presso il palacongressi di Riccione.", "17:00", "18:00", State.DA_AVVIARE, "Meeting lavorativo " + LocalDate.now().plusDays(1).toString());
	    meeting2 = calendario.newEvent("", LocalDate.now().plusDays(2), "00:00", "Meeting lavorativo", "Metting di 3 giorni presso il palacongressi di Riccione.", "17:00", "18:00", State.DA_AVVIARE, "Meeting lavorativo " + LocalDate.now().plusDays(1).toString());
	    meeting3 = calendario.newEvent("", LocalDate.now().plusDays(3), "00:00", "Meeting lavorativo", "Metting di 3 giorni presso il palacongressi di Riccione.", "17:00", "18:00", State.DA_AVVIARE, "Meeting lavorativo " + LocalDate.now().plusDays(1).toString());
	    presentaz1 = calendario.newEvent("", LocalDate.now(), "00:00", "Preprazione presentazione meeting", "", "08:00", "09:00", State.IN_CORSO, "Preprazione presentazione meeting " + LocalDate.now());
	    presentaz2 = calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Preprazione presentazione meeting", "", "08:00", "09:00", State.IN_CORSO, "Preprazione presentazione meeting " + LocalDate.now());
	    viaggio1 = calendario.newEvent("", LocalDate.of(2024, 04, 25), "00:00", "00:00", "Viaggio Parigi", "12:00", "15:00", State.CONCLUSO, "Viaggio Parigi " + LocalDate.of(2024, 04, 25).toString());
	    viaggio2 = calendario.newEvent("", LocalDate.of(2024, 04, 26), "00:00", "00:00", "Viaggio Parigi", "12:00", "15:00", State.CONCLUSO, "Viaggio Parigi " + LocalDate.of(2024, 04, 25).toString());
	    viaggio3 = calendario.newEvent("", LocalDate.of(2024, 04, 27), "00:00", "00:00", "Viaggio Parigi", "12:00", "15:00", State.CONCLUSO, "Viaggio Parigi " + LocalDate.of(2024, 04, 25).toString());
	
    	eventTest = calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "evento Test", "", "10:00", "12:00", State.IN_CORSO, "eventoTest " + LocalDate.now().plusDays(1).toString());
        eventTest2 = calendario.newEvent("", LocalDate.now(), "00:00", "evento Test2", "", "15:00", "18:00", State.CONCLUSO, "eventoTest2 " + LocalDate.now().toString());
	}
	
	@Test
    public void testCreateEvent() throws EventAlreadyExistsException, InvalidParameterException {		
        Set<Event> events = calendario.getAllEvents();
        assertTrue(events.contains(meeting1));
        assertTrue(events.contains(meeting2));
        assertTrue(events.contains(meeting3));
        assertTrue(events.contains(presentaz2));
        assertTrue(events.contains(presentaz1));
        assertTrue(events.contains(viaggio1));
        assertTrue(events.contains(viaggio2));
        assertTrue(events.contains(viaggio3));
    }

	@Test
	public void testModifyEventDesc() throws EventAlreadyExistsException, InvalidParameterException, EventNotFoundException {
	    boolean success = false;

	    // Modifica la descrizione degli eventi
	    success = calendario.modifyEvent(meeting1.getName(), meeting1.getDescription(), meeting1.getDate(), meeting1.getDate(),
	    		  						 meeting1.getDaOra(), meeting1.getAOra(), meeting1.getName(), "In questa giornata è stato presentato ....", 
	    		  						 meeting1.getDate(), meeting1.getDaOra(), meeting1.getAOra(), meeting1.getState(), meeting1.getIdentifier());

	    // Verifica che l'operazione è andata a buon fine
	    assertTrue(success);

	    // Verifica che gli eventi originali siano stati aggiornati
	    assertEquals("In questa giornata è stato presentato ....", meeting1.getDescription());
	    assertNotEquals("In questa giornata è stato presentato ....", meeting2.getDescription());
	    assertEquals("Metting di 3 giorni presso il palacongressi di Riccione.", meeting2.getDescription());
	    
	    success = calendario.modifyEvent(meeting2.getName(), meeting2.getDescription(), meeting2.getDate(), meeting2.getDate(),
	    								 meeting2.getDaOra(), meeting2.getAOra(), meeting2.getName(), "Workshop relativo a....", 
	    								 meeting2.getDate(), meeting2.getDaOra(), meeting2.getAOra(), meeting2.getState(), meeting2.getIdentifier());

		// Verifica che l'operazione è andata a buon fine
		assertTrue(success);
		
	    assertNotEquals("In questa giornata è stato presentato ....", meeting3.getDescription());
	    assertNotEquals("Workshop relativo a....", meeting3.getDescription());
	    assertEquals("Metting di 3 giorni presso il palacongressi di Riccione.", meeting3.getDescription());
	}
	
	@Test
	public void testModifyEventTime() throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
	    boolean success = calendario.modifyEvent(presentaz1.getName(), presentaz1.getDescription(), presentaz1.getDate(), presentaz1.getDate(), 
	    										 presentaz1.getDaOra(), presentaz1.getAOra(), presentaz1.getName(), presentaz1.getDescription(), 
	    										 presentaz1.getDate(), "09:00", "10:00", presentaz1.getState(), presentaz1.getIdentifier());
	    
	    // Verifica che l'operazione è andata a buon fine
	    assertTrue(success);
	    
	    // Verifica che l'ora sia stata modificata per presentaz1 e presentaz2
	    assertEquals("09:00", presentaz1.getDaOra());
	    assertEquals("10:00", presentaz1.getAOra());
	    assertEquals("09:00", presentaz2.getDaOra());
	    assertEquals("10:00", presentaz2.getAOra());
	}
	
	@Test
	public void testModifyEventState() throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
	    calendario.modifyEvent(presentaz1.getName(), presentaz1.getDescription(), presentaz1.getDate(), presentaz1.getDate(), 
	    					   presentaz1.getDaOra(), presentaz1.getAOra(), presentaz1.getName(), presentaz1.getDescription(), 
	    					   presentaz1.getDate(), presentaz1.getDaOra(), presentaz1.getAOra(), State.CONCLUSO, presentaz1.getIdentifier());
	    
	    // Verifica che l'ora sia stata modificata per presentaz1 e presentaz2
	    assertEquals(State.CONCLUSO, presentaz1.getState());
	    assertEquals(State.CONCLUSO, presentaz2.getState());
	}
	
    @Test
    public void testDeleteActivity() throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
    	Event event1 = calendario.newEvent("", LocalDate.now(), "00:00", "evento multiplo", "", "20:00", "21:00", State.IN_CORSO, "eventoMultiplo " + LocalDate.now().toString());
        Event event2 = calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "evento multiplo", "", "20:00", "21:00", State.IN_CORSO, "eventoMultiplo " + LocalDate.now().toString());
        Event event3 = calendario.newEvent("", LocalDate.now().plusDays(2), "00:00", "evento multiplo", "", "20:00", "21:00", State.IN_CORSO, "eventoMultiplo " + LocalDate.now().toString());
    	
        // Rimuovere solo un giorno dell'evento
        calendario.removeActivity(event2.getName(), event2.getDate(), event2.getDaOra(), event2.getAOra());

        Set<Event> events = calendario.getAllEvents();

        // Verificare che solo il giorno specificato sia stato rimosso
        assertFalse(events.contains(event2));
        assertTrue(events.contains(event1));
        assertTrue(events.contains(event3));

        // Verificare che gli altri eventi siano ancora presenti
        assertTrue(events.contains(meeting1));
        assertTrue(events.contains(meeting2));
        assertTrue(events.contains(meeting3));
        assertTrue(events.contains(presentaz1));
        assertTrue(events.contains(presentaz2));
        assertTrue(events.contains(viaggio1));
        assertTrue(events.contains(viaggio3));
    }

    public void testDeleteEvent() throws EventNotFoundException, EventAlreadyExistsException, InvalidParameterException {
    	// Rimuovi tutti gli eventi con lo stesso identificatore
    	Event event1 = calendario.newEvent("", LocalDate.now(), "00:00", "evento multiplo", "", "20:00", "21:00", State.IN_CORSO, "eventoMultiplo " + LocalDate.now().toString());
        Event event2 = calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "evento multiplo", "", "20:00", "21:00", State.IN_CORSO, "eventoMultiplo " + LocalDate.now().toString());
        Event event3 = calendario.newEvent("", LocalDate.now().plusDays(2), "00:00", "evento multiplo", "", "20:00", "21:00", State.IN_CORSO, "eventoMultiplo " + LocalDate.now().toString());

        // Rimuovere solo un giorno dell'evento
    	calendario.removeEvents(event3.getName(), event3.getDate(), event3.getDaOra(), event3.getAOra());
    	Set<Event> events = calendario.getAllEvents();

        // Verificare che tutti gli eventi collegati siano rimossi
        assertFalse(events.contains(event2));
        assertFalse(events.contains(event1));
        assertFalse(events.contains(event3));
	
	    // Verifico che gli altri eventi non siano stati rimossi
	    assertTrue(events.contains(meeting1));
	    assertTrue(events.contains(meeting2));
	    assertTrue(events.contains(meeting3));
	    assertTrue(events.contains(presentaz1));
	    assertTrue(events.contains(presentaz2));
    }
    
	@Test
    public void testException() {
		
		try {
			// NUOVO EVENTO
			calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Ferie", "", "17:00", "18:00", State.DA_AVVIARE, "Ferie " + LocalDate.now().plusDays(1).toString());
		} catch(Exception ex) {
			
		}
		
		// Test per EventAlreadyExistsException
		assertThrows(EventAlreadyExistsException.class, () -> calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Ferie", "", "17:00", "18:00", State.DA_AVVIARE, "Ferie " + LocalDate.now().plusDays(1).toString()));
		
		// Test per InvalidParameterException
		assertThrows(InvalidParameterException.class, () -> calendario.newEvent("", LocalDate.now(), "00:00", "", "Ultima sessione di esami", "08:00", "09:00", State.IN_CORSO, "" + LocalDate.now().toString()));
		assertThrows(InvalidParameterException.class, () -> calendario.newEvent("", LocalDate.now().plusDays(1), "00:00", "Esame PMO", "Ultimo appello.", "", "", State.IN_CORSO, "Esame PMO " + LocalDate.now().plusDays(1).toString()));
		
		// Test per EventNotFoundException durante la modifica di un evento non esistente
		assertThrows(EventNotFoundException.class, () ->
        				calendario.modifyEvent("Laurea", "", LocalDate.of(2024, 10, 25), LocalDate.now().plusDays(1), "08:00", "20:00", "Malattia", 
        									   "Proclamazione tesi", LocalDate.now().plusDays(1), "00:00", "18:00", State.DA_AVVIARE, "Laurea " + LocalDate.of(2024, 10, 25).toString()));
		
		// Test per EventNotFoundException durante la rimozione di un evento non esistente
		assertThrows(EventNotFoundException.class, () ->
        				calendario.removeActivity("Discussione progetto", LocalDate.now(), "00:00", "01:00"));		
		assertThrows(EventNotFoundException.class, () ->
						calendario.removeEvents("Evento inesistente", LocalDate.now().plusDays(1), "23:00", "00:00"));		

	}
	   
    @Test
    public void testGetEventsInDate() {
        LocalDate date = LocalDate.now().plusDays(1);
        calendarModel.setValueAddEvent(date, meeting1);
        calendarModel.setValueAddEvent(date, presentaz2);

        Set<Event> eventsInDate = calendarModel.getEventsInDate(date);

        // Verifico che gli eventi aggiunti siano presenti
        assertNotNull(eventsInDate);
        assertTrue(eventsInDate.contains(meeting1));
        assertTrue(eventsInDate.contains(presentaz2));
        assertFalse(eventsInDate.contains(viaggio1));
    }

    @Test
    public void testGetValueAtDate() {
        LocalDate date = LocalDate.now().plusDays(1);

        Set<Event> events = new HashSet<>();
        events.add(eventTest);
        events.add(eventTest2);

        String event1Html = calendarModel.toHtml(eventTest) + eventTest.getInfoEventToString().replace("\n", " ").replace("\r", " ");
        String event2Html = calendarModel.toHtml(eventTest2) + eventTest2.getInfoEventToString().replace("\n", " ").replace("\r", " ");

        String expectedHtml = "<html>8<br>" + event1Html + "<br>" + event2Html + "</html>";
        Object actualHtml = calendarModel.getValueAtDate(date, events);
        
        assertEquals(expectedHtml, actualHtml);
    }
}
