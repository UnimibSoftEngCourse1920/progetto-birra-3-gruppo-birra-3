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

	public Double computeCapacity(Map<String, Double> instruments) {
		try {
			double total = 0;
			for (Double value : instruments.values()) {
				if(value < 0) throw new CapacityException("Negative Capacity");
				total += value;
			}
			return total;
		}catch(CapacityException e) {
			System.out.println(e.getMessage());
			return 0.00;
		}
	}


	public void updateInstruments(Map<String, Double> instruments) throws NullInstrumentsException{
		try {
			if(instruments == null) throw new NullInstrumentsException("Instruments null");
			for(Entry<String, Double> i : instruments.entrySet()) {
				if(i.getKey() == null || i.getValue() == null) throw new InstrumentException("Instrument null");
				getInstruments().put(i.getKey(), i.getValue());
			}
			setCapacity(computeCapacity(getInstruments()));
		}catch(InstrumentException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public void deleteInstrument(String name) {
		try {
			if(name == null) throw new InstrumentException("Instrument name null");
			getInstruments().remove(name);
			setCapacity(computeCapacity(instruments));
		}catch(InstrumentException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public static Equipment getInstance() {
		if (instance == null) {
			instance = new Equipment(new HashMap<String, Double>()); 
		}

		return instance;
	}
}