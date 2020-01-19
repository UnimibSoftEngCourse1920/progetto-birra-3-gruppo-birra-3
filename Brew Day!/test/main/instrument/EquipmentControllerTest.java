package main.instrument;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

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
}
