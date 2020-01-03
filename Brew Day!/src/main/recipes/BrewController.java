package main.recipes;

public class BrewController {
	
	private Brew brew; 

	public void create(int id, Recipe recipe) {
		//Must be completed
		Brew o = new Brew(id,recipe);
		this.brew = o;
		store();
	}

	public void store() {
		//Must be completed
	}
	
	public void delete() {
		//Must be completed
	}

}
