package main.resources;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.io.Serializable;
import main.NullIngredientsException;;

public class Storage implements Serializable{

	private static final long serialVersionUID = 4L;
	private Map<String,Double> ingredients;
	private static Storage instance;

	private Storage(Map<String, Double> ingredients) {
		super();
		try {
			if (ingredients == null) {
				throw new NullIngredientsException();
			}
			
			this.ingredients = ingredients;
			
		} catch (NullIngredientsException e){
			System.err.println(e.getMessage());
		}
	}

	public Map<String, Double> getIngredients() {
		return this.ingredients;
	}


	public void setIngredients(Map<String, Double> ingredients) {
		try {
			if (ingredients == null) {
				throw new NullIngredientsException();
			}
			
			this.ingredients = ingredients;
			
		} catch (NullIngredientsException e){
			System.err.println(e.getMessage());
		}
	}

	public void updateIngredients(Map<String, Double> ingredients) {
		try {
			if (ingredients == null) {
				throw new NullIngredientsException();
			}
			
			for(Entry<String, Double> i : ingredients.entrySet()) {
				this.getIngredients().put(i.getKey(), i.getValue());
			}
			
		} catch (NullIngredientsException e){
			System.err.println(e.getMessage());
		}
		
		
	}

	public void deleteIngredient(String ingredient) {
		try {
			if (ingredients == null) {
				throw new NullIngredientsException();
			}
			
			this.ingredients.remove(ingredient);
			
		} catch (NullIngredientsException e){
			System.err.println(e.getMessage());
		}
	}
	
	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage(new HashMap<String, Double>());
		}

		return instance;
	}
	
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
		} else if (!ingredients.equals(other.ingredients))
				return false;
		return true;
	}

	@Override
	public String toString() {
		return "ingredients = " + ingredients;
	}	
}

