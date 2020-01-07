package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonIOException;

import main.recipes.Recipe;

public class Main {

	public static void main(String args[]) throws JsonIOException, IOException {
		
		String filepath = System.getProperty("user.dir") + "\\src\\Files\\Json\\test.json";
		
        IOController ioController = new IOController();
        
        //Creates a JSON file at the specified filepath and writes a Test object in it
        Test t1 = new Test(1);
        ioController.WriteObjectToJSONFile(t1,filepath);
        
        //Creates a Test object reading from the JSON file at the specified filepath
        Test t2 = ioController.ReadTestFromJSONFile(filepath);
        System.out.println(t2.toString()); //Prints it
        
		//Deletes the JSON file 
        File file = new File(filepath);
        file.delete();
    }
}
