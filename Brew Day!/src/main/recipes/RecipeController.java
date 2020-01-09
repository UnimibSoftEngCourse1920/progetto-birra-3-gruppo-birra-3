package main.recipes;

import java.util.HashMap;
import java.io.File;
import java.util.ArrayList;
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

	protected ArrayList<Recipe> extractRecipe() {
		if (ioController.ReadObjectFromFile(filepath) != null) {
			return (ArrayList<Recipe>) ioController.ReadObjectFromFile(filepath);
		}

		return new ArrayList<Recipe>();
	}


	protected void store(Recipe recipe) {
		ArrayList<Recipe> recipes = extractRecipe();
		for (Recipe r : recipes) {
			if (r.getId() == recipe.getId()) {
				return;
			}
		}

		recipes.add(recipe);
		ioController.WriteObjectToFile(recipes, filepath);
	}

	protected void update(int id, String name, HashMap<String,Double> ingredients) {
		if (name != null && ingredients != null) {
			ArrayList<Recipe> recipes = extractRecipe();
			for (Recipe recipe : recipes) {
				if (recipe.getId() == id) {
					if (name == null) {
						recipe.updateRecipe(recipe.getName(), ingredients);
					} else if (ingredients == null) {
						recipe.updateRecipe(name, recipe.getIngredients());
					} else {
						recipe.updateRecipe(name, ingredients);
					}
				}
			}

			ioController.WriteObjectToFile(recipes, filepath);
		}

	}

	protected void delete(int id) {
		ArrayList<Recipe> recipes = extractRecipe();
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).getId() == id) {
				recipes.remove(i);
			}
		}

		ioController.WriteObjectToFile(recipes, filepath);
	}

	//for only testing purpose
	public void deleteFile() {
		File file = new File(filepath);
		file.delete();
	}
}
