package main.resources;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.Serializable;

public class Storage implements Serializable{

	private static final long serialVersionUID = 4L;
	private HashMap<String,Double> ingredients;
	private static Storage instance;

	private Storage(HashMap<String, Double> ingredients) {
		super();
		this.ingredients = ingredients;
	}
	
	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage(new HashMap<String, Double>());
		}

		return instance;
	}

	public HashMap<String, Double> getIngredients() {
		return this.ingredients;
	}


	public void setIngredients(HashMap<String, Double> ingredients) {
		this.ingredients = ingredients;
	}

	public void updateIngredients(HashMap<String, Double> ingredients) {
		for(Entry<String, Double> i : ingredients.entrySet()) {
			getIngredients().put(i.getKey(), i.getValue());
		}
	}

	public void deleteIngredient(String ingredient) {
		this.ingredients.remove(ingredient);
	}
	
}

