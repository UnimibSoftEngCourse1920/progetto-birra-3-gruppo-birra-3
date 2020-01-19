package main.recipes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
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
		ingredients.put("Hop", 20.0); 
		Recipe recipe = new Recipe("Recipe", ingredients);
		
		iocontroller.writeObjectToFile(recipe, System.getProperty("user.dir") + "\\src\\Files\\Recipe.txt"); 
		ArrayList<Recipe> recipes = recipeController.extractRecipe();
		//assertTrue(recipes.get(0).getId() == recipe.getId());
		assertTrue(recipes.get(0).equals(recipe));
		
		recipeController.deleteFile();
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
		//assertTrue(recipes.get(0).getId() == recipe1.getId());
		//assertTrue(recipes.get(1).getId() == recipe2.getId());
		assertTrue(recipes.get(0).equals(recipe1));
		assertTrue(recipes.get(1).equals(recipe2));
		
		recipeController.deleteFile();
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
		String nameEdit2 = "Recipe 1 Edit";
		
		recipeController.update(1, nameEdit1, ingredients2);
		recipeController.update(2, nameEdit2, ingredients3);
		
		recipe1.setName(nameEdit1);
		recipe2.setName(nameEdit2);
		recipe1.setIngredients(ingredients2);
		recipe2.setIngredients(ingredients3);
		
		ArrayList<Recipe> recipes = recipeController.extractRecipe();
		//assertTrue(recipes.get(0).getName() == nameEdit1);
		//assertTrue(recipes.get(1).getName() == nameEdit2);
		assertTrue(recipes.get(0).equals(recipe1));
		assertTrue(recipes.get(1).equals(recipe2));
		
		recipeController.deleteFile();
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

		recipeController.delete(1);
		recipeController.delete(2);
        
		ArrayList<Recipe> recipes = recipeController.extractRecipe();
		assertTrue(recipes.isEmpty());
		
		recipeController.deleteFile();
	}

}
