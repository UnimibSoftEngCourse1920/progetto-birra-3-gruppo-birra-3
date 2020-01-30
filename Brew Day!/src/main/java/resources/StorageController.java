package main.java.resources;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import main.java.IOController;

@SuppressWarnings("serial")
public class StorageController implements Serializable{

	private Path filepath;
	private IOController ioController;
	private static StorageController instance;

	private StorageController() {
		super();
		this.filepath = Paths.get(System.getProperty("user.dir") + "\\src\\Files\\Storage.txt");
		this.ioController = new IOController();
	}

	public static StorageController getInstance() {
		if (instance == null) {
			instance = new StorageController();
		}

		return instance;
	}

	/*
	 * Returns the Storage object stored in the Storage.txt file
	 */
	public Storage extractStorage() {
		return (Storage) this.ioController.readObjectFromFile(filepath.toString());	
	}

	/*
	 * Creates the Storage instance, initialize it with the given ingredients
	 * and stores it in the Storage.txt file
	 */
	public void createStorage(Map<String,Double> ingredients) {
		Storage storage = Storage.getInstance();
		storage.setIngredients(ingredients);
		store(storage);
	}

	public void store(Storage storage) {
		this.ioController.writeObjectToFile(storage, filepath.toString());
	}

	/*
	 * Updates the storage ingredients with the given ingredients.
	 * Updates both the Storage instance and the object stored in file. 
	 */
	public void update(Map<String,Double> ingredients) {
		Storage storage = extractStorage();
		storage.updateIngredients(ingredients);
		Storage.getInstance().updateIngredients(ingredients);
		store(storage);	
	}

	/*
	 * Deletes the Storage.txt file
	 */
	public void deleteFile() {
		File file = new File(filepath.toString());

		if (file.exists()) {
			try{
				Files.delete(filepath);
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
