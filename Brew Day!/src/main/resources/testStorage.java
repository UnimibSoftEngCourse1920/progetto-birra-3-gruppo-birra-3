package main.resources;

import java.util.HashMap;

public class testStorage {

	public static void main(String[] args) {
		
		//Test store
        HashMap<String,Double> ingredients = new HashMap<String, Double>();
        ingredients.put("Malt", 10.0);
        ingredients.put("Hop", 20.0);
        Storage storage = Storage.getInstance();
        storage.setIngredients(ingredients);
        StorageController.store(storage);
        
        //Test read
        System.out.println("Lo storage è: " + StorageController.extractStorage().toString());
        
        //Test update
        HashMap<String,Double> newIngredients = ingredients;
        newIngredients.put("Yeast", 30.0);
        newIngredients.put("Malt", 15.0);
        StorageController.update(newIngredients);
        System.out.println("Lo storage modificato è: " + StorageController.extractStorage().toString());
        
        //Test delete
        StorageController.delete("Hop");
        System.out.println("Lo storage con eliminazione è: " + StorageController.extractStorage().toString());
        
        StorageController.deleteFile();

	}

}
