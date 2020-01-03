package main.resources;
import java.util.HashMap;
import java.util.Map.Entry;
import main.recipes.*;

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


	public void setIngredients(HashMap<String, Double> ingredients) {
		this.ingredients = ingredients;
	}

	public void updateIngredients(HashMap<String, Double> ingredients) {
		for(Entry<String, Double> i : ingredients.entrySet()) {
			getIngredients().put(i.getKey(), i.getValue());
		}

		this.store();
	}

	public void store() {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");

			//Execute a query
			stmt = conn.createStatement();

			String sql = "INSERT INTO Storage_has_Ingredient " +
					"SET ";

			for(Entry<String, Double> i : this.getIngredients().entrySet()) {
				sql = sql + "Storage_idStorage = 1" 
						+ ", Ingredient_Name = " + i.getKey() 
						+  ", quantity = " + i.getValue();
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

	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage(new HashMap<String, Double>());
		}

		return instance;
	}
}

