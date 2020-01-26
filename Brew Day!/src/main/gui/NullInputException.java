package main.gui;

@SuppressWarnings("serial")
public class NullInputException extends Exception {
	public NullInputException() {
		super();
	}
	
	public NullInputException(String message) {
		super(message);
	}
}
