package main.resources;

import java.util.HashMap;

import main.IOController;

public class StorageController {
	
	private static String filepath = System.getProperty("user.dir") + "\\src\\Files\\Storage.txt";
	private static IOController ioController = new IOController();
	
	protected static Storage extractStorage() {
		return (Storage) ioController.ReadObjectFromFile(filepath);
		
	}
	
	protected static void store(Storage storage) {
		ioController.WriteObjectToFile(storage, filepath);
	}
	
	protected static void update(HashMap<String, Double> ingredients) {
		Storage storage = extractStorage();
		storage.updateIngredients(ingredients);
		store(storage);
	}
	
	protected static void delete(String ingredient) {
		Storage storage = extractStorage();
		storage.deleteIngredient(ingredient);
		store(storage);
	}
}
