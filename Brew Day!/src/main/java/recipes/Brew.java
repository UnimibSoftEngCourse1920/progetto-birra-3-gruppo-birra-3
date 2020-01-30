package main.java.recipes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Brew implements Serializable{

	private Double id;
	private Date startDate;
	private Date finishDate = null;
	private Map<Integer,String> notes = new HashMap<>();
	private Recipe recipe;
	private int countNote = 1;
	private double batchSize;
	private static final long serialVersionUID = 2L;
	
	public Brew(Recipe recipe, Date startDate, double batchSize) {
		super();
		
		//sets the brew id in pointed notation based on the relative recipe's id 
		this.id = (double) recipe.getId() + recipe.getCountBrew() / 10;
		this.startDate = startDate;
		this.recipe = recipe;
		this.batchSize = batchSize;
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
		return this.notes;
	}
	
	public Recipe getRecipe() {
		return this.recipe;
	}
	
	public double getBatchSize() {
		return batchSize;
	}

	public String getNoteType(int id) {
		if (id > 0) return "Standard";
		return "Tasting";
	}

	public void setFinishDate(Date finishDate) {
		if (this.finishDate == null) this.finishDate = finishDate;
	}

	/*
	 * Adds a new note with the given text and of the type depending 
	 * on the boolean value to the brew notes. If tasting is true the note is 
	 * a tasting note, otherwise it's a standard note
	 */
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

	/*
	 * Deletes the note with the given id, if it exists (otherwise 
	 * an exception is thrown)
	 */
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

	/*
	 * Modifies the text of the note with the given id changing it to
	 * the given text, if the note exists (otherwise an exception is thrown)
	 */
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
