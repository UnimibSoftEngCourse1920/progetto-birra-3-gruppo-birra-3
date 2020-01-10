package main.resources;

import java.util.HashMap;

public class testStorage {

	public static void main(String[] args) {
		
		StorageController storageController = StorageController.getInstance();
		
		//Test store
        HashMap<String,Double> ingredients = new HashMap<String, Double>();
        ingredients.put("Malt", 10.0);
        ingredients.put("Hop", 20.0);
        Storage storage = Storage.getInstance();
        storage.setIngredients(ingredients);
        storageController.store(storage);
        
        //Test read
        System.out.println("Lo storage e': " + storageController.extractStorage().toString());
        
        //Test update
        HashMap<String,Double> newIngredients = ingredients;
        newIngredients.put("Yeast", 30.0);
        newIngredients.put("Malt", 15.0);
        storageController.update(newIngredients);
        System.out.println("\nLo storage modificato e': " + storageController.extractStorage().toString());
        
        //Test delete
        storageController.delete("Hop");
        System.out.println("\nLo storage con eliminazione e': " + storageController.extractStorage().toString());
        
        storageController.deleteFile();

	}

}
