package main.recipes;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import main.IOController;
import main.resources.Storage;
import main.resources.StorageController;

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
		recipes.add(recipe1);
		recipes.add(recipe2);
		
		iocontroller.writeObjectToFile(recipes, System.getProperty("user.dir") + "\\src\\Files\\Recipe.txt"); 
		ArrayList<Recipe> extRecipes = (ArrayList<Recipe>) recipeController.extractRecipe();
		
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
		
		ArrayList<Recipe> recipes = (ArrayList<Recipe>) recipeController.extractRecipe();
		
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
		
		recipe1.updateRecipe(nameEdit1,ingredients2);
		recipe2.updateRecipe(nameEdit2,ingredients3);
		
		ArrayList<Recipe> extRecipes = (ArrayList<Recipe>) recipeController.extractRecipe();
		
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
        
		ArrayList<Recipe> recipes = (ArrayList<Recipe>) recipeController.extractRecipe();
		
		recipeController.deleteFile();
		
		assertTrue(recipes.isEmpty());
	}
	
	@Test
	public void testCreateBrew() {
		StorageController sController = StorageController.getInstance();
		BrewController brewController = BrewController.getInstance();
		RecipeController recipeController = RecipeController.getInstance();
		
		HashMap<String,Double> sIngredients = new HashMap<>();
	    sIngredients.put("Malt", 20.0);
	    sIngredients.put("Hop", 30.0);
		Storage storage = Storage.getInstance();
		storage.setIngredients(sIngredients);
		sController.store(storage);
		
		HashMap<String,Double> ingredients = new HashMap<>();
		Recipe recipe1 = new Recipe("Recipe 1", ingredients);
		recipeController.store(recipe1);
		Brew brew1 = recipeController.createBrew(recipe1.getId());
		Brew brew2 = recipeController.createBrew(recipe1.getId());
		Recipe recipe2 = new Recipe("Recipe 2", ingredients);
		recipeController.store(recipe2);
		Brew brew3 = recipeController.createBrew(recipe2.getId());
		
		brewController.deleteFile();
		sController.deleteFile();
		recipeController.deleteFile();
		
		assertTrue(brew1.getId().compareTo(recipe1.getId() + 0.1) == 0);
		assertTrue(brew2.getId().compareTo(recipe1.getId() + 0.2) == 0);
		assertTrue(brew3.getId().compareTo(recipe2.getId() + 0.1) == 0);
	}
	
	@Test
	public void testUpdateCounterId() {
		RecipeController recipeController = RecipeController.getInstance();
		
		recipeController.updateCounterId(21);
		
		int id = 0;
		try (FileInputStream fin = new FileInputStream(System.getProperty("user.dir") + "\\src\\Files\\CounterId.txt");
	        DataInputStream din = new DataInputStream(fin)) {
		    id = din.readInt();
		}
		catch(FileNotFoundException fe)
		{ 
		    System.out.println("FileNotFoundException : " + fe);
	    }
		catch(IOException ioe)
		{
			System.out.println("IOException : " + ioe);
		}
		
		assertEquals(id, 21);
		
		recipeController.updateCounterId(12);
		
		try (FileInputStream fin = new FileInputStream(System.getProperty("user.dir") + "\\src\\Files\\CounterId.txt");
		    DataInputStream din = new DataInputStream(fin)) {
			id = din.readInt();
		}
		catch(FileNotFoundException fe)
		{ 
			System.out.println("FileNotFoundException : " + fe);
		}
	    catch(IOException ioe)
	    {
			System.out.println("IOException : " + ioe);
		}
		
		assertEquals(id, 12);
		
		File file = new File(System.getProperty("user.dir") + "\\src\\Files\\CounterId.txt");
		
		if (file.delete()) {
			System.out.println("\nFile deleted");
		} else {
			System.out.println("\nImpossible delete file");
		}
	}
	
	
	@Test
	public void testFeatureWSIBT(){
		RecipeController recipeController = RecipeController.getInstance();
		
		//creo ricette
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 190.0); 
		Recipe recipe1 = new Recipe("Recipe 1", ingredients1);
		recipeController.store(recipe1);
		
		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Yeast", 30.0); 
		ingredients2.put("Sugar", 40.0); 
		Recipe recipe2 = new Recipe("Recipe 2", ingredients2);
		recipeController.store(recipe2);
		
		Map<String,Double> ingredients3 = new HashMap<>();
		ingredients3.put("Malt", 15.0); 
		ingredients3.put("Sugar", 40.0); 
		Recipe recipe3 = new Recipe("Recipe 3", ingredients3);
		recipeController.store(recipe3);
		
		//creo storage
		Map<String,Double> ingredients = new HashMap<String, Double>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Yeast", 35.0);
		ingredients.put("Hop", 189.0);
		ingredients.put("Sugar", 50.0);
		StorageController storageController = StorageController.getInstance();
		storageController.createStorage(ingredients);
		
		
		Recipe recipeFeature = recipeController.featureWSIBT();
		
		recipeController.deleteFile();
		storageController.deleteFile();
		
		assertTrue(recipeFeature.equals(recipe2));		
	}
}
