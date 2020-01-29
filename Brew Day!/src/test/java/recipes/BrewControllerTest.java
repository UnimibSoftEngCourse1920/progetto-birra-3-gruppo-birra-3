package test.java.recipes;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import main.java.IOController;
import main.java.recipes.Brew;
import main.java.recipes.BrewController;
import main.java.recipes.Recipe;
import main.java.resources.Storage;
import main.java.resources.StorageController;


public class BrewControllerTest {
	
	String recipeName = "Recipe";
	String note1 = "Note 1";
	String note2 = "Note 2";
	String note3 = "Note 3";
	String newNote1 = "New note 1";
	BrewController brewController = BrewController.getInstance();
	
	@Test
	public void testGetInstance() {
		assertEquals(BrewController.getInstance(), BrewController.getInstance());
	}

	@Test
	public void testExtractBrew() {
		IOController iocontroller = new IOController();
		
		Map<String,Double> ingredients = new HashMap<>();
	    ingredients.put("Malt", 10.0);
	    ingredients.put("Hop", 20.0);
		Recipe recipe = new Recipe(recipeName, ingredients);
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate,1);
	    brew1.addNote("test note 1",true);
	    brew1.addNote("test note 2",false);
	    
	    recipe.incrementCountBrew();
	    
	    Brew brew2 = new Brew(recipe, startDate,1);
	    
	    List<Brew> brews = new ArrayList<Brew>();
	    brews.add(brew1);
	    brews.add(brew2);
	    
	    iocontroller.writeObjectToFile(brews, System.getProperty("user.dir") + "\\src\\Files\\Brew.txt"); 
		List<Brew> extBrews = brewController.extractBrew();
		
		brewController.deleteFile();

		assertTrue(brews.equals(extBrews));
	}

	@Test
	public void testStore() {
		Recipe recipe = new Recipe(recipeName, new HashMap<>());
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate,1);
	    brew1.addNote(note1,true);
	    brew1.addNote(note2,false);
	   
		recipe.incrementCountBrew();
	    Brew brew2 = new Brew(recipe,startDate,1);
	    brew2.addNote(note3,true);
	    brew2.addNote("Note 4",false);
	    
	    brewController.store(brew1);
	    brewController.store(brew2);
	    
	    List<Brew> brews = brewController.extractBrew();
	    
	    brewController.deleteFile();
	        
	  	assertEquals(brews.get(0).getId(),brew1.getId());
	  	assertEquals(brews.get(1).getId(),brew2.getId());
	}

	@Test
	public void testUpdateNote() {
		Recipe recipe1 = new Recipe("Recipe1", null);
		Date startDate = new Date(System.currentTimeMillis());
		Brew brew1 = new Brew(recipe1, startDate,1);
		brew1.addNote(note1, false);
		brew1.addNote(note2, false);
		brewController.store(brew1);
		
		Recipe recipe2 = new Recipe("Recipe2", null);
		Brew brew2 = new Brew(recipe2, startDate,1);
		brew2.addNote(note3, true);
		brewController.store(brew2);
		
		String txt1 = newNote1;
		String txt2 = "New note 3";
		
		brewController.updateNote(brew1.getId(), 2, txt1);
		brewController.updateNote(brew2.getId(), -1, txt2);
		
		brew1.modifyNote(2, txt1);
		brew2.modifyNote(-1, txt2);
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertEquals(brews.get(0).getNotes().get(2),brew1.getNotes().get(2));
		assertEquals(brews.get(1).getNotes().get(-1),brew2.getNotes().get(-1));	
	}
	
	@Test
	public void testDelete() {
		HashMap<String,Double> ingredients = new HashMap<>();
	    ingredients.put("Malt", 10.0);
	    ingredients.put("Hop", 20.0);
		Recipe recipe = new Recipe(recipeName, ingredients);
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate,1);
	    brew1.addNote(note1,true);
	    brew1.addNote(note2,false);
		recipe.incrementCountBrew();
		
	    Brew brew2 = new Brew(recipe,startDate,1);
	    brew2.addNote(note3,true);
	    brew2.addNote("Note 4",false);
	    
	    brewController.store(brew1);
	    brewController.store(brew2);
	    
	    brewController.delete(brew1.getId());
		brewController.delete(brew2.getId());
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertTrue(brews.isEmpty());
	}
	
	@Test
	public void testDeleteNote() {
		Recipe recipe = new Recipe(recipeName, new HashMap<>());
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate,1);
	    brew1.addNote(note1,true);
	    brew1.addNote(note2,false);
	    brew1.addNote(note3,true);
		recipe.incrementCountBrew();
	    
	    brewController.store(brew1);
	    
	    brewController.deleteNote(brew1.getId(), -1);
	    brewController.deleteNote(brew1.getId(), 2);
	    brewController.deleteNote(brew1.getId(), -3);
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertTrue(brews.get(0).getNotes().isEmpty());
	}
	
	@Test
	public void testCancel() {
		StorageController sController = StorageController.getInstance();
		
		HashMap<String,Double> sIngredients = new HashMap<>();
	    sIngredients.put("Malt", 20.0);
	    sIngredients.put("Hop", 30.0);
		Storage storage = Storage.getInstance();
		storage.setIngredients(sIngredients);
		sController.store(storage);
		
		HashMap<String,Double> rIngredients = new HashMap<>();
	    rIngredients.put("Malt", 10.0);
	    rIngredients.put("Hop", 20.0);
		Recipe recipe = new Recipe(recipeName, rIngredients);
		Brew brew1 = recipe.createBrew();
		
		brewController.store(brew1);
		
		brewController.cancel(brew1.getId());
		
		Storage extStorage = sController.extractStorage();
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		sController.deleteFile();
		
		assertTrue(brews.isEmpty());
		assertTrue(sIngredients.equals(extStorage.getIngredients()));
	}
	
	@Test
	public void testAddNote() {	
		Recipe recipe1 = new Recipe("Recipe1", null);
		Date startDate = new Date(System.currentTimeMillis());
		Brew brew1 = new Brew(recipe1, startDate,1);
		brewController.store(brew1);
		
		Recipe recipe2 = new Recipe("Recipe2", null);
		Brew brew2 = new Brew(recipe2, startDate,1);
		brewController.store(brew2);
		
		brewController.addNote(brew1.getId(), newNote1, false);
		brewController.addNote(brew1.getId(), "New note 2", false);
		brewController.addNote(brew2.getId(), newNote1, true);
		
		brew1.addNote(newNote1, false);
		brew1.addNote("New note 2", false);
		brew2.addNote(newNote1, true);
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertEquals(brews.get(0).getNotes(),brew1.getNotes());
		assertEquals(brews.get(1).getNotes(),brew2.getNotes());
	}
	
	@Test
	public void testSetFinishDate() {	
		Recipe recipe1 = new Recipe("Recipe1", null);
		Date startDate = new Date(System.currentTimeMillis());
		Brew brew1 = new Brew(recipe1, startDate,1);
		brewController.store(brew1);
		
		Recipe recipe2 = new Recipe("Recipe2", null);
		Brew brew2 = new Brew(recipe2, startDate,1);
		brewController.store(brew2);
		
		brewController.setFinishDate(brew1.getId());
		brewController.setFinishDate(brew2.getId());
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertFalse(brews.get(0).getFinishDate() == null);
		assertFalse(brews.get(1).getFinishDate() == null);
	}
}
