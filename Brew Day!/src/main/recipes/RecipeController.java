package main.recipes;

import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import main.IOController;

public class RecipeController {

	private  String filepath;
	private  IOController ioController; 
	private static RecipeController instance;
	
	private RecipeController() {
		super();
		this.filepath = System.getProperty("user.dir") + "\\src\\Files\\Recipe.txt"; 
		this.ioController = new IOController();
	}
	
	public static RecipeController getInstance() {
		if (instance == null) {
			instance = new RecipeController();
		}
		
		return instance;
	}

	@SuppressWarnings("unchecked")
	protected ArrayList<Recipe> extractRecipe() {
		if (ioController.readObjectFromFile(filepath) != null) {
			return (ArrayList<Recipe>) ioController.readObjectFromFile(filepath);
		}

		return new ArrayList<>();
	}

	protected void store(Recipe recipe) {
		ArrayList<Recipe> recipes = extractRecipe();
		if (!recipes.contains(recipe)) {
			recipes.add(recipe);
			ioController.writeObjectToFile(recipes, filepath);
		}
	}

	protected void update(int id, String name, Map<String,Double> ingredients) {
		ArrayList<Recipe> recipes = extractRecipe();
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).getId() == id) {
				recipes.get(i).updateRecipe(name, ingredients);
			}
		}

		ioController.writeObjectToFile(recipes, filepath);
	}

	protected void delete(int id) {
		ArrayList<Recipe> recipes = extractRecipe();
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).getId() == id) {
				recipes.remove(i);
			}
		}

		ioController.writeObjectToFile(recipes, filepath);
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
}
