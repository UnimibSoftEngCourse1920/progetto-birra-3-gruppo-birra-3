package main.instrument;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


import main.instruments.Equipment;
import main.instruments.EquipmentController;
import main.recipes.Recipe;
import main.recipes.RecipeController;

public class EquipmentControllerTest {

	@Test
	public void testExtractEquipment() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put("Kettle", 30.50);
		instruments.put("Fermenter", 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		ec.createEquipment(instruments);
		
		Equipment e = Equipment.getInstance();
		Equipment e1 = ec.extractEquipment();
		
		assertTrue(e.equals(e1));
		
		ec.deleteFile();
	}

	@Test
	public void testCreateEquipment() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put("Kettle", 30.50);
		instruments.put("Fermenter", 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		ec.createEquipment(instruments);
		
		Equipment e1 = Equipment.getInstance();
		Equipment e2 = ec.extractEquipment();
		
		assertEquals(e1.getInstruments().get("Kettle"), e2.getInstruments().get("Kettle"));
		assertEquals(e1.getInstruments().get("Fermenter"), e2.getInstruments().get("Fermenter"));
		
		ec.deleteFile();
	}

	@Test
	public void testStore() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put("Kettle", 30.50);
		instruments.put("Fermenter", 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		Equipment e = Equipment.getInstance();
		e.setInstruments(instruments);
		ec.store(e);
		
		Equipment e1 = ec.extractEquipment();
		
		assertEquals(e.getInstruments().get("Kettle"), e1.getInstruments().get("Kettle"));
		assertEquals(e.getInstruments().get("Fermenter"), e1.getInstruments().get("Fermenter"));
		
		ec.deleteFile();
	}

	
	@Test
	public void testUpdate() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put("Kettle", 30.50);
		instruments.put("Fermenter", 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		Equipment e = Equipment.getInstance();
		e.setInstruments(instruments);
		ec.store(e);
		
		instruments.put("Pipe", 3.44);
		ec.update(instruments);
		
		Equipment e1 = ec.extractEquipment();
		
		assertEquals(e.getInstruments().get("Kettle"), e1.getInstruments().get("Kettle"));
		assertEquals(e.getInstruments().get("Fermenter"), e1.getInstruments().get("Fermenter"));
		assertEquals(e.getInstruments().get("Pipe"), e1.getInstruments().get("Pipe"));
		
		ec.deleteFile();
	}

	
	@Test
	public void testDelete() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put("Kettle", 30.50);
		instruments.put("Fermenter", 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		ec.createEquipment(instruments);
		ec.delete("Kettle");
		Equipment e = ec.extractEquipment();
		
		assertFalse(e.getInstruments().containsKey("Kettle"));
		
		ec.deleteFile();
	}
	
	@Test
	public void testUpdateEquipmentAndUpdateRecipe() {
		
		Map<String, Double> instruments = new HashMap<>();
		instruments.put("Kettle", 30.00);
		instruments.put("Fermenter", 20.00);
		
		EquipmentController ec = EquipmentController.getInstance();
		Equipment e = Equipment.getInstance();
		e.setInstruments(instruments);
		e.setCapacity(e.computeCapacity(instruments));
		ec.store(e);
		
		Map<String, Double> ingredients = new HashMap<>();
		ingredients.put("Malt", 5.00);
		ingredients.put("Hops", 5.00);
		
		Recipe r = new Recipe("Recipe test", ingredients);
		RecipeController rc = RecipeController.getInstance();
		rc.store(r);
		
		instruments.put("Pipe", 50.00);
		ec.update(instruments);
		
		ArrayList<Recipe> extRecipes = (ArrayList<Recipe>) rc.extractRecipe();
		Recipe newR = extRecipes.get(0);
		
		Map<String, Double> ingredientsR = r.getIngredients();
		Map<String, Double> ingredientsNewR = newR.getIngredients();
		
		
		// "*2" because is the multiplier in this specific case
		assertTrue(ingredientsNewR.get("Malt").equals((ingredientsR.get("Malt")*2)));
		assertTrue(ingredientsNewR.get("Hops").equals(ingredientsR.get("Hops")*2));
		
		
		ec.deleteFile();
		rc.deleteFile();
	}
}
