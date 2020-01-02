package main.resources;
import java.util.HashMap;
import java.util.Map.Entry;

import main.DatabaseController;

public class Storage {

	private HashMap<String,Double> ingredients;
	private static DatabaseController controller;
	private static StorageObserver observer;
	private static Storage instance;
	
	private Storage(HashMap<String, Double> ingredients) {
		super();
		this.ingredients = ingredients;
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
		
		this.observer.notify(getInstance());
	}

	public void store() {
		//this.controller.execute();
	}
	
	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage(new HashMap<String, Double>());
		}
		
		if (controller == null) {
			controller = controller.getInstance();
		}
		
		if (observer == null) {
			observer = new StorageObserver();
		}
		
		return instance;
	}
}

