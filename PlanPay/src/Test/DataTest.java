package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.EventImpl;
import pp.projects.model.State;
import pp.projects.model.TransactionImpl;

class DataTest {

	private EventImpl event;
	private TransactionImpl transaction;
	
	@BeforeEach
    public void setUp() {
		event = new EventImpl("Urbino", "Consegna progetto PMO", LocalDate.of(2024, 07, 12), "09:00", "12:00", State.DA_AVVIARE, "Urbino " + LocalDate.of(2024, 07, 12).toString());
		transaction = new TransactionImpl(LocalDate.now(), "Deposito Obbiettivo viaggio", 100.0);
	}
	
	@Test
	void eventTest() {
		assertEquals("Urbino", event.getName());
		event.setName("Urbino per PMO");
	    assertEquals("Urbino per PMO", event.getName());
	    assertEquals("Consegna progetto PMO", event.getDescription());
	    assertEquals(LocalDate.of(2024, 07, 12), event.getDate());
	    assertEquals(State.DA_AVVIARE, event.getState());
	    event.setState(State.IN_CORSO);
        assertEquals(State.IN_CORSO, event.getState());
        assertEquals("09:00", event.getDaOra());
        event.setDaOra("08:30");
        assertEquals("08:30", event.getDaOra());
        assertEquals("08:30 - 12:00 : 'Urbino per PMO'", event.getInfoEventToString());
	}
	
	@Test
    public void eventEqualsTest() {
        EventImpl sameEvent = new EventImpl("Urbino", "Consegna progetto PMO", LocalDate.of(2024, 07, 12), "09:00", "12:00", State.DA_AVVIARE, "Urbino " + LocalDate.of(2024, 07, 12).toString());
        assertTrue(event.equals(sameEvent));

        EventImpl differentEvent = new EventImpl("Workshop", "Technical workshop", LocalDate.of(2024, 6, 21), "10:00", "11:00", State.DA_AVVIARE, "Workshop20240621");
        assertFalse(event.equals(differentEvent));
    }

    @Test
    public void eventHashCodeTest() {
        EventImpl sameEvent = new EventImpl("Urbino", "Consegna progetto PMO", LocalDate.of(2024, 07, 12), "09:00", "12:00", State.DA_AVVIARE, "Urbino " + LocalDate.of(2024, 07, 12).toString());
        assertEquals(event.hashCode(), sameEvent.hashCode());

        EventImpl differentEvent = new EventImpl("Workshop", "Technical workshop", LocalDate.of(2024, 6, 21), "10:00", "11:00", State.DA_AVVIARE, "Workshop20240621");
        assertNotEquals(event.hashCode(), differentEvent.hashCode());
    }
    
    @Test
    public void transactionTest() {
        assertEquals("Deposito Obbiettivo viaggio", transaction.getName());
        transaction.setName("Prelievo Servizio");
        assertEquals("Prelievo Servizio", transaction.getName());
        assertEquals(LocalDate.now(), transaction.getDate());
        assertEquals(100.0, transaction.getAmount());
    }
}
