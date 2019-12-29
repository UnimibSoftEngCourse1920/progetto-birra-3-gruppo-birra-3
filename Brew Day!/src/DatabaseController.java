import java.util.HashMap;

public class DatabaseController {
	
	private static DatabaseController instance;
	private DBStrategy dbStrategy;
	
	public DatabaseController() {
		super();
	}
	
	public void setStrategy(DBStrategy dbStrategy) {
		this.dbStrategy = dbStrategy;
	}
	
	public void execute() {
		
	}
	
	public final DatabaseController getInstance() {
		if (instance == null) {
			instance = new DatabaseController();
		}
		
		return instance;
	}
	
	public HashMap<String,Double> update() {
		
	} 
	
	public Recipe autoSelectRecipe(HashMap<String,Double> ingredients, Equipment equipment) {
		
	}
	
	public Recipe selectRecipe(HashMap<String,Double> ingredients, Equipment equipment) {
		
	}
	
	
	

}
