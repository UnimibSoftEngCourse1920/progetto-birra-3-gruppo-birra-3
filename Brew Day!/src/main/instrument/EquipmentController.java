package main.instrument;

import main.IOController;

import java.io.File;
import java.util.Map;

public class EquipmentController {
	
	private String filepath;
	private IOController ioController;
	private static EquipmentController instance;
	
		
	private EquipmentController() {
		super();
		this.filepath = System.getProperty("user.dir") + "\\src\\Files\\Equipment.txt";
		this.ioController = new IOController();
	}
	
	public static EquipmentController getInstance() {
		if (instance == null) {
			instance = new EquipmentController();
		}
		return instance;
	}
	
	protected Equipment extractEquipment() {
		Equipment e = (Equipment) ioController.readObjectFromFile(filepath);
		return e;
	}
	
	protected void createEquipment(Map<String, Double> instruments) {
		try {
			if(instruments == null) throw new NullInstrumentsException();
			Equipment equipment = Equipment.getInstance();
			equipment.setInstruments(instruments);
			store(equipment);
		}catch(NullInstrumentsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	protected void store(Equipment equipment) {
		ioController.writeObjectToFile(equipment, filepath);
	}
	
	protected void update(Map<String, Double> instruments) {
		try {
			if(instruments == null) throw new NullInstrumentsException();
			Equipment equipment = extractEquipment();
			equipment.updateInstruments(instruments);
			store(equipment);
		}catch(NullInstrumentsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	protected void delete(String instrumentName) {
		Equipment equipment = extractEquipment();
		equipment.deleteInstrument(instrumentName);
		store(equipment);
	}
	
	//for only testing purpose
	public void deleteFile() {
		File file = new File(filepath);
		
		if (file.delete()) {
			System.out.println("\nFile deleted");
		} else {
			System.out.println("\nImpossible delete file");
		}
	}
}