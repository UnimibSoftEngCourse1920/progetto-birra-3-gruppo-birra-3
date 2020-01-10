package main.recipes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import main.resources.StorageController;

public class testBrew {
	
	public static void main(String[] args) {
		
	BrewController brewController = BrewController.getInstance();

	//Test addNote
	HashMap<String,Double> ingredients = new HashMap<String, Double>();
    ingredients.put("Malt", 10.0);
    ingredients.put("Hop", 20.0);
	Recipe recipe = new Recipe("test recipe", ingredients);
    Date startDate = new Date(System.currentTimeMillis());
    Brew brew = new Brew(recipe,startDate);
    
    brew.addNote(1,"test note 1",true);
    brew.addNote(2,"test note 2",false);
    brew.addNote(3,"test note 3",true);
    
    //Test modifyNote
    brew.modifyNote(-1,"test modify note 1");
    
    //Test deleteNote
    brew.deleteNote(3);
   
    //Test store
    recipe.incrementCountBrew();
    Brew brew2 = new Brew(recipe,startDate);
    brewController.store(brew);
    brewController.store(brew2);
    
    //Test read
    System.out.println("La brew e': " + brewController.extractBrew().toString());
    
    //Test update
    brewController.update(brew.getId(),-1,"test update note 1");
    System.out.println("\nLa brew e': " + brewController.extractBrew().toString());
    
    //Test delete
    brewController.delete(brew.getId());
    System.out.println("\nLa brew con eliminazione e': " + brewController.extractBrew().toString());
    
    brewController.deleteFile();
	}
}
