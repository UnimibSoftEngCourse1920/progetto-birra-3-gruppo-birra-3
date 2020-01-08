package main.instrument;

import java.util.HashMap;
import java.util.Map.Entry;

import java.util.Scanner;
import java.io.Serializable;


public class Equipment implements Serializable {
	
	private static final long serialVersionUID = 3L;
	private HashMap<String, Double> instruments;
	private static Equipment instance;
	
	Scanner scan = new Scanner(System.in);
	
	private Equipment(HashMap<String, Double> instruments) {
		super();
		this.instruments = instruments;
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
	}
	
	public void deleteInstrument(String name) {
		this.instruments.remove(name);
	}
	
	public static Equipment getInstance() {
		if (instance == null) {
			instance = new Equipment(new HashMap<String, Double>()); 
		}

		return instance;
	}
}