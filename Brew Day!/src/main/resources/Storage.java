package main.resources;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.io.Serializable;

public class Storage implements Serializable{

	private static final long serialVersionUID = 4L;
	private Map<String,Double> ingredients;
	private static Storage instance;

	private Storage(Map<String, Double> ingredients) {
		super();
		this.ingredients = ingredients;
	}

	public Map<String, Double> getIngredients() {
		return this.ingredients;
	}


	public void setIngredients(Map<String, Double> ingredients) {
		this.ingredients = ingredients;
	}

	public void updateIngredients(Map<String, Double> ingredients) {
		for(Entry<String, Double> i : ingredients.entrySet()) {
			getIngredients().put(i.getKey(), i.getValue());
		}
	}

	public void deleteIngredient(String ingredient) {
		this.ingredients.remove(ingredient);
	}
	
	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage(new HashMap<String, Double>());
		}

		return instance;
	}

	//for only testing purpose
	@Override
	public String toString() {
		return "ingredients = " + ingredients;
	}	
}

