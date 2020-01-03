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
		
		this.storeStorage();
	}

	public void updateIngredients(HashMap<String, Double> ingredients) {
		for(Entry<String, Double> i : ingredients.entrySet()) {
			getIngredients().put(i.getKey(), i.getValue());
		}

		this.updateStorage();
	}
	
	public void deleteIngredients(String[] ingredients) {
		for (int i = 0; i < ingredients.length; i++) {
			this.ingredients.remove(ingredients[i]);
		}
		
		this.deleteStorage();
	}

	public void storeStorage() {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");

			//Execute a query
			stmt = conn.createStatement();

			String sql = null;
			for(Entry<String, Double> i : this.getIngredients().entrySet()) {
				sql = "INSERT INTO Storage_has_Ingredient " +
						"VALUES (1" 
						+ ", " + i.getKey() 
						+  ", " + i.getValue()
						+")";
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
	
	public void updateStorage() {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");

			//Execute a query
			stmt = conn.createStatement();

			String sql = null;
			for(Entry<String, Double> i : this.getIngredients().entrySet()) {
				sql =  "UPDATE Storage_has_Ingredient "
						+ "SET quantity = " + i.getValue()
						+ " WHERE Ingredient_name = " + i.getKey();
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
	
	public void deleteStorage() {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");

			//Execute a query
			stmt = conn.createStatement();

			String sql = null;
			for(Entry<String, Double> i : this.getIngredients().entrySet()) {
				sql = "DELETE FROM Storage_has_Ingredient WHERE" 
						+ "Ingredient_Name = " + i.getKey();
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

