package main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import main.recipes.Recipe;

public class Main {

	public static void main(String args[]) {
		
		String filepath = System.getProperty("user.dir") + "\\src\\Files\\objects.txt";
		 
        IOController ioController = new IOController();
        
        //Write object to file
        Map<String,Double> i = new HashMap<>();
        i.put("Malt", 150.0);
        i.put("Yeast", 300.0);
        Recipe r1 = new Recipe(1, "Test Recipe", i);
        ioController.WriteObjectToFile(r1);
 
        //Read object from file
        Recipe r2 = (Recipe) ioController.ReadObjectFromFile(filepath);
        System.out.println(r2.toString()); 
        
        File file = new File(filepath);
        file.delete();
    }
}
