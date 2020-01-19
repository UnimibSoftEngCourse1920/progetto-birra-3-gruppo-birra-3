package main.recipes;

import static org.junit.Assert.*;
import main.resources.Storage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class BrewTest {

	@Test
	public void testBrew() {
		//create Storage
		Storage storage = Storage.getInstance();
		Map<String,Double> storageIngredients = new HashMap<>();
		storageIngredients.put("Hop", 50.0);
		storageIngredients.put("Malt", 5.0);
		storageIngredients.put("Yeast", 30.0);
		storage.setIngredients(storageIngredients);
		
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
	}

	@Test
	public void testAddNote() {
		Recipe recipe = new Recipe("Recipe", new HashMap<>());
		Brew brew = recipe.createBrew();
		brew.addNote(1, "Standard note", false);
		brew.addNote(2, "Tasting note", true);
		
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
		Recipe recipe = new Recipe("Recipe", new HashMap<>());
		Brew brew = recipe.createBrew();
		brew.addNote(1, "Note 1", false);
		brew.addNote(2, "Note 2", true);
		brew.addNote(3, "Note 3", false);
		
		brew.deleteNote(1);
		brew.deleteNote(-2);
		
		assertFalse(brew.getNotes().containsKey(1));
		assertFalse(brew.getNotes().containsKey(-2));
		assertTrue(brew.getNotes().containsKey(3));
	}

	@Test
	public void testModifyNote() {
		Recipe recipe = new Recipe("Recipe", new HashMap<>());
		Brew brew = recipe.createBrew();
		brew.addNote(1, "Note 1", false);
		brew.modifyNote(1, "New text");
		assertNotEquals(0,brew.getNotes().get(1).compareTo("Note 1"));
		
	}

}
