package main.instrument;

import main.IOController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class EquipmentController {
	
	private String filepath;
	private IOController ioController;
	private static EquipmentController instance;
	
	//only for testing
	private static Scanner scan = new Scanner(System.in);
	
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
		return (Equipment) ioController.readObjectFromFile(filepath);
	}
	
	protected void createEquipment() {
		Map<String, Double> instruments = new HashMap<String, Double>();
		insertInstruments(instruments);
		Equipment equipment = Equipment.getInstance();
		equipment.setInstruments(instruments);
		store(equipment);
	}
	
	protected void insertInstruments(Map<String, Double> instruments) {

		Map<String, Double> instruments1 = new HashMap<String, Double>();

		boolean b = true;
		
		while(b) {
			System.out.println("Insert the name of the instrument you want to add: ");
			String name = scan.next();
			System.out.println("Insert the capacity of " + name + ": ");
			double quantity = scan.nextDouble();
			instruments1.put(name, quantity);
			
			System.out.println("Do you want to add another ingredient? [Y/N]");
			char c = scan.next().charAt(0);
			
			if (c == 'Y' || c == 'y') {
				b = true;
			}
			else if (c == 'N' || c == 'n') {
				b = false;
			}
			//da creare un altro else per le exception
		}
	}
	
	protected void insertInstruments() {
		Equipment equipment = extractEquipment();
		insertInstruments(equipment.getInstruments());
		store(equipment);
	}
	
	protected void store(Equipment equipment) {
		ioController.writeObjectToFile(equipment, filepath);
	}
	
	protected void update() {
		Equipment equipment = extractEquipment();
		Map<String, Double> instruments = updatingInstruments();
		equipment.updateInstruments(instruments);
		store(equipment);
	}
	
	protected void delete() {
		Equipment equipment = extractEquipment();
		String instrumentName = getInstrumentNameToDelete();
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
	

	private Map<String, Double> updatingInstruments() {
		Equipment equipment = extractEquipment();

		
		
		for (Entry<String, Double> i : equipment.getInstruments().entrySet()) {
		    System.out.println("Do you want to change the value of " + i.getKey() + " ? [Y/N]");
		    
		    // scanning char Y,y: yes; N,n: no
		    char c = scan.next().charAt(0);
		    
		    if (c == 'Y' || c == 'y') { //positive answer
		    	System.out.println("Please insert the new value of " + i.getKey() + ":");
		    	double d = scan.nextDouble();
		    	equipment.getInstruments().put(i.getKey(), d);
		    	System.out.println("The new value of "  + i.getKey() + " is " + i.getValue());
		    }
		    
		    else if (c == 'N' || c == 'n') //negative answer
		    	System.out.println("Beautiful");
		}
		return equipment.getInstruments();
	}
	
	private String getInstrumentNameToDelete() {
		//asking which instrument to delete
		System.out.println("Please insert the name of the instrument to delete: ");

		String instrumentName = scan.next();
		
		return instrumentName;
	}
}