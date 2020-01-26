package main.resources;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.IngredientNotFoundException;

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
			this.getIngredients().put(i.getKey(), i.getValue());
		}
	}

	public void deleteIngredient(String ingredient) {
		try {
			if (!this.ingredients.containsKey(ingredient)) {
				throw new IngredientNotFoundException();
			}

			this.ingredients.remove(ingredient);

		} catch(IngredientNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage(new HashMap<String, Double>());
		}

		return instance;
	}


	//Starting here, for only testing purpose
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Storage other = (Storage) obj;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} 
		else if (!ingredients.equals(other.ingredients))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ingredients = " + ingredients;
	}	
}

