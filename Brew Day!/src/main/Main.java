package main;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.instruments.EquipmentController;
import main.recipes.BrewController;
import main.recipes.Recipe;
import main.recipes.RecipeController;
import main.resources.StorageController;

public class Main {

	public static void main(String[] args) {
		int startingId = 0;
		
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 10.0); 
		ingredients1.put("Hop", 20.0); 
		new Recipe("Recipe 1", ingredients1);

		Map<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Yeast", 30.0); 
		ingredients2.put("Sugar", 40.0); 
		new Recipe("Recipe 2", ingredients2);
	
		try (FileInputStream fin = new FileInputStream(System.getProperty("user.dir") + "\\src\\Files\\CounterId.txt");
			DataInputStream din = new DataInputStream(fin)) {
		
	        startingId = din.readInt();
	    }
	    catch(FileNotFoundException fe)
	    { 
	      System.out.println("FileNotFoundException : " + fe);
	    }
	    catch(IOException ioe)
	    {
	      System.out.println("IOException : " + ioe);
	    }
		
		Recipe.setStartingId(startingId);
		
		BrewController bController = BrewController.getInstance();
		StorageController sController = StorageController.getInstance();
		RecipeController rController = RecipeController.getInstance();
		EquipmentController eController = EquipmentController.getInstance();
		
		bController.deleteFile();
		sController.deleteFile();
		rController.deleteFile();
		eController.deleteFile();
		
		File file = new File(System.getProperty("user.dir") + "\\src\\Files\\CounterId.txt");
		
		if (file.delete()) {
			System.out.println("\nFile deleted");
		} else {
			System.out.println("\nImpossible delete file");
		}
	}
}
