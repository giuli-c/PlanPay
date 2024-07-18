package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.CalendarImpl;
import pp.projects.model.CalendarP;
import pp.projects.model.Event;
import pp.projects.model.EventAlreadyExistsException;
import pp.projects.model.InvalidParameterException;
import pp.projects.model.State;

class EventFileTest {
	private CalendarP calendar;
    private String tempFilePath;
    
    private Event event1;
    private Event event2;

	@BeforeEach
	 public void setUp() throws IOException, EventAlreadyExistsException, InvalidParameterException{
		calendar = new CalendarImpl(0, "");
        tempFilePath = "src/resource/test_events.txt";
        calendar.setPathEvents(tempFilePath);
        
        event1 = calendar.newEvent("", LocalDate.of(2024, 6, 21), "00:00", "Test Event 1", "Description 1", "10:00", "11:00", State.IN_CORSO, "Test Event 1 " + LocalDate.of(2024, 6, 21).toString());
        event2 = calendar.newEvent("", LocalDate.of(2024, 6, 22), "00:00", "Test Event 2", "Description 2", "12:00", "13:00", State.DA_AVVIARE, "Test Event 2 " + LocalDate.of(2024, 6, 22).toString());
	}

    @Test
    public void testSaveEventsToFile() {
        boolean saveSuccess = calendar.saveEventsToFile();
        assertTrue(saveSuccess);
                
        File file = new File(tempFilePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void testLoadEventsFromFile() {
        Set<Event> loadedEvents = calendar.loadEventsFromFile();

        assertTrue(loadedEvents.contains(event1));
        assertTrue(loadedEvents.contains(event2));        
    }
}
