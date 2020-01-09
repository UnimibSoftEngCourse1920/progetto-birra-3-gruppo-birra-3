package main.instrument;

import main.IOController;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class EquipmentController {
	
	private static String filepath = System.getProperty("user.dir") + "\\src\\Files\\Equipment.txt";
	private static IOController ioController = new IOController();
	
	static Scanner scan = new Scanner(System.in);
	
	protected static Equipment extractEquipment() {
		return (Equipment) ioController.ReadObjectFromFile(filepath);
	}
	
	protected static void createEquipment() {
		HashMap<String, Double> instruments = new HashMap<String, Double>();
		boolean b = true;
		
		while(b) {
			System.out.println("Insert the name of the instrument you want to add: ");
			String name = scan.next();
			System.out.println("Insert the capacity of " + name + ": ");
			double capacity = scan.nextDouble();
			instruments.put(name, capacity);
			
			System.out.println("Do you want to add another instrument? [Y/N]");
			char c = scan.next().charAt(0);
			
			if (c == 'Y' || c == 'y') {
				b = true;
			}
			else if (c == 'N' || c == 'n') {
				b = false;
			}
		}
		Equipment equipment = Equipment.getInstance();
		equipment.setInstruments(instruments);
		store(equipment);
	}
	
	protected static void store(Equipment equipment) {
		ioController.WriteObjectToFile(equipment, filepath);
	}
	
	protected static void update() {
		Equipment equipment = extractEquipment();
		HashMap<String, Double> instruments = updatingInstruments();
		equipment.updateInstruments(instruments);
		store(equipment);
	}
	
	protected static void delete() {
		Equipment equipment = extractEquipment();
		String instrumentName = getInstrumentNameToDelete();
		equipment.deleteInstrument(instrumentName);
		store(equipment);
	}
	
	//for only testing purpose
	public static void deleteFile() {
		File file = new File(filepath);
		file.delete();
	}
	
	private static HashMap<String, Double> updatingInstruments() {
		Equipment equipment = Equipment.getInstance();
		
		
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
		//scan.close();
		return equipment.getInstruments();
	}
	
	private static String getInstrumentNameToDelete() {
		//Scanner scan = new Scanner(System.in);
		
		//asking which instrument to delete
		System.out.println("Please insert the name of the instrument to delete: ");

		String instrumentName = scan.next();
		//scan.close();
		
		return instrumentName;
		
	}
}