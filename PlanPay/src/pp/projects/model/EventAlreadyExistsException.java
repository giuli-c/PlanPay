package pp.projects.model;

public class EventAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public EventAlreadyExistsException(String message) {
        super(message);
    }
}
