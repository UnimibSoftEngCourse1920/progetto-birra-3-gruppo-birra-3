package main;

@SuppressWarnings("serial")
public class NullIngredientsException extends Exception {
	
	public NullIngredientsException() {
		super("Impossibile to use null ingredients!");
	}
	
	public NullIngredientsException(String message) {
		super(message);
	}

}
