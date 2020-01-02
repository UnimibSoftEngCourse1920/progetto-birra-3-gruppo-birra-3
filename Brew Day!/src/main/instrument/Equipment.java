package main.instrument;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.sql.*;


public class Equipment {
	
	private HashMap<String, Double> instruments;
	
	Scanner scan = new Scanner(System.in);
	
	public Equipment(HashMap<String, Double> instruments) {
		super();
		this.instruments = instruments;
		
		// store??
	}

	public HashMap<String, Double> getInstruments() {
		return instruments;
	}

	public void setInstruments(HashMap<String, Double> instruments) {
		this.instruments = instruments;
	}
	
	public double computeCapacity(HashMap<String, Double> instruments) {
		double total = 0;
		for (Double value : instruments.values()) {
		    total += value;
		}
		
		return total;
	}
	
	// let change the capacity value of every instrument
	public void updateInstruments() {
		
		for (Entry<String, Double> i : instruments.entrySet()) {
		    System.out.println("Do you want to change the value of " + i.getKey() + " ?");
		    
		    // scanning char Y,y: yes; N,n: no
		    char c = scan.next().charAt(0);
		    
		    if (c == 'Y' || c == 'y') { // positive answer
		    	System.out.println("Please insert the new value of " + i.getKey() + ":");
		    	double d = scan.nextDouble();
		    	instruments.put(i.getKey(), d);
		    	System.out.println("The new value of "  + i.getKey() + "is " + i.getValue());
		    }
		    
		    else if (c == 'N' || c == 'n')
		    	System.out.println("Beautiful");
		}
		
		store(instruments);
	}
	
	public void store(HashMap<String, Double> instruments) {
		Connection conn = null;
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			System.out.println("Connecting to DB...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "username", "password");
			System.out.println("Connected DB successfully...");
			
			//Execute a query
			System.out.println("Creating statement...");
		    stmt = conn.createStatement();
		    String sql = createStoreString(instruments);
		    stmt.executeUpdate(sql);

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	public String createStoreString(HashMap<String, Double> instruments) {
		//creating script SQL for store instruments in DB
		return null;
	}
	
}