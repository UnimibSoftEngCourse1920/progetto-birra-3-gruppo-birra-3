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

	public Equipment extractEquipment() {
		return (Equipment) ioController.readObjectFromFile(filepath.toString());
	}

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

	public void update(Map<String, Double> instruments) {
		try {
			if(instruments == null) throw new NullInstrumentsException();
			
			Equipment equipment = extractEquipment();
			double oldCapacity = equipment.getCapacity();
			equipment.updateInstruments(instruments);
			Equipment.getInstance().updateInstruments(instruments);
			store(equipment);
			double newCapacity = equipment.getCapacity();
			double ingredientsMultiplier = newCapacity/oldCapacity;
			RecipeController recipeController = RecipeController.getInstance();
			recipeController.updateAllRecipesOnNewEquipment(ingredientsMultiplier);
			
		}catch(NullInstrumentsException e) {
			System.out.println(e.getMessage());
		}
	}

	public void delete(String instrumentName) {
		Equipment equipment = extractEquipment();
		equipment.deleteInstrument(instrumentName);
		Equipment.getInstance().deleteInstrument(instrumentName);
		store(equipment);
	}
	
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