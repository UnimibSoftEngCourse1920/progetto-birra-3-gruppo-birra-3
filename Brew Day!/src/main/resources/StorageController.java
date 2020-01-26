package main.resources;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import main.IOController;
import main.IngredientNotFoundException;

@SuppressWarnings("serial")
public class StorageController implements Serializable{

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

	public Storage extractStorage() {
		return (Storage) this.ioController.readObjectFromFile(this.filepath);	
	}

	public void createStorage(Map<String,Double> ingredients) {
		Storage storage = Storage.getInstance();
		storage.setIngredients(ingredients);
		store(storage);
	}

	public void store(Storage storage) {
		this.ioController.writeObjectToFile(storage, this.filepath);
	}

	public void update(Map<String,Double> ingredients) {
		Storage storage = extractStorage();
		storage.updateIngredients(ingredients);
		Storage.getInstance().updateIngredients(ingredients);
		store(storage);	
	}

	protected void delete(String ingredient) {
		try {
			if (!extractStorage().getIngredients().containsKey(ingredient)) {
				throw new IngredientNotFoundException();
			}

			Storage storage = extractStorage();
			storage.deleteIngredient(ingredient);
			Storage.getInstance().deleteIngredient(ingredient);
			store(storage);

		} catch(IngredientNotFoundException e) {
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
