package main.recipes;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.resources.Storage;
import main.resources.StorageController;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecipeTest {

	@Test
	public void testComputeMissingIngredients() {
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0);
		
		StorageController storageController = StorageController.getInstance();
		storageController.createStorage(ingredients1);
		
		HashMap<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Malt", 20.0); 
		ingredients2.put("Hop", 40.0); 
		Recipe recipe = new Recipe("Recipe", ingredients2);
		
		Map<String,Double> missing = recipe.computeMissingIngredients();
		
		assertTrue(missing.get("Malt") == 10.0);
		assertTrue(missing.get("Hop") == 20.0);
		
		storageController.deleteFile();
	}
	
	@Test
	public void testCreateBrew() {
		StorageController sController = StorageController.getInstance();
		BrewController brewController = BrewController.getInstance();
		
		HashMap<String,Double> sIngredients = new HashMap<>();
	    sIngredients.put("Malt", 20.0);
	    sIngredients.put("Hop", 30.0);
		Storage storage = Storage.getInstance();
		storage.setIngredients(sIngredients);
		sController.store(storage);
		
		HashMap<String,Double> ingredients = new HashMap<>();
		Recipe recipe1 = new Recipe("Recipe 1", ingredients);
		Brew brew1 = recipe1.createBrew();
		Brew brew2 = recipe1.createBrew();
		Recipe recipe2 = new Recipe("Recipe 2", ingredients);
		Brew brew3 = recipe2.createBrew();
		
		brewController.deleteFile();
		sController.deleteFile();
		
		assertTrue(brew1.getId().compareTo(2.1) == 0);
		assertTrue(brew2.getId().compareTo(2.2) == 0);
		assertTrue(brew3.getId().compareTo(3.1) == 0);
	}
	
	@Test
	public void testGetQuantity() {
		HashMap<String,Double> ingredients = new HashMap<>();
		ingredients.put("Malt", 10.0); 
		ingredients.put("Hop", 20.0); 
		Recipe recipe = new Recipe("Recipe", ingredients);
		
		double malt = recipe.getQuantity("Malt");
		double hop = recipe.getQuantity("Hop");
		
		assertTrue(malt == 10.0);
		assertTrue(hop == 20.0);
	}
	
	@Test
	public void testRecipe() {
		HashMap<String,Double> ingredients = new HashMap<>();
		Recipe recipe1 = new Recipe("Recipe 1", ingredients);
		assertEquals(recipe1.getId(), 5);

		Recipe recipe2 = new Recipe("Recipe 2", ingredients);
		assertEquals(recipe2.getId(), 6);
	}
}
