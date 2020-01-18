package main.resources;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import main.IOController;

public class StorageController {
	
	private String filepath;
	private IOController ioController;
	private static StorageController instance;
	
	//only for testing
	private static Scanner scan = new Scanner(System.in);
	
	private StorageController() {
		super();
		this.filepath = System.getProperty("user.dir") + "\\src\\Files\\Storage.txt";
		this.ioController = new IOController();
	}
	
	public static StorageController getInstance() {
		if (instance == null) {
			instance = new StorageController();
		}
		
		return instance;
	}
	
	protected Storage extractStorage() {
		return (Storage) this.ioController.readObjectFromFile(this.filepath);	
	}
	
	
	protected void createStorage() {
		Map<String, Double> ingredients = new HashMap<>();
		insertIngredients(ingredients);
		Storage storage = Storage.getInstance();
		storage.setIngredients(ingredients);
		store(storage);
	}
	
	protected void insertIngredients(Map<String, Double> ingredients) {
		boolean b = true;
		
		while(b) {
			System.out.println("Insert the name of the ingredient you want to add: ");
			String name = scan.next();
			System.out.println("Insert the quantity of " + name + ": ");
			double quantity = scan.nextDouble();
			ingredients.put(name, quantity);
			
			System.out.println("Do you want to add another ingredient? [Y/N]");
			char c = scan.next().charAt(0);
			
			if (c == 'N' || c == 'n') {
				b = false;
			}
		}
	}
	
	protected void insertIngredients() {
		Storage storage = extractStorage();
		insertIngredients(storage.getIngredients());
		store(storage);
	}
	
	protected void store(Storage storage) {
		this.ioController.writeObjectToFile(storage, this.filepath);
	}
	
	protected void update() {
		Storage storage = extractStorage();
		Map<String, Double> ingredients = updatingIngredients(storage);
		storage.updateIngredients(ingredients);
		store(storage);
	}
	
	protected void delete() {
		Storage storage = extractStorage();
		String ingredientName = getIngredientNameToDelete();
		storage.deleteIngredient(ingredientName);
		store(storage);
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
	
	private Map<String, Double> updatingIngredients(Storage storage) {		
		
		for (Entry<String, Double> i : storage.getIngredients().entrySet()) {
		    System.out.println("Do you want to change the value of " + i.getKey() + " ? [Y/N]");
		    
		    // scanning char Y,y: yes; N,n: no
		    char c = scan.next().charAt(0);
		    
		    if (c == 'Y' || c == 'y') { //positive answer
		    	System.out.println("Please insert the new value of " + i.getKey() + ":");
		    	double d = scan.nextDouble();
		    	storage.getIngredients().put(i.getKey(), d);
		    	System.out.println("The new value of "  + i.getKey() + " is " + i.getValue());
		    }
		    
		    else if (c == 'N' || c == 'n') //negative answer
		    	System.out.println("Beautiful");
		}
		return storage.getIngredients();
	}
	
	private String getIngredientNameToDelete() {
		//asking which instrument to delete
		System.out.println("Please insert the name of the ingredient to delete: ");
		return scan.next();
	}
}
