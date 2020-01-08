package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonIOException;

import main.recipes.Recipe;

public class Main {

	public static void main(String args[]) throws JsonIOException, IOException {
		
		String filepath = System.getProperty("user.dir") + "\\src\\Files\\test.json";
		
        IOController ioController = new IOController();
        
        //Creates a JSON file at the specified filepath and writes a Test object in it
        Test t1 = new Test(1);
        Test t2 = new Test(2);
        Test t3 = new Test(3);
        Test t4 = new Test(4);
        Test t5 = new Test(5);
        ioController.WriteObjectToJSONFile(t1, filepath);
        ioController.WriteObjectToJSONFile(t2, filepath);
        ioController.WriteObjectToJSONFile(t3, filepath);
        ioController.WriteObjectToJSONFile(t4, filepath);
        ioController.WriteObjectToJSONFile(t5, filepath);
        
        
        //Creates a Test object reading from the JSON file at the specified filepath
        Test t6 = ioController.ReadTestFromJSONFile(filepath);
        System.out.println("Questo e' il test" + t6.toString()); //Prints it
        
		//Deletes the JSON file 
        File file = new File(filepath);
        file.delete();
    }
}
