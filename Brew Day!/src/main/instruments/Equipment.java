package main.instruments;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java.io.Serializable;

public class Equipment implements Serializable{

	private Map<String, Double> instruments;
	private double capacity;
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
	
	public double getCapacity() {
		return capacity;
	}
	

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public void setInstruments(Map<String, Double> instruments) {
		this.instruments = instruments;
	}

	public double computeCapacity(Map<String, Double> instruments) {
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
			this.getInstruments().remove(name);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instruments == null) ? 0 : instruments.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipment other = (Equipment) obj;
		if (instruments == null) {
			if (other.instruments != null) {
				return false;
			}
		} 
		else if (!instruments.equals(other.instruments)) {
			return false;
		}
		return true;
	}	
}