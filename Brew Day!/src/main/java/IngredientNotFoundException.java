package main.java;

@SuppressWarnings("serial")
public class IngredientNotFoundException extends RuntimeException {
	
	public IngredientNotFoundException() {
		super("Ingredient not found");
	}
	
	public IngredientNotFoundException(String message) {
		super(message);
	}
}
