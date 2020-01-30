package main.java.recipes;

import main.java.IngredientNotFoundException;
import main.java.instruments.Equipment;
import main.java.resources.Storage;
import main.java.resources.StorageController;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Recipe implements Serializable{


	private static int startingId;
	private String name;
	private Map<String,Double> ingredients;
	private Map<String, Double> percentageIngredients;
	private Map<String,Double> missingIngredients;
	private double countBrew = 1.0;
	private int id;
	private static final long serialVersionUID = 1L;

	public Recipe(String name, Map<String, Double> ingredients) {
		super();
		
		RecipeController recipeController = RecipeController.getInstance();
		
        /*
         * sets the recipe id incrementing the static id and updates the 
		 * CounterId.txt file to make the static id persistent
		 */
        startingId++;
        this.id = startingId;
        recipeController.updateCounterId(startingId);
        
		this.name = name;
		this.ingredients = ingredients;
		
		this.percentageIngredients = computePercentage(ingredients);
	}

	public int getId() {
		return id;
	}	

	public String getName() {
		return name;
	}

	public Map<String, Double> getMissingIngredients() {
		return missingIngredients;
	}

	/*
	 * Returns the quantity of the ingredient with the given name, if the
	 * ingredient exists (otherwise an exception is thrown)
	 */
	public double getQuantity(String name) {
		Double result = null;
		try {
			result = ingredients.get(name);
			if(result == null) {
				throw new IngredientNotFoundException();
			}
		} catch(IngredientNotFoundException e){
			System.out.println(e.getMessage());
		}
		return result;
	}

	public Map<String,Double> getIngredients() {
		return ingredients;
	}

	public double getCountBrew() {
		return countBrew;
	}

	public static int getStartingId() {
		return startingId;
	}

	public Map<String, Double> getPercentageIngredients() {
		return percentageIngredients;
	}

	public static void setStartingId(int startingId) {
		Recipe.startingId = startingId;
	}

	public void setIngredients(Map<String, Double> ingredients) {
		this.ingredients = ingredients;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void updateRecipe(String name, Map<String, Double> ingredients) {
		this.setName(name);
	    this.setIngredients(ingredients);
	}

	/*
	 * Creates and returns a brew from this recipe and subtract the necessary 
	 * ingredients from the storage if there are enough in it
	 */
	public Brew createBrew(){
		missingIngredients = this.computeMissingIngredients();
		
		//if no ingredients are missing from the storage the brew is created and stored
		if(missingIngredients.isEmpty()) {
			Date currentDate = new Date(System.currentTimeMillis());
			Brew b = new Brew(this, currentDate, Equipment.getInstance().getCapacity());
			countBrew++;
			BrewController brewController = BrewController.getInstance();
			brewController.store(b);
			
			//subtract used ingredients from the storage
			Storage storage = Storage.getInstance();
			Map<String,Double> storageIngredients = storage.getIngredients();
			for (Entry<String,Double> i : this.ingredients.entrySet()) {
				double sIngredientValue = storageIngredients.get(i.getKey()).doubleValue();
				storageIngredients.replace(i.getKey(),sIngredientValue-i.getValue());
			}
			
			//updates the storage instance and file
			StorageController storageController = StorageController.getInstance();
			storageController.update(storageIngredients);
			return b;
		}
		//if some ingredients are missing the an alert is shown
		else {
			StringBuilder missingAlert = new StringBuilder();
			missingAlert.append("Some ingredients are missing from your storage! \nYou should buy:");
			for(Entry<String, Double> i : missingIngredients.entrySet()) {
				missingAlert.append("\n" + i.getKey() + " : " + missingIngredients.get(i.getKey()));
			}
			return null;
		}
	}

	/*
	 * Returns the Map of the ingredients that are missing from the storage 
	 * to start brewing this recipe if the storage exists in the Storage.txt file
	 * (otherwise an exception is thrown)
	 */
	public Map<String, Double> computeMissingIngredients(){
		Map<String,Double> result = new HashMap<>();		
		
		File f = new File(System.getProperty("user.dir") + "\\src\\Files\\Storage.txt");
		
		try {
			if(f.exists()) {
				Map<String,Double> storageIngredients = Storage.getInstance().getIngredients();
				for(Entry<String, Double> i : this.ingredients.entrySet()) {
					double ingredientValue = storageIngredients.get(i.getKey()).doubleValue();
					if (ingredientValue < i.getValue()) {
						result.put(i.getKey(), i.getValue() - ingredientValue);
					}
				}

				return result;
			}
			else throw new StorageNotFoundWSIBTException();
		}catch(StorageNotFoundWSIBTException e){
			System.out.println(e.getMessage());
		}
		return result;
	}

	//Created only for testing purpose
	public void incrementCountBrew() {
		countBrew++;
	}
	
	/*
	 * Returns the Map of the ingredients quantities expressed as
	 * a percentage of the total sum of their quantities
	 */
	public Map<String, Double> computePercentage(Map<String, Double> ingredients){
		if(ingredients != null) {
			Map<String, Double> percentageIng = new HashMap<>();
			Double totalGrams = 0.0;
			for (Entry<String,Double> i : this.ingredients.entrySet()) {
				totalGrams += i.getValue();
			}
			for (Entry<String,Double> i : this.ingredients.entrySet()) {
				if(totalGrams.compareTo(0.0) != 0)
					percentageIng.put(i.getKey(), i.getValue()/totalGrams);
			}
			return percentageIng;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
