package main.recipes;

import java.util.Map;
import java.util.Map.Entry;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import main.IOController;

public class RecipeController {

	private  String filepath;
	private  IOController ioController; 
	private static RecipeController instance;
	
	private RecipeController() {
		super();
		this.filepath = System.getProperty("user.dir") + "\\src\\Files\\Recipe.txt"; 
		this.ioController = new IOController();
	}
	
	public static RecipeController getInstance() {
		if (instance == null) {
			instance = new RecipeController();
		}
		
		return instance;
	}

	@SuppressWarnings("unchecked")
	protected ArrayList<Recipe> extractRecipe() {
		if (ioController.readObjectFromFile(filepath) != null) {
			return (ArrayList<Recipe>) ioController.readObjectFromFile(filepath);
		}

		return new ArrayList<>();
	}
    
	//Protected
	public void store(Recipe recipe) {
		ArrayList<Recipe> recipes = extractRecipe();
		if (!recipes.contains(recipe)) {
			recipes.add(recipe);
			ioController.writeObjectToFile(recipes, filepath);
		}
	}

	protected void update(int id, String name, Map<String,Double> ingredients) {
		ArrayList<Recipe> recipes = extractRecipe();
		boolean found = false;
		try {
			for (int i = 0; i < recipes.size(); i++) {
				if (recipes.get(i).getId() == id) {
					recipes.get(i).updateRecipe(name, ingredients);
					found = true;
				}
			}
			if(!found) {
				throw new RecipeNotFoundException();
			}
		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}

		ioController.writeObjectToFile(recipes, filepath);
	}

	protected void delete(int id) {
		ArrayList<Recipe> recipes = extractRecipe();
		boolean found = false;
		try {
			for (int i = 0; i < recipes.size(); i++) {
				if (recipes.get(i).getId() == id) {
					recipes.remove(i);
					i--;
					found = true;
				}
			}
			if(!found) {
				throw new RecipeNotFoundException();
			}
		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		ioController.writeObjectToFile(recipes, filepath);
	}

	//for only testing purpose
	public void deleteFile() {
		File file = new File(filepath);
		
		if (file.delete()) {
			System.out.println("\nFile deleted");
		} else {
			System.out.println("\nImpossible delete file");
		}
	}
	
	public Recipe featureWSIBT() {
		//inserisco tutte le ricette in un arraylist
		ArrayList<Recipe> recipes = extractRecipe();
		//creo hashmap per inserire le ricette possibili sulla base degli ingredienti
		Map<Integer, Double> recipeMax = new HashMap<>();
		//scorro l'arraylist e per ogni ricetta faccio il controllo sugli ingredienti
		for (int i = 0; i < recipes.size(); i++) {
			Recipe r = recipes.get(i);
			Map<String, Double> missingIngredients = r.computeMissingIngredients();
			double totIngredients = 0.0;
			//se sono presenti tutti gli ingredienti in storage calcolo gli ingredienti
			if(missingIngredients.isEmpty()) {
				Map<String, Double> ingredients = r.getIngredients();
				for(Entry<String, Double> j : ingredients.entrySet()) {
					totIngredients = totIngredients + j.getValue();
				}
				recipeMax.put(r.getId(), totIngredients);
			}			
		}
		
		//se esiste una ricetta plausibile
		if(!recipeMax.isEmpty()) {
			double max = 0.0;
			int id = -1;
			//cerco la ricetta che massimizza gli ingredienti utilizzati
			for(Entry<Integer, Double> r : recipeMax.entrySet()) {
				if(r.getValue() > max) {
					max = r.getValue();
				    id = r.getKey();
				}
			}
			//determino la ricetta da restituire
			for (int i = 0; i < recipes.size(); i++) {
				Recipe r = recipes.get(i);
				if(r.getId() == id) {
					return r;
				}
			}
		}
		return null;
	}
}