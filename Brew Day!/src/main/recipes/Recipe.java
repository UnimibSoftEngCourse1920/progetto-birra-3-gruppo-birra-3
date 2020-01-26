package main.recipes;

import main.IngredientNotFoundException;
import main.instruments.Equipment;
import main.resources.Storage;
import main.resources.StorageController;
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
	private Equipment equipment;
	private Storage storage;
	private Map<String,Double> missingIngredients;
	private double countBrew = 1.0;
	private int id;
	private static final long serialVersionUID = 1L;

	public Recipe(String name, Map<String, Double> ingredients) {
		super();
		
		RecipeController recipeController = RecipeController.getInstance();
		
        startingId++;
        this.id = startingId;
        recipeController.updateCounterId(startingId);
        
		this.name = name;
		this.ingredients = ingredients;
		this.equipment = Equipment.getInstance();
		this.storage = Storage.getInstance();
		
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

	public double getQuantity(String name) {
		Double result = null;
		try {
			result = ingredients.get(name);
			if(result == null) {
				throw new IngredientNotFoundException();
			}
		} catch(IngredientNotFoundException e){
			System.err.println(e.getMessage());
		}
		return result;
	}

	public Equipment getEquipment() {
		return equipment;
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


	public Brew createBrew(){
		missingIngredients = this.computeMissingIngredients();
		if(missingIngredients.isEmpty()) {
			Date currentDate = new Date(System.currentTimeMillis());
			Brew b = new Brew(this, currentDate);
			countBrew++;
			BrewController brewController = BrewController.getInstance();
			brewController.store(b);
			//subtract ingredients from storage
			storage = Storage.getInstance();
			Map<String,Double> storageIngredients = storage.getIngredients();
			for (Entry<String,Double> i : this.ingredients.entrySet()) {
				double sIngredientValue = storageIngredients.get(i.getKey()).doubleValue();
				storageIngredients.replace(i.getKey(),sIngredientValue-i.getValue());
			}
			
			StorageController storageController = StorageController.getInstance();
			storageController.update(storageIngredients);
			return b;
		}
		else {
			StringBuilder missingAlert = new StringBuilder();
			missingAlert.append("Some ingredients are missing from your storage! \nYou should buy:");
			for(Entry<String, Double> i : missingIngredients.entrySet()) {
				missingAlert.append("\n" + i.getKey() + " : " + missingIngredients.get(i.getKey()));
			}
			return null;
		}
	}

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
	
	public Map<String, Double> computePercentage(Map<String, Double> ingredients){
		if(ingredients != null) {
			Map<String, Double> percentageIngredients = new HashMap<String, Double>();
			double totalGrams = 0.0;
			for (Entry<String,Double> i : this.ingredients.entrySet()) {
				totalGrams = i.getValue();
			}
			for (Entry<String,Double> i : this.ingredients.entrySet()) {
				percentageIngredients.put(i.getKey(), i.getValue()/totalGrams);
			}
			return percentageIngredients;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(countBrew);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((equipment == null) ? 0 : equipment.hashCode());
		result = prime * result + id;
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((storage == null) ? 0 : storage.hashCode());
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
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} 
		else if (!ingredients.equals(other.ingredients))
				return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} 
		else if (!name.equals(other.name))
				return false;
		return true;
	}

	public Map<String, Double> getPercentageIngredients() {
		return percentageIngredients;
	}
}
