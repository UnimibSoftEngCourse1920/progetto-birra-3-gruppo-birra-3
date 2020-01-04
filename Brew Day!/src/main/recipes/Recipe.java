package main.recipes;

import main.instrument.Equipment;
import main.resources.Storage;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Recipe implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	Map<String,Double> ingredients = new HashMap<>();
	private Equipment equipment;
	private Storage storage;
	
	public Recipe(int id, String name, Map<String, Double> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
	}

	public int getId() {
		return id;
	}	

	public String getName() {
		return name;
	}

	public double getQuantity(String name) {
		return ingredients.get(name);
	}

	public void setIngredient(String name, double quantity) {
		if(ingredients.get(name) == null) {
			ingredients.put(name, quantity);
		}
		else {
			ingredients.replace(name, quantity);
		}
	}

	public Equipment getEquipment() {
		return equipment;
	}
	
	public Map<String, Double> computeMissingIngredients(Map<String, Double> availableIngredients){
		Map<String,Double> results = new HashMap<>();
		Double available;
		Double needed;
		
		for(Entry<String, Double> i : ingredients.entrySet()) {
			available = availableIngredients.get(i.getKey());
			if(available != null) {
				needed = ingredients.get(i.getKey());
				if(available - needed > 0) {
					results.put(i.getKey(), available - needed);
				}
			}
		}
		
		return results;
	}
	
	public Brew createBrew(double id){
		Map<String,Double> missingIngredients = new HashMap<>();
		missingIngredients = computeMissingIngredients(storage.getIngredients());
		if(missingIngredients.isEmpty()) {
			Date currentDate = new Date(System.currentTimeMillis());
			Brew b = new Brew(id, this, currentDate);
			return b;
		}
		else {
			for(Entry<String, Double> i : missingIngredients.entrySet()) {
				System.out.println(i.getKey() + "   " + missingIngredients.get(i.getKey()));
			}
			return null;
		}
	}
	
	public void storeRecipe() {
		
	}
	
	public void updateRecipe() {
		
	}
	
	public void deleteRecipe() {
	
	}
	
    //Created only for testing purpose
	@Override
	public String toString() {
		String ingredientsString = "";
		for(Entry<String, Double> i : ingredients.entrySet()) {
			ingredientsString += i.getKey() + "   " + ingredients.get(i.getKey()) + ", ";
		}
		return "Recipe [id=" + id + "; name=" + name + "; ingredients=" + ingredientsString + "]";
	}
}
