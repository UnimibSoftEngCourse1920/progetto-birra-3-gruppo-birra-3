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

	public double computeCapacity() {
		try {
			double total = 0;
			for (Double value : this.instruments.values()) {
				if(value < 0) throw new CapacityException("Negative Capacity");
				total += value;
			}
			return total;
		}catch(CapacityException e) {
			System.out.println(e.getMessage());
			return 0.00;
		}
	}


	public void updateInstruments(Map<String, Double> instruments) {
		for(Entry<String, Double> i : instruments.entrySet()) {
			
			try {
				if(i.getKey() == null || i.getValue() == null) throw new InstrumentException("Instrument null");
				getInstruments().put(i.getKey(), i.getValue());
			}catch(InstrumentException e) {
				System.out.println(e.getMessage());
			}
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

	//starting here, for only testing purpose
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
			if (other.instruments != null)
				return false;
		} else if (!instruments.equals(other.instruments))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "instruments = " + instruments;
	}	
}