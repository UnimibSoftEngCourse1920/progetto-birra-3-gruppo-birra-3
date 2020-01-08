package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import main.recipes.Recipe;

public class IOController {

	public IOController() {
		// TODO Auto-generated constructor stub
	}
	
	//Writes a generic object in the JSON file at the specified filepath
	public void WriteObjectToJSONFile(Object obj, String filepath) throws JsonIOException, IOException {
		Gson gson = new Gson();
		String strJson = gson.toJson(obj);
		System.out.println(strJson);
		
		
		File newFile = new File(filepath);
		FileWriter fileWriter = new FileWriter(filepath, true);
	    if (newFile.length() == 0) {
	    	fileWriter.write("[");
	    	fileWriter.write(strJson);
	    	//gson.toJson(strJson, fileWriter);
	    	System.out.println("Scritto if");
	    }
	    else {
	    	String file = readFile(filepath);
	    	FileWriter fileWriterDelete = new FileWriter(filepath);
	    	fileWriterDelete.write("");
	    	fileWriter.write(file, 0, file.length()-2);
	    	fileWriter.write("," + strJson);
	    	System.out.println("Scritto else");
	    }
	    	    
	    fileWriter.write("]");
	    fileWriter.flush();
    	fileWriter.close();
    }
	
	private static String readFile(String filePath) 
	{
	    StringBuilder contentBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
	    {
	 
	        String sCurrentLine;
	        while ((sCurrentLine = br.readLine()) != null) 
	        {
	            contentBuilder.append(sCurrentLine).append("\n");
	        }
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
	//Reads a recipe object from the JSON file at the specified filepath (doesn't work)
	public Recipe ReadRecipeFromJSONFile(String filepath) {
		BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\Files\\recipes.txt"));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    Gson gson = new GsonBuilder().create();
	    ArrayList<Recipe> recipesFromJson = gson.fromJson(reader, new TypeToken<ArrayList<Recipe>>() {
	    }.getType());
	    
	   String name = recipesFromJson.get(0).getName();
       int id = recipesFromJson.get(0).getId();
       Map<String,Double> ingredients = recipesFromJson.get(0).getIngredients();
       
       Recipe r = new Recipe(id,name,ingredients);
       return r;
	}
	
	//Created only for testing purpose (doesn't work)
	public Test ReadTestFromJSONFile(String filepath) throws JsonIOException, IOException {
		
		BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(filepath));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    Gson gson = new GsonBuilder().create();
	    
	    ArrayList<Test> testFromJson = gson.fromJson(reader, new TypeToken<ArrayList<Test>>() {}.getType());
	    
	    Test t = new Test(testFromJson.get(3).getId());
	    return t;
	}
}
