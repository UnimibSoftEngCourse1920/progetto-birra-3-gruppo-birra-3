package main.resources;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

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
		
		assertTrue(storage1.equals(storage2));
		
		sController.deleteFile();
	}

	@Test
	public void testExtractStorage() {
		testStore();
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
		
		assertEquals(storageExtracted.getIngredients().get("Malt").doubleValue(), 10,0);
		assertEquals(storageExtracted.getIngredients().get("Hop").doubleValue(), 30,0);
		assertEquals(storageExtracted.getIngredients().get("Yeast").doubleValue(), 40,0);
		
		sController.deleteFile();
	}
	
	@Test
	public void testDelete() {
		Storage storage = Storage.getInstance();
		Map<String,Double> ingredients = new HashMap<>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		storage.setIngredients(ingredients);
		StorageController sController = StorageController.getInstance();
		sController.store(storage);	
		sController.delete("Malt");
		Storage storageExtracted = sController.extractStorage();
		
		assertTrue(storageExtracted.getIngredients().containsKey("Hop"));
		assertFalse(storageExtracted.getIngredients().containsKey("Malt"));
		
		sController.deleteFile();
	}
}
