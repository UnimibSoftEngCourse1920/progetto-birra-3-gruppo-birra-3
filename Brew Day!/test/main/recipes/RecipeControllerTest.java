package main.recipes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import main.IOController;

public class RecipeControllerTest {
	
	@Test
	public void testGetInstance() {
		assertEquals(RecipeController.getInstance(), RecipeController.getInstance());
	}

	@Test
	public void testExtractRecipe() {
		IOController iocontroller = new IOController();
		RecipeController recipeController = RecipeController.getInstance();
		
		HashMap<String,Double> ingredients = new HashMap<>();
		ingredients.put("Malt", 10.0); 
		Recipe recipe1 = new Recipe("Recipe 1", ingredients);
		ingredients.put("Hop", 20.0); 
		Recipe recipe2 = new Recipe("Recipe 2", ingredients);
		
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		iocontroller.writeObjectToFile(recipes, System.getProperty("user.dir") + "\\src\\Files\\Recipe.txt"); 
		ArrayList<Recipe> extRecipes = recipeController.extractRecipe();
		
		recipeController.deleteFile();
		
		assertTrue(extRecipes.equals(recipes));
	}

	@Test
	public void testStore() {
		RecipeController recipeController = RecipeController.getInstance();
		
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe("Recipe 1", ingredients1);
		recipeController.store(recipe1);

		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Yeast", 30.0); 
		ingredients2.put("Sugar", 40.0); 
		Recipe recipe2 = new Recipe("Recipe 2", ingredients2);
		recipeController.store(recipe2);
		
		ArrayList<Recipe> recipes = recipeController.extractRecipe();
		
		recipeController.deleteFile();
		
		assertTrue(recipes.get(0).equals(recipe1));
		assertTrue(recipes.get(1).equals(recipe2));
	}

	@Test
	public void testUpdate() {
		RecipeController recipeController = RecipeController.getInstance();
		
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe("Recipe 1", ingredients1);
		recipeController.store(recipe1);

		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Yeast", 30.0); 
		ingredients2.put("Sugar", 40.0); 
		Recipe recipe2 = new Recipe("Recipe 2", ingredients2);
		recipeController.store(recipe2);
		
		Map<String,Double> ingredients3 = new HashMap<>();
		ingredients3.put("Additive", 50.0); 
		
		String nameEdit1 = "Recipe 1 Edit";
		String nameEdit2 = "Recipe 2 Edit";
		
		recipeController.update(recipe1.getId(), nameEdit1, ingredients2);
		recipeController.update(recipe2.getId(), nameEdit2, ingredients3);
		
		recipe1.setName(nameEdit1);
		recipe2.setName(nameEdit2);
		recipe1.setIngredients(ingredients2);
		recipe2.setIngredients(ingredients3);
		
		ArrayList<Recipe> extRecipes = recipeController.extractRecipe();
		
		recipeController.deleteFile();
		
		assertTrue(extRecipes.get(0).equals(recipe1));
		assertTrue(extRecipes.get(1).equals(recipe2));
	}

	@Test
	public void testDelete() {
        RecipeController recipeController = RecipeController.getInstance();
		
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe("Recipe 1", ingredients1);
		recipeController.store(recipe1);

		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Yeast", 30.0); 
		ingredients2.put("Sugar", 40.0); 
		Recipe recipe2 = new Recipe("Recipe 2", ingredients2);
		recipeController.store(recipe2);

		recipeController.delete(recipe1.getId());
		recipeController.delete(recipe2.getId());
        
		ArrayList<Recipe> recipes = recipeController.extractRecipe();
		
		recipeController.deleteFile();
		
		assertTrue(recipes.isEmpty());
	}
}
