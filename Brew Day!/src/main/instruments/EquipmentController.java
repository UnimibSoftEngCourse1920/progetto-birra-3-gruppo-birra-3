package main.instruments;

import main.IOController;
import main.recipes.RecipeController;

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

	public Equipment extractEquipment() {
		return (Equipment) ioController.readObjectFromFile(filepath);
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
		ioController.writeObjectToFile(equipment, filepath);
	}

	public void update(Map<String, Double> instruments) {
		try {
			if(instruments == null) throw new NullInstrumentsException();
			
			Equipment equipment = Equipment.getInstance();
			double oldCapacity = equipment.getCapacity();
			equipment.updateInstruments(instruments);
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
		Equipment equipment = Equipment.getInstance();
		equipment.deleteInstrument(instrumentName);
		store(equipment);
	}

	//for only testing purpose
	public void deleteFile() {
		File file = new File(filepath);

		if (file.exists()) {
			if (file.delete()) {
				System.out.println("\nFile deleted");
			} else {
				System.out.println("\nImpossible delete file");
			}
		}
	}
}