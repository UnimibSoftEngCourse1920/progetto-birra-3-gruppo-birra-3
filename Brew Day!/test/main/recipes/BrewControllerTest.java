package main.recipes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
		
		HashMap<String,Double> ingredients = new HashMap<>();
	    ingredients.put("Malt", 10.0);
	    ingredients.put("Hop", 20.0);
		Recipe recipe = new Recipe("Recipe", ingredients);
	    Date startDate = new Date(System.currentTimeMillis());
	    Brew brew = new Brew(recipe,startDate);
	    brew.addNote(1,"test note 1",true);
	    brew.addNote(2,"test note 2",false);
	    
	    iocontroller.writeObjectToFile(brew, System.getProperty("user.dir") + "\\src\\Files\\Brew.txt"); 
		ArrayList<Brew> brews = brewController.extractBrew();
		//assertTrue(brews.get(0).getId() == brew.getId());
		assertTrue(brews.get(0).equals(brew));
		
		brewController.deleteFile();
	}

	@Test
	public void testStore() {
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
	    
	    ArrayList<Brew> brews = brewController.extractBrew();
	    //assertTrue(brews.get(0).getId() == brew1.getId());
	    //assertTrue(brews.get(1).getId() == brew2.getId());
	  	assertTrue(brews.get(0).equals(brew1));
	  	assertTrue(brews.get(1).equals(brew2));
	  	
	  	brewController.deleteFile();
	}

	@Test
	public void testUpdate() {
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
	    
	    String noteEdit1 = "Note 1 Edit";
		String noteEdit2 = "Note 4 Edit";
		brewController.deleteNote(brew2.getId(),2);
		brewController.updateNote(brew1.getId(),-1,noteEdit1);
		brewController.updateNote(brew2.getId(),4,noteEdit2);
		
		brew1.deleteNote(2);
		brew1.modifyNote(-1,noteEdit1);
		brew2.modifyNote(4,noteEdit2);
		
		ArrayList<Brew> brews = brewController.extractBrew();
		assertTrue(brews.get(0).equals(brew1));
	  	assertTrue(brews.get(1).equals(brew2));
	  	
	  	brewController.deleteFile();
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
	    
	    brewController.delete(1.1);
		brewController.delete(2.1);
		
		ArrayList<Brew> brews = brewController.extractBrew();
		assertTrue(brews.isEmpty());
		
		brewController.deleteFile();
	}

}
