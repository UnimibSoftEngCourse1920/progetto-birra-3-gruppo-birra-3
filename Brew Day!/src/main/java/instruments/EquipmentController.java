package main.java.instruments;

import main.java.IOController;
import main.java.recipes.RecipeController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class EquipmentController {

	private Path filepath;
	private IOController ioController;
	private static EquipmentController instance;


	private EquipmentController() {
		super();
		this.filepath = Paths.get(System.getProperty("user.dir") + "\\src\\Files\\Equipment.txt");
		this.ioController = new IOController();
	}

	public static EquipmentController getInstance() {
		if (instance == null) {
			instance = new EquipmentController();
		}
		return instance;
	}

	/*
	 * Returns the Equipment object stored in the Equipment.txt file
	 */
	public Equipment extractEquipment() {
		return (Equipment) ioController.readObjectFromFile(filepath.toString());
	}

	/*
	 * Creates the Equipment instance, initialize it with the given instruments
	 * and the relative capacity and stores it in the Equipment.txt file
	 */
	public void createEquipment(Map<String, Double> instruments) {
		try {
			if(instruments == null) throw new NullInstrumentsException();
			Equipment equipment = Equipment.getInstance();
			equipment.setInstruments(instruments);
			equipment.setCapacity(equipment.computeCapacity(instruments));
			store(equipment);
		}catch(NullInstrumentsException e) {
			System.out.println(e.getMessage());
		}
	}

	public void store(Equipment equipment) {
		ioController.writeObjectToFile(equipment, filepath.toString());
	}

	/*
	 * Updates the equipment instruments with the given instruments, computes the 
	 * multiplier and uses it to update all the recipes ingredients.
	 * Updates both the Equipment instance and the object stored in file. 
	 */
	public void update(Map<String, Double> instruments) {
		try {
			if(instruments == null) throw new NullInstrumentsException();
			
			//updates the equipment
			Equipment equipment = extractEquipment();
			double oldCapacity = equipment.getCapacity();
			equipment.updateInstruments(instruments);
			Equipment.getInstance().updateInstruments(instruments);
			store(equipment);
			
			//updates the recipes
			double newCapacity = equipment.getCapacity();
			double ingredientsMultiplier = newCapacity/oldCapacity;
			RecipeController recipeController = RecipeController.getInstance();
			recipeController.updateAllRecipesOnNewEquipment(ingredientsMultiplier);
			
		}catch(NullInstrumentsException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Deletes the given instrument and computes the new capacity.
	 * Deletes it from both the Equipment instance and the object stored in file.
	 */
	public void delete(String instrumentName) {
		Equipment equipment = extractEquipment();
		equipment.deleteInstrument(instrumentName);
		Equipment.getInstance().deleteInstrument(instrumentName);
		store(equipment);
	}
	
	/*
	 * Deletes the Equipment.txt file
	 */
	public void deleteFile() {
		File file = new File(filepath.toString());

		if (file.exists()) {
			try{
				Files.delete(filepath);
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}