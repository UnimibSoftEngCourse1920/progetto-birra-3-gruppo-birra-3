package main.recipes;

import main.resources.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Brew {

	private Double id;
	private Date startDate;
	private Date finishDate;
	private Map<Integer,String> notes = new HashMap<>();
	private Recipe recipe;
	private Storage storage;

	public Brew(Double id, Recipe recipe, Date startDate) {
		super();
		this.recipe = recipe;
		//this.id = (Double) recipe.getId() + 0.01; must be review
		this.startDate = startDate;
	}

	public Double getId() {
		return id;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void addNote(int id, String text, boolean tasting) {
		if(tasting) {
			notes.put((-1) * id, text);
		}
		else {
			notes.put(id, text);
		}
	}

	public void deleteNote(int id) {
		notes.remove(id);
	}

	public void modifyNote(int id, String text) {
		notes.put(id, text);
	}

	public void storeBrew() {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");

			//Execute a query
			stmt = conn.createStatement();

			String sql = "INSERT INTO Brew " 
					+ "SET id = " + this.getId()
					+ ", name = " + recipe.getName() 
					+ ", startDate " + this.getStartDate() 
					+ ", finishDate = " + this.getFinishDate()
					+ ", Recipe_id = " + recipe.getId()
					+ "Storage_idStorage = 1";
			
			stmt.executeUpdate(sql);
			
			for(Entry<Integer, String> i : this.notes.entrySet()) {
				sql = "INSERT INTO Note "
						+ "SET id = " + i.getKey()
						+ ", text = " + i.getValue()
						+ ", Brew_id = " + this.getId();
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
	
	public void updateBrew() {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");

			//Execute a query
			stmt = conn.createStatement();

			String sql = "UPDATE Brew " 
					+ "SET name = " + recipe.getName() 
					+ ", startDate " + this.getStartDate() 
					+ ", finishDate = " + this.getFinishDate()
					+ ", Recipe_id = " + recipe.getId()
					+ " WHERE id = " + this.getId();
			
			stmt.executeUpdate(sql);
			
			for(Entry<Integer, String> i : this.notes.entrySet()) {
				sql = "UPDATE Note " 
						+ "SET text = " + i.getValue()
						+ " WHERE id = " + i.getKey();
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
	
	public void deleteBrew() {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");

			//Execute a query
			stmt = conn.createStatement();

			String sql = "DELETE FROM Brew " +
					"WHERE id = " + this.getId();
			
			stmt.executeUpdate(sql);
			
			for(Entry<Integer, String> i : this.notes.entrySet()) {
				sql = "DELETE FROM Note "
						+ "WHERE id = " + i.getKey();
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
}
