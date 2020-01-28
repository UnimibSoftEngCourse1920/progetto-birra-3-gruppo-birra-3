package main.java.instruments;

@SuppressWarnings("serial")
public class NullInstrumentsException extends Exception {
	public NullInstrumentsException() {
		super();
	}
	
	public NullInstrumentsException(String message) {
		super(message);
	}
}
