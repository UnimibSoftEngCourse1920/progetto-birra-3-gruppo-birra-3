package main.resources;
import java.util.HashMap;
import java.util.Map.Entry;
import main.Main;

import java.sql.*;

public class Storage {

	private HashMap<String,Double> ingredients;
	private static Storage instance;

	private Storage(HashMap<String, Double> ingredients) {
		super();
		this.ingredients = ingredients;
	}

	public HashMap<String, Double> getIngredients() {
		return this.ingredients;
	}


	public void setIngredients(HashMap<String, Double> ingredients) throws SQLException {
		this.ingredients = ingredients;

		//Main.connectDB(); probabilmente non necessario

		//Execute a query
		Statement stmt = Main.conn.createStatement();

		String sql = null;
		for(Entry<String, Double> i : this.getIngredients().entrySet()) {
			sql = "INSERT INTO Storage_has_Ingredient " +
					"VALUES (1" 
					+ ", " + i.getKey() 
					+  ", " + i.getValue()
					+")";
			stmt.executeUpdate(sql);
		}
	}

	public void updateIngredients(HashMap<String, Double> ingredients) throws SQLException {
		for(Entry<String, Double> i : ingredients.entrySet()) {
			getIngredients().put(i.getKey(), i.getValue());
		}

		//Main.connectDB();	probabilmente non necessario

		//Execute a query
		Statement stmt = Main.conn.createStatement();

		String sql = null;
		for(Entry<String, Double> i : this.getIngredients().entrySet()) {
			sql =  "UPDATE Storage_has_Ingredient "
					+ "SET quantity = " + i.getValue()
					+ " WHERE Ingredient_name = " + i.getKey();
			stmt.executeUpdate(sql);
		}
	}

	public void deleteIngredients(String[] ingredients) throws SQLException {
		for (int i = 0; i < ingredients.length; i++) {
			this.ingredients.remove(ingredients[i]);
		}

		//Main.connectDB();	probabilmente non necessario

		//Execute a query
		Statement stmt = Main.conn.createStatement();

		String sql = null;
		for(Entry<String, Double> i : this.getIngredients().entrySet()) {
			sql = "DELETE FROM Storage_has_Ingredient WHERE" 
					+ "Ingredient_Name = " + i.getKey();
			stmt.executeUpdate(sql);
		}
	}

	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage(new HashMap<String, Double>());
		}

		return instance;
	}
}

