package test.java.instrument;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import main.java.instruments.Equipment;
import main.java.instruments.EquipmentController;
import main.java.recipes.Recipe;
import main.java.recipes.RecipeController;

public class EquipmentControllerTest {
	
	String kettle = "Kettle";
	String fermenter = "Fermenter";

	@Test
	public void testExtractEquipment() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put(kettle, 30.50);
		instruments.put(fermenter, 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		ec.createEquipment(instruments);
		
		Equipment e = Equipment.getInstance();
		Equipment e1 = ec.extractEquipment();
		
		assertEquals(e.getInstruments(),e1.getInstruments());
		
		ec.deleteFile();
	}

	@Test
	public void testCreateEquipment() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put(kettle, 30.50);
		instruments.put(fermenter, 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		ec.createEquipment(instruments);
		
		Equipment e1 = Equipment.getInstance();
		Equipment e2 = ec.extractEquipment();
		
		assertEquals(e1.getInstruments().get(kettle), e2.getInstruments().get(kettle));
		assertEquals(e1.getInstruments().get(fermenter), e2.getInstruments().get(fermenter));
		
		ec.deleteFile();
	}

	@Test
	public void testStore() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put(kettle, 30.50);
		instruments.put(fermenter, 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		Equipment e = Equipment.getInstance();
		e.setInstruments(instruments);
		ec.store(e);
		
		Equipment e1 = ec.extractEquipment();
		
		assertEquals(e.getInstruments().get(kettle), e1.getInstruments().get(kettle));
		assertEquals(e.getInstruments().get(fermenter), e1.getInstruments().get(fermenter));
		
		ec.deleteFile();
	}

	
	@Test
	public void testUpdate() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put(kettle, 30.50);
		instruments.put(fermenter, 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		Equipment e = Equipment.getInstance();
		e.setInstruments(instruments);
		ec.store(e);
		
		instruments.put("Pipe", 3.44);
		ec.update(instruments);
		
		Equipment e1 = ec.extractEquipment();
		
		assertEquals(e.getInstruments().get(kettle), e1.getInstruments().get(kettle));
		assertEquals(e.getInstruments().get(fermenter), e1.getInstruments().get(fermenter));
		assertEquals(e.getInstruments().get("Pipe"), e1.getInstruments().get("Pipe"));
		
		ec.deleteFile();
	}

	
	@Test
	public void testDelete() {
		Map<String, Double> instruments = new HashMap<>();
		instruments.put(kettle, 30.50);
		instruments.put(fermenter, 3.49);
		
		EquipmentController ec = EquipmentController.getInstance();
		ec.createEquipment(instruments);
		ec.delete(kettle);
		Equipment e = ec.extractEquipment();
		
		assertFalse(e.getInstruments().containsKey(kettle));
		
		ec.deleteFile();
	}
	
	@Test
	public void testUpdateEquipmentAndUpdateRecipe() {
		
		Map<String, Double> instruments = new HashMap<>();
		instruments.put(kettle, 30.00);
		instruments.put(fermenter, 20.00);
		
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
