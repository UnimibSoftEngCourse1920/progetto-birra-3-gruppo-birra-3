package main.recipes;

import main.instrument.Equipment;
import main.resources.Storage;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class Recipe implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	HashMap<String,Double> ingredients;
	private Equipment equipment;
	private Storage storage;
	private static final AtomicInteger count = new AtomicInteger(0); //this serve to autoincrement the id
	
	public Recipe(String name, HashMap<String, Double> ingredients) {
		super();
		this.id = count.incrementAndGet();
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

	public Equipment getEquipment() {
		return equipment;
	}
	
	public HashMap<String,Double> getIngredients() {
		return this.ingredients;
	}
	
	public void setIngredients(HashMap<String, Double> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public HashMap<String, Double> computeMissingIngredients(HashMap<String, Double> availableIngredients){
		HashMap<String,Double> results = new HashMap<>();
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
	
	public void updateRecipe(String name, HashMap<String, Double> ingredients) {
		this.setName(name);
		this.setIngredients(ingredients);
	}
	
    @Override
	public String toString() {
		return "id = " + id + ", name = " + name + ", ingredients = " + ingredients;
	}
}
