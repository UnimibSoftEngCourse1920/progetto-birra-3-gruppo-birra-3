package test.java.resources;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import main.java.IOController;
import main.java.resources.Storage;
import main.java.resources.StorageController;

public class StorageControllerTest {
	
	@Test
	public void createStorage() {
		Map<String,Double> ingredients = new HashMap<String, Double>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		StorageController sController = StorageController.getInstance();
		sController.createStorage(ingredients);
		
		Storage storage1 = Storage.getInstance();
		Storage storage2 = sController.extractStorage();
		
		assertEquals(storage1.getIngredients(), storage2.getIngredients());
		
		sController.deleteFile();
	}

	@Test
	public void testExtractStorage() {
		IOController ioController = new IOController();
		StorageController sController = StorageController.getInstance();
		Storage storage1 = Storage.getInstance();
		Map<String,Double> ingredients = new HashMap<>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		
		ioController.writeObjectToFile(storage1, System.getProperty("user.dir") + "\\src\\Files\\Storage.txt");
		
		Storage storage2 = sController.extractStorage();
		
		sController.deleteFile();
		
		assertEquals(storage1.getIngredients(), storage2.getIngredients());
	}

	@Test
	public void testStore() {
		Storage storage1 = Storage.getInstance();
		Map<String,Double> ingredients = new HashMap<>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		StorageController sController = StorageController.getInstance();
		sController.store(storage1);
		Storage storage2 = sController.extractStorage();
		
		assertEquals(storage1.getIngredients(), storage2.getIngredients());
		
		sController.deleteFile();		
	}

	@Test
	public void testUpdate() {
		Storage storage = Storage.getInstance();
		Map<String,Double> ingredients = new HashMap<>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		StorageController sController = StorageController.getInstance();
		sController.store(storage);
		
		ingredients.put("Hop", 30.0);
		ingredients.put("Yeast", 40.0);
		sController.update(ingredients);
		Storage storageExtracted = sController.extractStorage();
		
		assertEquals(10.0, storageExtracted.getIngredients().get("Malt").doubleValue(),0);
		assertEquals(30.0, storageExtracted.getIngredients().get("Hop").doubleValue(),0);
		assertEquals(40.0, storageExtracted.getIngredients().get("Yeast").doubleValue(),0);
		
		sController.deleteFile();
	}
}
