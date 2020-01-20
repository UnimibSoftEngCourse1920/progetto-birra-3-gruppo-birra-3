package main.recipes;

@SuppressWarnings("serial")
public class BrewNotFoundException extends RuntimeException {

	public BrewNotFoundException() {
		super("Brew not found");
	}

	public BrewNotFoundException(String message) {
		super(message);
	}
}
