package main.recipes;

import java.util.HashMap;
import java.util.Map;



public class TestRecipe {

	public static void main(String[] args) {
		
		RecipeController recipeController = RecipeController.getInstance();

		//Test write and read
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe("Recipe 1", ingredients1);
		recipeController.store(recipe1);

		System.out.println("La prima recipe e': " + recipeController.extractRecipe().toString());

		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Yeast", 30.0); 
		ingredients2.put("Sugar", 40.0); 
		Recipe recipe2 = new Recipe("Recipe 2", ingredients2);
		recipeController.store(recipe2);

		System.out.println("\nLe due recipe sono: " + recipeController.extractRecipe().toString());

		Map<String,Double> ingredients3 = new HashMap<>();
		ingredients3.put("Additive", 50.0); 
		Recipe recipe3 = new Recipe("Recipe 3", ingredients2); 
		recipeController.store(recipe3);

		System.out.println("\nLe tre recipe sono: " + recipeController.extractRecipe().toString());

		Map<String,Double> ingredients4 = new HashMap<>();
		ingredients4.put("Sugar", 100.0); 
		Recipe recipe4 = new Recipe("Recipe 4", ingredients4); 
		recipeController.store(recipe4);

		System.out.println("\nLe quattro recipe sono: " + recipeController.extractRecipe().toString());

		//Test update
		recipeController.update(1, "New Recipe 1", ingredients2);
		recipeController.update(2, "New Recipe 2", ingredients4);

		System.out.println("\nLe quattro recipe dopo le modifiche sono: " + recipeController.extractRecipe().toString());

		//Test delete
		recipeController.delete(1);
		recipeController.delete(3);
		System.out.println("\nLe recipe dopo l'eliminazione sono: " + recipeController.extractRecipe().toString());

		recipeController.deleteFile();
	}
}
