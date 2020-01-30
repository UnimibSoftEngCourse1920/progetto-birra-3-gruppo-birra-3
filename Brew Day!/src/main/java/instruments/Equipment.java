package main.java.instruments;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java.io.Serializable;

public class Equipment implements Serializable{

	private Map<String, Double> instruments;
	private Double capacity;
	private static Equipment instance;
	private static final long serialVersionUID = 3L;

	private Equipment(Map<String, Double> instruments) {
		super();
		this.instruments = instruments;
		setCapacity(computeCapacity(instruments));
	}

	public Map<String, Double> getInstruments() {
		return this.instruments;
	}
	
	public Double getCapacity() {
		return capacity;
	}
	

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public void setInstruments(Map<String, Double> instruments) {
		this.instruments = instruments;
	}

	/*
	 * Computes the total capacity of the equipment, given by the sum
	 * of all instrument's capacity, and returns it
	 */
	public Double computeCapacity(Map<String, Double> instruments) {
		double total = 0;
		for (Double value : instruments.values()) {
				total += value;
		}
	    return total;
	}

	/*
	 * Updates the instruments map with the given instruments 
	 * and computes the new capacity
	 */
	public void updateInstruments(Map<String, Double> instruments) throws NullInstrumentsException{
		try {
			if(instruments == null) throw new NullInstrumentsException("Instruments null");
			for(Entry<String, Double> i : instruments.entrySet()) {
				getInstruments().put(i.getKey(), i.getValue());
			}
			setCapacity(computeCapacity(getInstruments()));
		}catch(NullInstrumentsException e) {
			System.out.println(e.getMessage());
		}
		
	}

	/*
	 * Deletes the given instrument and computes the new capacity
	 */
	public void deleteInstrument(String name) {
			getInstruments().remove(name);
			setCapacity(computeCapacity(instruments));
	}

	public static Equipment getInstance() {
		if (instance == null) {
			instance = new Equipment(new HashMap<String, Double>()); 
		}

		return instance;
	}
}