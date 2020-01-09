package main.instrument;

import java.util.HashMap;
import java.util.Map.Entry;
import java.io.Serializable;



public class Equipment implements Serializable{
	
	private HashMap<String, Double> instruments;
	private static Equipment instance;
	private static final long serialVersionUID = 3L;
	
	
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
	
	
	public void updateInstruments(HashMap<String, Double> instruments) {
		for(Entry<String, Double> i : instruments.entrySet()) {
			getInstruments().put(i.getKey(), i.getValue());
		}
	}
	
	public void deleteInstrument(String name) {
		this.getInstruments().remove(name);
	}
	
	public static Equipment getInstance() {
		if (instance == null) {
			instance = new Equipment(new HashMap<String, Double>()); 
		}

		return instance;
	}
	
	//for only testing purpose
		@Override
		public String toString() {
			return "instruments = " + instruments;
		}	
}