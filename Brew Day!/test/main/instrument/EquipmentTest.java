package main.instrument;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class EquipmentTest {

	@Test
	public void testComputeCapacity() {
		Equipment equipment = Equipment.getInstance();
		Map<String, Double> instruments = new HashMap<>();
		
		instruments.put("Kettle", 25.0);
		instruments.put("Fermenter", 10.0);	
		equipment.setInstruments(instruments);
		
		assertEquals(35.0, equipment.computeCapacity(), 0.00);
		
		instruments.put("Pipe", 0.57);
		assertEquals(35.57, equipment.computeCapacity(), 0.00);
		
		instruments.remove("Kettle");
		assertEquals(10.57, equipment.computeCapacity(), 0.00);
	}

	@Test
	public void testUpdateInstruments() throws NullInstrumentsException {
		Equipment equipment = Equipment.getInstance();
		Map<String, Double> instruments = new HashMap<>();
		
		instruments.put("Kettle", 25.0);
		instruments.put("Fermenter", 10.0);	
		equipment.setInstruments(instruments);
		
		Map<String, Double> newInstruments = new HashMap<>();
		
		newInstruments.put("Kettle", 50.42);
		newInstruments.put("Pipe", 0.35);	
		equipment.updateInstruments(newInstruments);
		
		assertEquals(3, equipment.getInstruments().size());
		assertNotEquals(2, equipment.getInstruments().size());
		assertEquals((Double) 50.42, equipment.getInstruments().get("Kettle"));
		assertEquals((Double) 10.0, equipment.getInstruments().get("Fermenter"));
		assertEquals((Double) 0.35, equipment.getInstruments().get("Pipe"));
		assertFalse(equipment.getInstruments().containsValue(20.0));
	}

	@Test
	public void testDeleteInstrument() {
		Equipment equipment = Equipment.getInstance();
		Map<String, Double> instruments = new HashMap<>();
		
		instruments.put("Kettle", 25.0);
		instruments.put("Fermenter", 10.0);	
		equipment.setInstruments(instruments);
		
		equipment.deleteInstrument("Kettle");
		assertEquals(1, equipment.getInstruments().size());
		assertTrue(equipment.getInstruments().containsKey("Fermenter"));
		assertFalse(equipment.getInstruments().containsKey("Kettle"));
	}
}
