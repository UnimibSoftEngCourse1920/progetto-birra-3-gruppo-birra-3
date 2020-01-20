package main.instrument;

@SuppressWarnings("serial")
public class NullInstrumentsException extends Exception {
	public NullInstrumentsException() {
		super();
	}
	
	public NullInstrumentsException(String message) {
		super(message);
	}
}
