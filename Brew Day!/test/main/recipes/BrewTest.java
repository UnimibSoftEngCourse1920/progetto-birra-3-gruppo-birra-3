package main.recipes;

import static org.junit.Assert.*;
import main.resources.Storage;
import main.resources.StorageController;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class BrewTest {
	
	String yeast = "Yeas";
	String recipeName = "Recipe";
	String note1 = "Note 1";
	BrewController brewController = BrewController.getInstance();

	@Test
	public void testBrew() {
		//create Storage
		StorageController sController = StorageController.getInstance();
		Storage storage = Storage.getInstance();
		Map<String,Double> storageIngredients = new HashMap<>();
		storageIngredients.put("Hop", 50.0);
		storageIngredients.put("Malt", 5.0);
		storageIngredients.put(yeast, 30.0);
		storage.setIngredients(storageIngredients);
		sController.store(storage);

		//create Recipe 1
		Map<String,Double> ingredients = new HashMap<>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		Recipe recipe1 = new Recipe("Recipe 1", ingredients);
		
		//create Brew with more ingredients than Storage
		assertEquals(null, recipe1.createBrew());
		
		storageIngredients.put("Malt", 20.0);
		storage.setIngredients(storageIngredients);
		
		//create Brew correctly
		assertEquals(1,1 , recipe1.createBrew().getId());
		assertEquals(1,2 , recipe1.createBrew().getId());
		
		assertEquals(2,1 , new Recipe("Recipe 2", new HashMap<String,Double>()).createBrew().getId());
	    
		sController.deleteFile();
		brewController.deleteFile();
	}

	@Test
	public void testAddNote() {	
		//create Storage
		StorageController sController = StorageController.getInstance();
		Storage storage = Storage.getInstance();
		Map<String,Double> storageIngredients = new HashMap<>();
		storageIngredients.put("Hop", 50.0);
		storageIngredients.put("Malt", 5.0);
		storageIngredients.put(yeast, 30.0);
		storage.setIngredients(storageIngredients);
		sController.store(storage);
			
		Recipe recipe = new Recipe(recipeName, new HashMap<>());
		Brew brew = recipe.createBrew();
		brew.addNote("Standard note", false);
		brew.addNote("Tasting note", true);
		
		sController.deleteFile();
		brewController.deleteFile();
		
		for(Entry<Integer, String> i : brew.getNotes().entrySet()) {
			if (i.getKey() > 0) {
				assertEquals(0, i.getValue().compareTo("Standard note"));
			} else {
				assertEquals(0,i.getValue().compareTo("Tasting note"));
			}
		}
	}

	@Test
	public void testDeleteNote() {	
		//create Storage
		StorageController sController = StorageController.getInstance();
		Storage storage = Storage.getInstance();
		Map<String,Double> storageIngredients = new HashMap<>();
		storageIngredients.put("Hop", 50.0);
		storageIngredients.put("Malt", 5.0);
		storageIngredients.put(yeast, 30.0);
		storage.setIngredients(storageIngredients);
		sController.store(storage);
		
		Recipe recipe = new Recipe(recipeName, new HashMap<>());
		Brew brew = recipe.createBrew();
		brew.addNote(note1, false);
		brew.addNote("Note 2", true);
		brew.addNote("Note 3", false);
		
		brew.deleteNote(1);
		brew.deleteNote(-2);
		
		sController.deleteFile();
		brewController.deleteFile();
		
		assertFalse(brew.getNotes().containsKey(1));
		assertFalse(brew.getNotes().containsKey(-2));
		assertTrue(brew.getNotes().containsKey(3));
	}

	@Test
	public void testModifyNote() {
		//create Storage
		StorageController sController = StorageController.getInstance();
		Storage storage = Storage.getInstance();
		Map<String,Double> storageIngredients = new HashMap<>();
		storageIngredients.put("Hop", 50.0);
		storageIngredients.put("Malt", 5.0);
		storageIngredients.put(yeast, 30.0);
		storage.setIngredients(storageIngredients);
		sController.store(storage);
		
		Recipe recipe = new Recipe(recipeName, new HashMap<>());
		Brew brew = recipe.createBrew();
		brew.addNote(note1, false);
		brew.modifyNote(1, "New text");
		
		sController.deleteFile();
		brewController.deleteFile();
		
		assertNotEquals(0,brew.getNotes().get(1).compareTo(note1));
	}

}
