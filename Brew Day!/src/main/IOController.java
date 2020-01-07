package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		gson.toJson(obj, new FileWriter(filepath));
	}
	
	//Reads a recipe object from the JSON file at the specified filepath (doesn't work)
	public Recipe ReadRecipeFromJSONFile(String filepath) {
		BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\Files\\Json\\recipes.json"));
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
		Gson gson = new Gson();
		Test t = gson.fromJson(filepath, Test.class);
		return t;
	}
}
