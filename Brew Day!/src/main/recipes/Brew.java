package main.recipes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Brew implements Serializable{

	private Double id;
	private Date startDate;
	private Date finishDate;
	private Map<Integer,String> notes = new HashMap<>();
	private int countNote = 1;
	private static final long serialVersionUID = 2L;
	
	public Brew(Recipe recipe, Date startDate) {
		super();
		this.id = (double) recipe.getId() + recipe.getCountBrew() / 10;
		this.startDate = startDate;
	}

	public Double getId() {
		return id;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}
	
	public Map<Integer,String> getNotes() {
		return notes;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void addNote(String text, boolean tasting) {
		if(tasting) {
			notes.put((-1) * countNote, text);
			countNote++;
		}
		else {
			notes.put(countNote, text);
			countNote++;
		}
	}

	public void deleteNote(int id) {
		try {
			if(notes.get(id) == null) {
				throw new NoteNotFoundException();
			}
			notes.remove(id);
		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void modifyNote(int id, String text) {
		try {
			if(notes.get(id) == null) {
				throw new NoteNotFoundException();
			}
			notes.put(id, text);
		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	@Override
	public String toString() {
		return "id = " + id + ", startDate = " + startDate + ", finishDate = " + finishDate + ", notes = " + notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finishDate == null) ? 0 : finishDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		Brew other = (Brew) obj;
		if (finishDate == null) {
			if (other.finishDate != null)
				return false;
		} 
		else if (!finishDate.equals(other.finishDate))
				return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
				return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} 
		else if (!notes.equals(other.notes))
				return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} 
		else if (!startDate.equals(other.startDate))
				return false;
		return true;
	}

}
