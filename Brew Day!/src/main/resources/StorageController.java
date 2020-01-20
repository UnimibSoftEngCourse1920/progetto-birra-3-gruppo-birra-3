package main.resources;

import java.io.File;
import java.util.Map;

import main.IOController;
import main.NullIngredientsException;

public class StorageController {

	private String filepath;
	private IOController ioController;
	private static StorageController instance;

	//Protected
	public StorageController() {
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
		return (Storage) this.ioController.readObjectFromFile(this.filepath);	
	}

	//Protected
	public void createStorage(Map<String,Double> ingredients) {
		try {
			if (ingredients == null) {
				throw new NullIngredientsException();
			}

			Storage storage = Storage.getInstance();
			storage.setIngredients(ingredients);
			store(storage);

		} catch (NullIngredientsException e){
			System.err.println(e.getMessage());
		}
	}

	protected void store(Storage storage) {
		this.ioController.writeObjectToFile(storage, this.filepath);
	}

	protected void update(Map<String,Double> ingredients) {
		try {
			if (ingredients == null) {
				throw new NullIngredientsException();
			}

			Storage storage = extractStorage();
			storage.updateIngredients(ingredients);
			store(storage);

		} catch (NullIngredientsException e){
			System.err.println(e.getMessage());
		}	
	}

	protected void delete(String ingredient) {
		try {
			if (ingredient == null) {
				throw new NullIngredientsException();
			}

			Storage storage = extractStorage();
			storage.deleteIngredient(ingredient);
			store(storage);

		} catch (NullIngredientsException e){
			System.err.println(e.getMessage());
		}	
	}

	//for only testing purpose
	public void deleteFile() {
		File file = new File(filepath);
		if (file.delete()) {
			System.out.println("\nFile deleted");
		} else {
			System.out.println("\nImpossible delete file");
		}
	}
}
