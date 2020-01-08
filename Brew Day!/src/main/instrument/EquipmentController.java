package main.instrument;

import main.IOController;
import java.util.ArrayList;

public class EquipmentController {
	
	private static String filepath = System.getProperty("user.dir") + "\\src\\Files\\Equipment.txt";
	private static IOController ioController = new IOController();
	
	protected static Equipment extractEquipment() {
		return (Equipment) ioController.ReadObjectFromFile(filepath);
	}
	
	protected static void store(Equipment equipment) {
		ioController.WriteObjectToFile(equipment, filepath);
	}
	
	protected static void update() {
		Equipment equipment = extractEquipment();
		equipment.updateInstruments();
		store(equipment);
	}
	
	protected static void delete(String name) {
		Equipment equipment = extractEquipment();
		equipment.deleteInstrument(name);
		store(equipment);
	}
	

}
