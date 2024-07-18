package pp.projects.model;

public class IllegalOperationException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalOperationException(String message){
		super(message);
	}
}
