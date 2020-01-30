package main.java.recipes;

import java.util.Map;
import java.util.Map.Entry;

import main.java.IOController;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeController {

	private Path filepath;
	private  String counterIdFilepath;
	private  IOController ioController; 
	private static RecipeController instance;
	
	private RecipeController() {
		super();
		this.filepath = Paths.get(System.getProperty("user.dir") + "\\src\\Files\\Recipe.txt");
		this.counterIdFilepath = System.getProperty("user.dir") + "\\src\\Files\\CounterId.txt"; 
		this.ioController = new IOController();
	}
	
	public static RecipeController getInstance() {
		if (instance == null) {
			instance = new RecipeController();
		}
		
		return instance;
	}

	/*
	 * Returns the List of Recipe objects stored in the Recipe.txt file 
	 */
	@SuppressWarnings("unchecked")
	public List<Recipe> extractRecipe() {
		if (ioController.readObjectFromFile(filepath.toString()) != null) {
			return (ArrayList<Recipe>) ioController.readObjectFromFile(filepath.toString());
		}

		return new ArrayList<>();
	}
    
	/*
	 * Stores the given recipe object in the Recipe.txt file if it isn't stored already
	 */
	public void store(Recipe recipe) {
		List<Recipe> recipes = extractRecipe();
		if (!recipes.contains(recipe)) {
			recipes.add(recipe);
			ioController.writeObjectToFile(recipes, filepath.toString());
		}
	}

	/*
	 * Updates with the given ingredients and name the recipe with the given id, 
	 * if the recipe exists in the Recipe.txt file (otherwise an exception is thrown)
	 */
	public void update(int id, String name, Map<String,Double> ingredients) {
		List<Recipe> recipes = extractRecipe();
		boolean found = false;
		try {
			for (int i = 0; i < recipes.size(); i++) {
				if (recipes.get(i).getId() == id) {
					recipes.get(i).updateRecipe(name, ingredients);
					found = true;
					ioController.writeObjectToFile(recipes, filepath.toString());
					break;
				}
			}
			if(!found) {
				throw new RecipeNotFoundException();
			}
		} catch(RecipeNotFoundException e) {
			System.out.println(e.getMessage());
		}

		
	}

	/*
	 * Deletes the recipe with the given id, if the recipe exists in the 
	 * Recipe.txt file (otherwise an exception is thrown)
	 */
	public void delete(int id) {
		List<Recipe> recipes = extractRecipe();
		boolean found = false;
		try {
			for (int i = 0; i < recipes.size(); i++) {
				if (recipes.get(i).getId() == id) {
					recipes.remove(i);
					
					found = true;
					ioController.writeObjectToFile(recipes, filepath.toString());
					break;
				}
			}
			if(!found) {
				throw new RecipeNotFoundException();
			}
		} catch(RecipeNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Creates and returns a brew from the recipe with the given id, if the recipe 
	 * exists in the Recipe.txt file (otherwise an exception is thrown)
	 */
	public Brew createBrew(int id) {
		List<Recipe> recipes = extractRecipe();
		Brew brew = null;
		boolean found = false;
		try {
			for (int i = 0; i < recipes.size(); i++) {
				if (recipes.get(i).getId() == id) {
				    brew = recipes.get(i).createBrew();
					found = true;
					ioController.writeObjectToFile(recipes, filepath.toString());
					break;
				}
			}
			if(!found) {
				throw new RecipeNotFoundException();
			}
		} catch(RecipeNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return brew;
	}
	
	/*
	 * Updates the counterId.txt file by writing the given id in it
	 */
	public void updateCounterId(int id) {
		try (FileOutputStream fos = new FileOutputStream(counterIdFilepath);
		    DataOutputStream dos = new DataOutputStream(fos)) {
		    dos.writeInt(id); 
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
	}
	
	/*
	 * Returns the missing ingredients to start brewing the recipe with the 
	 * given id, if the recipe exists in the Recipe.txt file (otherwise an 
	 * exception is thrown)
	 */
	public Map<String,Double> getMissingIngredients(int id) {
		List<Recipe> recipes = extractRecipe();
		Map<String,Double> missingIngredients = null;
		boolean found = false;
		try {
			for (int i = 0; i < recipes.size(); i++) {
				if (recipes.get(i).getId() == id) {
					missingIngredients = recipes.get(i).getMissingIngredients();
					found = true;
					break;
				}
			}
			if(!found) {
				throw new RecipeNotFoundException();
			}
		} catch(RecipeNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return missingIngredients;
	}

	/*
	 * Deletes the Recipe.txt file
	 */
	public void deleteFile() {
		File file = new File(filepath.toString());

		if (file.exists()) {
			try{
				Files.delete(filepath);
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/*
	 * Returns the recipe that maximize the usage of the available ingredients, 
	 * if there aren't recipes that can be brewed or there aren't recipes at all
	 * an exception is thrown
	 */
	public Recipe featureWSIBT() {
		try {
			//puts the recipes in a list
			List<Recipe> recipes = extractRecipe();
			//creates a map to contain the recipes for which there aren't missing ingredients
			Map<Integer, Double> recipeMax = new HashMap<>();
			//browses the list and computes the missing ingredients for each recipe
			for (int i = 0; i < recipes.size(); i++) {
				Recipe r = recipes.get(i);
				Map<String, Double> missingIngredients = r.computeMissingIngredients();
				double totIngredients = 0.0;
				//if there aren't missing ingredients the total sum of the ingredients is saved in the map along with the recipe
				if(missingIngredients.isEmpty()) {
					Map<String, Double> ingredients = r.getIngredients();
					for(Entry<String, Double> j : ingredients.entrySet()) {
						totIngredients = totIngredients + j.getValue();
					}
					recipeMax.put(r.getId(), totIngredients);
				}			
			}
			
			//if there is a recipe for which there aren't missing ingredients
			if(!recipeMax.isEmpty()) {
				double max = 0.0;
				int id = -1;
				//looks for the recipe that maximize the ingredients usage
				for(Entry<Integer, Double> r : recipeMax.entrySet()) {
					if(r.getValue() > max) {
						max = r.getValue();
					    id = r.getKey();
					}
				}
				//returns the right recipe
				for (int i = 0; i < recipes.size(); i++) {
					Recipe r = recipes.get(i);
					if(r.getId() == id) {
						return r;
					}
				}
			}
			throw new RecipeNotFoundException("Recipe WSIBT not found");
		}catch(RecipeNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/*
	 * Updates all the recipes' ingredients using the given multiplier
	 */
	public void updateAllRecipesOnNewEquipment(double multiplier) {
		List<Recipe> recipes = extractRecipe();
		List<Recipe> newRecipes = new ArrayList<>();
		for(int i = 0; i<recipes.size(); i++) {
			Recipe r = recipes.get(i);
			Map<String, Double> ingredientsR = r.getIngredients();
			Map<String, Double> newIngredientsR = new HashMap<>();
			for (Entry<String,Double> i1 : ingredientsR.entrySet()) {
				
				//computes the new ingredient quantity using the multiplier
				double updateValue = Math.round(i1.getValue()*multiplier*100.0)/100.0;
				newIngredientsR.put(i1.getKey(), updateValue);
			}
			r.updateRecipe(r.getName(), newIngredientsR);
			newRecipes.add(r);
		}
		ioController.writeObjectToFile(newRecipes, filepath.toString());
	}
}