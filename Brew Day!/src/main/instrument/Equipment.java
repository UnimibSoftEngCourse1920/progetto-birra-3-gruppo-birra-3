package main.instrument;

import java.util.HashMap;
import java.util.Map.Entry;

import main.resources.Storage;

import java.util.Scanner;
import java.sql.*;


public class Equipment {
	
	private HashMap<String, Double> instruments;
	private static Equipment instance;
	
	Scanner scan = new Scanner(System.in);
	
	private Equipment(HashMap<String, Double> instruments) {
		super();
		setInstruments(instruments);
		storeEquipment();
	}

	public HashMap<String, Double> getInstruments() {
		return this.instruments;
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
		
		updateEquipment();
	}
	
	public void storeEquipment() {
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
		    
		    //creating script SQL for store instruments in DB (used only the 1st time)
		    //equipment table(idEquipment, capacity)
			String sqlEq = "INSERT INTO Equipment "
						+ "VALUES (1"
						+ ", " + computeCapacity(this.instruments)
						+ ")";
		    
			stmt.executeUpdate(sqlEq);

			//equipment_has_instrument table(Equipment_idEquipment, Instrument_name)
			String sqlEqIn = null;
			for(Entry<String, Double> i : this.getInstruments().entrySet()) {
				sqlEqIn = "INSERT INTO Equipment_has_Instrument "
							+ "VALUES (1" 
							+ ", " + i.getKey()
							+ ")";
				stmt.executeUpdate(sqlEqIn);
			}
			
			//instrument table(name, capacity)
			String sqlIn = null;
			for(Entry<String, Double> i : this.getInstruments().entrySet()) {
				sqlIn = "INSERT INTO Instrument "
						+ "VALUES (" + i.getKey()
						+ ", " + i.getValue()
						+ ")";
				stmt.executeUpdate(sqlIn);
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
	
	public void updateEquipment() {
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
		    
		    //create sql statement for updating modified instruments
		    //instruments table(name, capacity)
		    String sqlIn = null;
			for(Entry<String, Double> i : this.getInstruments().entrySet()) {
				sqlIn = "UPDATE Instrument"
						+ "SET capacity = " + i.getValue()
						+ "WHERE name = " + i.getKey();
				stmt.executeUpdate(sqlIn);
			}
			
			 //equipment table(idEquipment, capacity)
		    String sqlEq = "UPDATE Equipment"
						+ "SET capacity = " + computeCapacity(this.instruments)
						+ "WHERE idEquipment = 1";
				stmt.executeUpdate(sqlEq);

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
	
	public static Equipment getInstance() {
		if (instance == null) {
			instance = new Equipment(new HashMap<String, Double>()); 
		}

		return instance;
	}
}