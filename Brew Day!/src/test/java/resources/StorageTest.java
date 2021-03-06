package test.java.resources;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import main.java.resources.Storage;

public class StorageTest {
	
	String yeast = "Yeast";

	@Test
	public void testUpdateIngredients() {
		
		Storage storage = Storage.getInstance();
		
		Map<String, Double> ingredients = new HashMap<>();
		
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		
		storage.setIngredients(ingredients);
		
		Map<String, Double> newIngredients = new HashMap<>();
		
		newIngredients.put("Hop", 30.0);
		newIngredients.put(yeast, 40.0);
		
		storage.updateIngredients(newIngredients);
		
		assertTrue(storage.getIngredients().containsKey("Malt"));
		assertTrue(storage.getIngredients().containsKey("Hop"));
		assertTrue(storage.getIngredients().containsKey(yeast));
		assertEquals(10.0, storage.getIngredients().get("Malt").doubleValue(),0);
		assertEquals(30.0, storage.getIngredients().get("Hop").doubleValue(),0);
		assertEquals(40.0, storage.getIngredients().get(yeast).doubleValue(),0);
	}

	@Test
	public void testGetInstance() {
		assertEquals(Storage.getInstance(), Storage.getInstance());
	}

}
