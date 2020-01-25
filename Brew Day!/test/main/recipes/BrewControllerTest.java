package main.recipes;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import main.IOController;
import main.resources.Storage;
import main.resources.StorageController;


public class BrewControllerTest {
	
	@Test
	public void testGetInstance() {
		assertEquals(BrewController.getInstance(), BrewController.getInstance());
	}

	@Test
	public void testExtractBrew() {
		IOController iocontroller = new IOController();
		BrewController brewController = BrewController.getInstance();
		
		Map<String,Double> ingredients = new HashMap<>();
	    ingredients.put("Malt", 10.0);
	    ingredients.put("Hop", 20.0);
		Recipe recipe = new Recipe("Recipe", ingredients);
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate);
	    brew1.addNote("test note 1",true);
	    brew1.addNote("test note 2",false);
	    
	    recipe.incrementCountBrew();
	    
	    Brew brew2 = new Brew(recipe, startDate);
	    
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
		BrewController brewController = BrewController.getInstance();
		
		Recipe recipe = new Recipe("Recipe", new HashMap<>());
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate);
	    brew1.addNote("Note 1",true);
	    brew1.addNote("Note 2",false);
	   
		recipe.incrementCountBrew();
	    Brew brew2 = new Brew(recipe,startDate);
	    brew2.addNote("Note 3",true);
	    brew2.addNote("Note 4",false);
	    
	    brewController.store(brew1);
	    brewController.store(brew2);
	    
	    List<Brew> brews = brewController.extractBrew();
	    
	    brewController.deleteFile();
	        
	  	assertTrue(brews.get(0).equals(brew1));	
	  	assertTrue(brews.get(1).equals(brew2));
	}

	@Test
	public void testUpdateNote() {
		BrewController brewController = BrewController.getInstance();
		
		Recipe recipe1 = new Recipe("Recipe1", null);
		Date startDate = new Date(System.currentTimeMillis());
		Brew brew1 = new Brew(recipe1, startDate);
		brew1.addNote("Note 1", false);
		brew1.addNote("Note 2", false);
		brewController.store(brew1);
		
		Recipe recipe2 = new Recipe("Recipe2", null);
		Brew brew2 = new Brew(recipe2, startDate);
		brew2.addNote("Note 3", true);
		brewController.store(brew2);
		
		System.out.println(brew1.getId());
		System.out.println(brew2.getId());
		
		String txt1 = "New Note 1";
		String txt2 = "New note 3";
		
		brewController.updateNote(brew1.getId(), 2, txt1);
		brewController.updateNote(brew2.getId(), -3, txt2);
		
		brew1.modifyNote(2, txt1);
		brew2.modifyNote(-3, txt2);
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertEquals(brews.get(0).getNotes(),brew1.getNotes());
		assertEquals(brews.get(1).getNotes(),brew2.getNotes());	
	}
	
	@Test
	public void testDelete() {
		BrewController brewController = BrewController.getInstance();
		
		HashMap<String,Double> ingredients = new HashMap<>();
	    ingredients.put("Malt", 10.0);
	    ingredients.put("Hop", 20.0);
		Recipe recipe = new Recipe("Recipe", ingredients);
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate);
	    brew1.addNote("Note 1",true);
	    brew1.addNote("Note 2",false);
		recipe.incrementCountBrew();
		
	    Brew brew2 = new Brew(recipe,startDate);
	    brew2.addNote("Note 3",true);
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
		BrewController brewController = BrewController.getInstance();

		Recipe recipe = new Recipe("Recipe", new HashMap<>());
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate);
	    brew1.addNote("Note 1",true);
	    brew1.addNote("Note 2",false);
	    brew1.addNote("Note 3",true);
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
		BrewController brewController = BrewController.getInstance();
		
		HashMap<String,Double> sIngredients = new HashMap<>();
	    sIngredients.put("Malt", 20.0);
	    sIngredients.put("Hop", 30.0);
		Storage storage = Storage.getInstance();
		storage.setIngredients(sIngredients);
		
		HashMap<String,Double> rIngredients = new HashMap<>();
	    rIngredients.put("Malt", 10.0);
	    rIngredients.put("Hop", 20.0);
		Recipe recipe = new Recipe("Recipe", rIngredients);
		Brew brew1 = recipe.createBrew();
		
		brewController.store(brew1);
		
		brewController.cancel(brew1.getId());
		
		StorageController sController = StorageController.getInstance();
		Storage extStorage = sController.extractStorage();
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertTrue(brews.isEmpty());
		assertTrue(sIngredients.equals(extStorage.getIngredients()));
	}
	
	@Test
	public void tesAddNote() {
		BrewController brewController = BrewController.getInstance();
		
		Recipe recipe1 = new Recipe("Recipe1", null);
		Date startDate = new Date(System.currentTimeMillis());
		Brew brew1 = new Brew(recipe1, startDate);
		brewController.store(brew1);
		
		Recipe recipe2 = new Recipe("Recipe2", null);
		Brew brew2 = new Brew(recipe2, startDate);
		brewController.store(brew2);
		
		brewController.addNote(brew1.getId(), "New note 1", false);
		brewController.addNote(brew1.getId(), "New note 2", false);
		brewController.addNote(brew2.getId(), "New note 1", true);
		
		brew1.addNote("New note 1", false);
		brew1.addNote("New note 2", false);
		brew2.addNote("New note 1", true);
		
		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertEquals(brews.get(0).getNotes(),brew1.getNotes());
		assertEquals(brews.get(1).getNotes(),brew2.getNotes());
	}

}
