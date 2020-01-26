package main.resources;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.IOController;

@SuppressWarnings("serial")
public class StorageController implements Serializable{

	private String filepath;
	private IOController ioController;
	private static StorageController instance;
	Logger logger = Logger.getLogger(IOController.class.getName());

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

	//for only testing purpose
	public void deleteFile() {
		File file = new File(filepath);
		if (file.exists()) {
			if (file.delete()) {
				logger.log(Level.INFO,"\nFile deleted");
			} else {
				logger.log(Level.INFO,"\nImpossible delete file");
			}
		}
	}
}
