package main.resources;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class StorageTest {

	@Test
	public void testUpdateIngredients() {
		Storage storage = Storage.getInstance();
		
		Map<String, Double> ingredients = new HashMap<>();
		
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		
		storage.setIngredients(ingredients);
		
		Map<String, Double> newIngredients = new HashMap<>();
		
		newIngredients.put("Hop", 30.0);
		newIngredients.put("Yeast", 40.0);
		
		storage.updateIngredients(newIngredients);
		
		assertTrue(storage.getIngredients().containsKey("Malt"));
		assertTrue(storage.getIngredients().containsKey("Hop"));
		assertTrue(storage.getIngredients().containsKey("Yeast"));
		assertTrue(storage.getIngredients().containsValue(10.0));
		assertTrue(storage.getIngredients().containsValue(30.0));
		assertTrue(storage.getIngredients().containsValue(40.0));
		assertFalse(storage.getIngredients().containsValue(20.0));
	}

	@Test
	public void testDeleteIngredient() {
		Storage storage = Storage.getInstance();
		
		Map<String, Double> ingredients = new HashMap<>();
		
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		
		storage.setIngredients(ingredients);
		
		storage.deleteIngredient("Malt");
		
		assertTrue(storage.getIngredients().containsKey("Hop"));
		assertFalse(storage.getIngredients().containsKey("Malt"));
	}

	@Test
	public void testGetInstance() {
		assertEquals(Storage.getInstance(), Storage.getInstance());
	}

}
