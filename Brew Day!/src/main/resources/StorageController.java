package main.resources;

import java.io.File;
import java.util.HashMap;

import main.IOController;

public class StorageController {
	
	private String filepath;
	private IOController ioController;
	private static StorageController instance;
	
	private StorageController() {
		super();
		this.filepath = System.getProperty("user.dir") + "\\src\\Files\\Storage.txt";
		this.ioController = new IOController();
	}
	
	public static StorageController getInstance() {
		if (instance == null) {
			instance = new StorageController();
		}
		
		return instance;
	}
	
	protected Storage extractStorage() {
		return (Storage) this.ioController.ReadObjectFromFile(this.filepath);	
	}
	
	protected void store(Storage storage) {
		this.ioController.WriteObjectToFile(storage, this.filepath);
	}
	
	protected void update(HashMap<String, Double> ingredients) {
		Storage storage = extractStorage();
		storage.updateIngredients(ingredients);
		store(storage);
	}
	
	protected void delete(String ingredient) {
		Storage storage = extractStorage();
		storage.deleteIngredient(ingredient);
		store(storage);
	}
	
	//for only testing purpose
	public void deleteFile() {
		File file = new File(filepath);
		file.delete();
	}
}
