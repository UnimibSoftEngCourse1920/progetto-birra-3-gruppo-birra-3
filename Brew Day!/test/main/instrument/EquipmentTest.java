package main.instrument;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import main.instruments.Equipment;
import main.instruments.NullInstrumentsException;

public class EquipmentTest {
	
	String kettle = "Kettle";
	String fermenter = "Fermenter";

	@Test
	public void testComputeCapacity() {
		Equipment equipment = Equipment.getInstance();
		Map<String, Double> instruments = new HashMap<>();
		
		instruments.put(kettle, 25.0);
		instruments.put(fermenter, 10.0);	
		equipment.setInstruments(instruments);
		
		assertEquals(35.0, equipment.computeCapacity(instruments), 0.00);
		
		instruments.put("Pipe", 0.57);
		assertEquals(35.57, equipment.computeCapacity(instruments), 0.00);
		
		instruments.remove(kettle);
		assertEquals(10.57, equipment.computeCapacity(instruments), 0.00);
	}

	@Test
	public void testUpdateInstruments() throws NullInstrumentsException {
		Equipment equipment = Equipment.getInstance();
		Map<String, Double> instruments = new HashMap<>();
		
		instruments.put(kettle, 25.0);
		instruments.put(fermenter, 10.0);	
		equipment.setInstruments(instruments);
		
		Map<String, Double> newInstruments = new HashMap<>();
		
		newInstruments.put(kettle, 50.42);
		newInstruments.put("Pipe", 0.35);	
		equipment.updateInstruments(newInstruments);
		
		assertEquals(3, equipment.getInstruments().size());
		assertNotEquals(2, equipment.getInstruments().size());
		assertEquals((Double) 50.42, equipment.getInstruments().get(kettle));
		assertEquals((Double) 10.0, equipment.getInstruments().get(fermenter));
		assertEquals((Double) 0.35, equipment.getInstruments().get("Pipe"));
		assertFalse(equipment.getInstruments().containsValue(20.0));
	}

	@Test
	public void testDeleteInstrument() {
		Equipment equipment = Equipment.getInstance();
		Map<String, Double> instruments = new HashMap<>();
		
		instruments.put(kettle, 25.0);
		instruments.put(fermenter, 10.0);	
		equipment.setInstruments(instruments);
		
		equipment.deleteInstrument(kettle);
		assertEquals(1, equipment.getInstruments().size());
		assertTrue(equipment.getInstruments().containsKey(fermenter));
		assertFalse(equipment.getInstruments().containsKey(kettle));
	}
}
