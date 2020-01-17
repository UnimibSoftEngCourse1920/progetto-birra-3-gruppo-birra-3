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


	private int id;
	private String name;
	private Map<String,Double> ingredients;
	private Equipment equipment;
	private Storage storage;
	private double countBrew = 1;
	private static final AtomicInteger count = new AtomicInteger(0); //this serves to autoincrement the id
	private static final long serialVersionUID = 1L;

	public Recipe(String name, Map<String, Double> ingredients) {
		super();
		this.id = count.incrementAndGet();
		this.name = name;
		this.ingredients = ingredients;
		this.equipment = Equipment.getInstance();
		this.storage = Storage.getInstance();
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

	public Map<String,Double> getIngredients() {
		return ingredients;
	}

	public double getCountBrew() {
		return countBrew;
	}

	public void setIngredients(Map<String, Double> ingredients) {
		this.ingredients = ingredients;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void updateRecipe(String name, Map<String, Double> ingredients) {
		this.setName(name);
		this.setIngredients(ingredients);
	}


	public Brew createBrew(){
		Map<String,Double> missingIngredients = computeMissingIngredients(storage.getIngredients());
		if(missingIngredients.isEmpty()) {
			Date currentDate = new Date(System.currentTimeMillis());
			Brew b = new Brew(this, currentDate);
			countBrew++;
			return b;
		}
		else {
			for(Entry<String, Double> i : missingIngredients.entrySet()) {
				System.out.println("Need: " + i.getKey() + " " + missingIngredients.get(i.getKey()));
			}
			return null;
		}
	}

	public Map<String, Double> computeMissingIngredients(Map<String, Double> availableIngredients){
		Map<String,Double> missingIngredients = new HashMap<>();
		Map<String,Double> storageIngredients = storage.getIngredients();

		for(Entry<String, Double> i : this.ingredients.entrySet()) {
			if (!storageIngredients.containsKey(i.getKey())) {
				missingIngredients.put(i.getKey(), i.getValue());
			} else {
				Double ingredientValue = storage.getIngredients().get(i.getKey()).doubleValue();
				if (ingredientValue < i.getValue()) {
					missingIngredients.put(i.getKey(), i.getValue() - ingredientValue);
				}
			}
		}

		return missingIngredients;
	}

	//Created only for testing purpose
	public void incrementCountBrew() {
		countBrew++;
	}

	@Override
	public String toString() {
		return "id = " + id + ", name = " + name + ", ingredients = " + ingredients;
	}
}
