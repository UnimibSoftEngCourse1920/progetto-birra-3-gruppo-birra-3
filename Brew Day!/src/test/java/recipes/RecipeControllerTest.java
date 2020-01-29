package test.java.recipes;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import main.java.IOController;
import main.java.recipes.Brew;
import main.java.recipes.BrewController;
import main.java.recipes.Recipe;
import main.java.recipes.RecipeController;
import main.java.resources.Storage;
import main.java.resources.StorageController;

public class RecipeControllerTest {
	
	String userDir = "user.dir";
	String recipeName1 = "Recipe 1";
	String recipeName2 = "Recipe 2";
	String yeast = "Yeast";
	String sugar = "Sugar";
	String filepathCounterid = "\\src\\Files\\CounterId.txt";
	
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
		Recipe recipe1 = new Recipe(recipeName1, ingredients);
		ingredients.put("Hop", 20.0); 
		Recipe recipe2 = new Recipe(recipeName2, ingredients);
		
		List<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(recipe1);
		recipes.add(recipe2);
		
		iocontroller.writeObjectToFile(recipes, System.getProperty(userDir) + "\\src\\Files\\Recipe.txt"); 
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
		Recipe recipe1 = new Recipe(recipeName1, ingredients1);
		recipeController.store(recipe1);

		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put(yeast, 30.0); 
		ingredients2.put(sugar, 40.0); 
		Recipe recipe2 = new Recipe(recipeName2, ingredients2);
		recipeController.store(recipe2);
		
		ArrayList<Recipe> recipes = (ArrayList<Recipe>) recipeController.extractRecipe();
		
		recipeController.deleteFile();
		
		assertEquals(recipes.get(0).getId(),recipe1.getId());
		assertEquals(recipes.get(1).getId(),recipe2.getId());
	}

	@Test
	public void testUpdate() {
		RecipeController recipeController = RecipeController.getInstance();
		
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe(recipeName1, ingredients1);
		recipeController.store(recipe1);

		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put(yeast, 30.0); 
		ingredients2.put(sugar, 40.0); 
		Recipe recipe2 = new Recipe(recipeName2, ingredients2);
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
		
		assertEquals(extRecipes.get(0).getName(),recipe1.getName());
		assertEquals(extRecipes.get(1).getName(),recipe2.getName());
		assertEquals(extRecipes.get(0).getIngredients(),recipe1.getIngredients());
		assertEquals(extRecipes.get(1).getIngredients(),recipe2.getIngredients());
	}

	@Test
	public void testDelete() {
        RecipeController recipeController = RecipeController.getInstance();
		
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe(recipeName1, ingredients1);
		recipeController.store(recipe1);

		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put(yeast, 30.0); 
		ingredients2.put(sugar, 40.0); 
		Recipe recipe2 = new Recipe(recipeName2, ingredients2);
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
		Recipe recipe1 = new Recipe(recipeName1, ingredients);
		recipeController.store(recipe1);
		Brew brew1 = recipeController.createBrew(recipe1.getId());
		Brew brew2 = recipeController.createBrew(recipe1.getId());
		Recipe recipe2 = new Recipe(recipeName2, ingredients);
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
		try (FileInputStream fin = new FileInputStream(System.getProperty(userDir) + filepathCounterid);
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
		
		assertEquals(21, id);
		
		recipeController.updateCounterId(12);
		
		try (FileInputStream fin = new FileInputStream(System.getProperty(userDir) + filepathCounterid);
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
		
		Path filepath = Paths.get(System.getProperty(userDir) + filepathCounterid);
		try {
			Files.delete(filepath);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		assertEquals(12, id);
	}
	
	
	@Test
	public void testFeatureWSIBT(){
		RecipeController recipeController = RecipeController.getInstance();
		
		//creo ricette
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 190.0); 
		Recipe recipe1 = new Recipe(recipeName1, ingredients1);
		recipeController.store(recipe1);
		
		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put(yeast, 30.0); 
		ingredients2.put(sugar, 40.0); 
		Recipe recipe2 = new Recipe(recipeName2, ingredients2);
		recipeController.store(recipe2);
		
		Map<String,Double> ingredients3 = new HashMap<>();
		ingredients3.put("Malt", 15.0); 
		ingredients3.put(sugar, 40.0); 
		Recipe recipe3 = new Recipe("Recipe 3", ingredients3);
		recipeController.store(recipe3);
		
		//creo storage
		Map<String,Double> ingredients = new HashMap<String, Double>();
		ingredients.put("Malt", 10.0);
		ingredients.put(yeast, 35.0);
		ingredients.put("Hop", 189.0);
		ingredients.put(sugar, 50.0);
		StorageController storageController = StorageController.getInstance();
		storageController.createStorage(ingredients);
		
		
		Recipe recipeFeature = recipeController.featureWSIBT();
		
		recipeController.deleteFile();
		storageController.deleteFile();
		
		assertEquals(recipeFeature.getId(), recipe2.getId());		
	}
	
	@Test
	public void testGetMissingIngredients() {
		StorageController sController = StorageController.getInstance();
		BrewController brewController = BrewController.getInstance();
		RecipeController recipeController = RecipeController.getInstance();
		
		HashMap<String,Double> sIngredients = new HashMap<>();
	    sIngredients.put("Malt", 0.0);
		Storage storage = Storage.getInstance();
		storage.setIngredients(sIngredients);
		sController.store(storage);
		
		Map<String,Double> ingredients = new HashMap<String, Double>();
		ingredients.put("Malt", 10.0);
		Recipe recipe1 = new Recipe(recipeName1, ingredients);
		recipeController.store(recipe1);
		recipeController.createBrew(recipe1.getId());
		Recipe recipe2 = new Recipe(recipeName2, ingredients);
		recipeController.store(recipe2);
		
		Map<String,Double> missingIngredients = recipeController.getMissingIngredients(recipe1.getId());
		
		brewController.deleteFile();
		sController.deleteFile();
		recipeController.deleteFile();
		
		assertTrue(missingIngredients.equals(ingredients));
	}
}
