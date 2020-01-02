package main.recipes;

import java.util.HashMap;
import java.util.Map;

import main.instrument.Equipment;

public class Recipe {
	
	private int id;
	private String name;
	Map<String,Double> ingredients = new HashMap<>();
	Map<String,Double> missingIngredients = new HashMap<>();
	private Equipment equipment;
	private RecipeController Rcontroller;
	
	public Recipe(int id, String name, Map<String, Double> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuantity(String name) {
		ingredients.get(name);
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
	
	public static Map<String, Double> computeMissingIngredients(Map<String, Double> AvailableIngredients){
		//Must be completed
	}
}
