package main.recipes;

import main.instrument.Equipment;
import main.resources.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Recipe {
	
	private int id;
	private String name;
	Map<String,Double> ingredients = new HashMap<>();
	Map<String,Double> missingIngredients = new HashMap<>();
	private Equipment equipment;
	private Storage storage;
	
	public Recipe(int id, String name, Map<String, Double> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
	}

	public int getId() {
		return id;
	}	

	public String getName() {
		return name;
	}

	public double getQuantity(String name) {
		return ingredients.get(name);
	}

	public void setIngredient(String name, double quantity) {
		if(ingredients.get(name) == null) {
			ingredients.put(name, quantity);
		}
		else {
			ingredients.replace(name, quantity);
		}
	}

	public Equipment getEquipment() {
		return equipment;
	}
	
	public Map<String, Double> computeMissingIngredients(Map<String, Double> availableIngredients){
		Map<String,Double> results = new HashMap<>();
		Double available;
		Double needed;
		
		for(Entry<String, Double> i : this.ingredients.entrySet()) {
			available = availableIngredients.get(i.getKey());
			if(available != null) {
				needed = this.ingredients.get(i.getKey());
				if(available - needed > 0) {
					results.put(i.getKey(), available - needed);
				}
			}
		}
		
		return results;
	}
	
	public Map<String, Double> createBrew(){
		//must be completed
	}
	
	public void storeRecipe() {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");

			//Execute a query
			stmt = conn.createStatement();

			String sql = "INSERT INTO Recipe" +
					"SET id = " + this.getId() + ", "
							+ "name = " + this.getName() 
							+ ", Equipment_idEquipment = 1"
							+ "Storage_idStorage = 1";
			
			stmt.executeUpdate(sql);
			
			for(Entry<String, Double> i : this.ingredients.entrySet()) {
				//Must be completed
				stmt.executeUpdate(sql);
			}
			
			for(Entry<String, Double> i : this.missingIngredients.entrySet()) {
				//Must be completed
				stmt.executeUpdate(sql);
			}

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	public void modifyRecipe() {
		//Must be completed
	}

	public void deleteRecipe() {
		//Must be completed
	}
}
