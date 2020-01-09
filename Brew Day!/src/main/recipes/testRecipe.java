package main.recipes;

import java.io.File;
import java.util.ArrayList;
import main.IOController;
import java.util.HashMap;
import main.resources.StorageController;
import java.util.HashMap;

public class testRecipe {

	public static void main(String[] args) {

		//Test write and read
		HashMap<String,Double> ingredients1 = new HashMap<String, Double>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe("Recipe 1", ingredients1); RecipeController.store(recipe1);

		System.out.println("La prima recipe é: " +
				RecipeController.extractRecipe().toString());

		HashMap<String,Double> ingredients2 = new HashMap<String, Double>();
		ingredients2.put("Yeast", 30.0); 
		ingredients2.put("Sugar", 40.0); 
		Recipe recipe2 = new Recipe("Recipe 2", ingredients2);
		RecipeController.store(recipe2);

		System.out.println("\nLe due recipe sono: " + RecipeController.extractRecipe().toString());

		HashMap<String,Double> ingredients3 = new HashMap<String, Double>();
		ingredients3.put("Additive", 50.0); 
		Recipe recipe3 = new Recipe("Recipe 3", ingredients2); 
		RecipeController.store(recipe3);

		System.out.println("\nLe tre recipe sono: " + RecipeController.extractRecipe().toString());

		HashMap<String,Double> ingredients4 = new HashMap<String, Double>();
		ingredients4.put("Sugar", 100.0); 
		Recipe recipe4 = new Recipe("Recipe 4", ingredients4); 
		RecipeController.store(recipe4);

		System.out.println("\nLe quattro recipe sono: " + RecipeController.extractRecipe().toString());

		//Test update
		RecipeController.update(1, "New Recipe 1", ingredients2);
		RecipeController.update(2, "New Recipe 2", null);
		RecipeController.update(3, null, ingredients4);
		RecipeController.update(4, null, null);

		System.out.println("\nLe quattro recipe dopo le modifiche sono: " + RecipeController.extractRecipe().toString());

		//Test delete
		RecipeController.delete(1);
		RecipeController.delete(3);
		System.out.println("\nLe recipe dopo l'eliminazione sono: " + RecipeController.extractRecipe().toString());

		RecipeController.deleteFile();
	}
}
