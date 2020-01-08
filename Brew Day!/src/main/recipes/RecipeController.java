package main.recipes;

import java.util.HashMap;
import java.util.ArrayList;
import main.IOController;

public class RecipeController {

	private static String filepath = System.getProperty("user.dir") + "\\src\\Files\\Recipe.txt";
	private static IOController ioController = new IOController();
	
	protected static ArrayList<Recipe> extractRecipe() {
		return (ArrayList<Recipe>) ioController.ReadObjectFromFile(filepath);
	}
	

	protected static void store(Recipe recipe) {
		ioController.WriteObjectToFile(recipe, filepath);
	}

	protected static void update(int id, String name, HashMap<String,Double> ingredients) {
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

	protected static void delete(int id) {
		ArrayList<Recipe> recipes = extractRecipe();
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).getId() == id) {
				recipes.remove(i);
			}
		}

		ioController.WriteObjectToFile(recipes, filepath);
	}



}
