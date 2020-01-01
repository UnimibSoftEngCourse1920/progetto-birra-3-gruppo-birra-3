package main.recipes;

public class RecipeController {
	
	private Recipe recipe;

	public RecipeController(Recipe recipe) {
		super();
		this.recipe = recipe;
	}

	public void save() {
		//Must be completed
	}
	
	public void modify() {
		//Must be completed
	}

	public void delete() {
		//Must be completed
	}
	
	public void createBrew(int id, Recipe recipe) {
		//Must be completed
		Bcontroller.create(id,recipe);
	}
	
	public void startBrew(Recipe recipe) {
		//Must be completed (Probably needs this class to be an extension of Thread)
		while(!recipe.missingIngredients.isEmpty()) {}
		Scontroller.updateIngredients(recipe.ingredients);
		createBrew(id,recipe);
	}
	
	public void notifyMissingIngredients() {
		//Must be completed
	}
}
