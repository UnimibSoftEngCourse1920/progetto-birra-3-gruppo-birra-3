package main.instrument;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import main.DatabaseController;

public class Equipment {
	
	private double capacity;
	private HashMap<String, Double> instruments;
	private DatabaseController controller;
	
	Scanner scan = new Scanner(System.in);
	
	public Equipment(HashMap<String, Double> instruments, DatabaseController controller) {
		super();
		this.instruments = instruments;
		this.controller = controller;
	}


	public double getCapacity() {
		return capacity;
	}

	public HashMap<String, Double> getInstruments() {
		return instruments;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
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
	public void updateInstruments(HashMap<String, Double> instruments) {
		
		for (Entry<String, Double> i : instruments.entrySet()) {
		    System.out.println("Do you want to change the value of " + i.getKey() + " ?");
		    
		    // scanning char Y: yes N: no
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
	}
	
	public void store() {
		this.controller.store();
	}
	
}
