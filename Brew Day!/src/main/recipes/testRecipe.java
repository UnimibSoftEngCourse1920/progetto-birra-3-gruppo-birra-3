package main.recipes;

import java.io.File;
import java.util.ArrayList;
import main.IOController;
import java.util.HashMap;
import main.resources.StorageController;
import java.util.HashMap;

public class testRecipe {

	public static void main(String[] args) {
		
		RecipeController recipeController = RecipeController.getInstance();

		//Test write and read
		HashMap<String,Double> ingredients1 = new HashMap<String, Double>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe("Recipe 1", ingredients1);
		recipeController.store(recipe1);

		System.out.println("La prima recipe e': " + recipeController.extractRecipe().toString());

		HashMap<String,Double> ingredients2 = new HashMap<String, Double>();
		ingredients2.put("Yeast", 30.0); 
		ingredients2.put("Sugar", 40.0); 
		Recipe recipe2 = new Recipe("Recipe 2", ingredients2);
		recipeController.store(recipe2);

		System.out.println("\nLe due recipe sono: " + recipeController.extractRecipe().toString());

		HashMap<String,Double> ingredients3 = new HashMap<String, Double>();
		ingredients3.put("Additive", 50.0); 
		Recipe recipe3 = new Recipe("Recipe 3", ingredients2); 
		recipeController.store(recipe3);

		System.out.println("\nLe tre recipe sono: " + recipeController.extractRecipe().toString());

		HashMap<String,Double> ingredients4 = new HashMap<String, Double>();
		ingredients4.put("Sugar", 100.0); 
		Recipe recipe4 = new Recipe("Recipe 4", ingredients4); 
		recipeController.store(recipe4);

		System.out.println("\nLe quattro recipe sono: " + recipeController.extractRecipe().toString());

		//Test update
		recipeController.update(1, "New Recipe 1", ingredients2);
		recipeController.update(2, "New Recipe 2", null);
		recipeController.update(3, null, ingredients4);
		recipeController.update(4, null, null);

		System.out.println("\nLe quattro recipe dopo le modifiche sono: " + recipeController.extractRecipe().toString());

		//Test delete
		recipeController.delete(1);
		recipeController.delete(3);
		System.out.println("\nLe recipe dopo l'eliminazione sono: " + recipeController.extractRecipe().toString());

		recipeController.deleteFile();
	}
}
