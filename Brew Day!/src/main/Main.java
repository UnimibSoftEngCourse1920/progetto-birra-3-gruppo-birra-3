package main;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

import main.recipes.Brew;
import main.recipes.Recipe;

public class Main {

	public static void main(String args[]) {
		
		//test
		String filepath = System.getProperty("user.dir") + "\\src\\Files\\objects.txt";
		 
        IOController ioController = new IOController();
        
        //Write object to file
        HashMap<String,Double> i = new HashMap<>();
        i.put("Malt", 150.0);
        i.put("Yeast", 300.0);
        Recipe r1 = new Recipe("Recipe1", i);
        Recipe r2 = new Recipe("Recipe2", i);
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        recipes.add(r1);
        recipes.add(r2);
        ioController.WriteObjectToFile(recipes, filepath);
        ArrayList<Recipe> recipes2 = (ArrayList<Recipe>) ioController.ReadObjectFromFile(filepath);
        System.out.println(recipes2.toString());
        File file = new File(filepath);
        file.delete();
    }
	
	
	
	
	
}
