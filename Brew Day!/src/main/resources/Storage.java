package main.resources;
import java.util.HashMap;

import java.io.Serializable;

public class Storage implements Serializable{

	private static final long serialVersionUID = 4L;
	private HashMap<String,Double> ingredients;
	private static Storage instance;

	private Storage(HashMap<String, Double> ingredients) {
		super();
		this.ingredients = ingredients;
	}

	public HashMap<String, Double> getIngredients() {
		return this.ingredients;
	}


	public void setIngredients(HashMap<String, Double> ingredients) {
		
	}

	public void updateIngredients(HashMap<String, Double> ingredients) {
		
	}

	public void deleteIngredients(String[] ingredients) {
		
	}
}

