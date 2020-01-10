package main.instrument;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.io.Serializable;



public class Equipment implements Serializable{
	
	private Map<String, Double> instruments;
	private static Equipment instance;
	private static final long serialVersionUID = 3L;
	
	
	private Equipment(Map<String, Double> instruments) {
		super();
		this.instruments = instruments;
	}

	public Map<String, Double> getInstruments() {
		return this.instruments;
	}

	public void setInstruments(Map<String, Double> instruments) {
		this.instruments = instruments;
	}
	
	public double computeCapacity(Map<String, Double> instruments) {
		double total = 0;
		for (Double value : instruments.values()) {
		    total += value;
		}
		
		return total;
	}
	
	
	public void updateInstruments(Map<String, Double> instruments) {
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