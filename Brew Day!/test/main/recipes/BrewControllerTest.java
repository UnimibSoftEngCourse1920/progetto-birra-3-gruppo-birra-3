package main.recipes;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import main.IOController;


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
	    brew1.addNote(1,"test note 1",true);
	    brew1.addNote(2,"test note 2",false);
	    
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
	    brew1.addNote(1,"Note 1",true);
	    brew1.addNote(2,"Note 2",false);
	   
		recipe.incrementCountBrew();
	    Brew brew2 = new Brew(recipe,startDate);
	    brew2.addNote(3,"Note 3",true);
	    brew2.addNote(4,"Note 4",false);
	    
	    brewController.store(brew1);
	    brewController.store(brew2);
	    
	    List<Brew> brews = brewController.extractBrew();
	    
	    brewController.deleteFile();
	        
	  	assertTrue(brews.get(0).equals(brew1));	
	  	assertTrue(brews.get(1).equals(brew2));
	}

	@Test
	public void testUpdate() {
		BrewController brewController = BrewController.getInstance();
		
		Recipe recipe = new Recipe("Recipe", new HashMap<>());
	    Brew brew1 = recipe.createBrew();
	    brew1.addNote(1,"Note 1",true);
	    brew1.addNote(2,"Note 2",false);
		
	    Brew brew2 = recipe.createBrew();
	    brew2.addNote(3,"Note 3",true);
	    brew2.addNote(4,"Note 4",false);
	    brewController.store(brew1);
	    brewController.store(brew2);  
	    
	    String noteEdit1 = "Note 1 Edit";
		String noteEdit2 = "Note 4 Edit";
		
		brewController.updateNote(brew1.getId(),-1,noteEdit1);
		brewController.updateNote(brew2.getId(),4,noteEdit2);
		
		brew1.modifyNote(-1, noteEdit1);
		brew2.modifyNote(4, noteEdit2);
		
		ArrayList<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertTrue(brews.get(0).equals(brew1));
	  	assertTrue(brews.get(1).equals(brew2));
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
	    brew1.addNote(1,"Note 1",true);
	    brew1.addNote(2,"Note 2",false);
		recipe.incrementCountBrew();
		
	    Brew brew2 = new Brew(recipe,startDate);
	    brew2.addNote(3,"Note 3",true);
	    brew2.addNote(4,"Note 4",false);
	    
	    brewController.store(brew1);
	    brewController.store(brew2);
	    
	    brewController.delete(brew1.getId());
		brewController.delete(brew2.getId());
		
		ArrayList<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertTrue(brews.isEmpty());
	}
	
	@Test
	public void testDeleteNote() {
		BrewController brewController = BrewController.getInstance();

		Recipe recipe = new Recipe("Recipe", new HashMap<>());
	    Date startDate = new Date(System.currentTimeMillis());
	    
	    Brew brew1 = new Brew(recipe,startDate);
	    brew1.addNote(1,"Note 1",true);
		recipe.incrementCountBrew();
	    
	    brewController.store(brew1);
	    
	    brewController.deleteNote(brew1.getId(), -1);
		
		ArrayList<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();
		
		assertTrue(brews.get(0).getNotes().isEmpty());
	}

}
