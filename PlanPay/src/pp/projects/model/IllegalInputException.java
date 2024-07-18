package pp.projects.model;

public class IllegalInputException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalInputException(String message){
		super(message);
	}
}
