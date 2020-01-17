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
		return this.notes;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void addNote(int id, String text, boolean tasting) {
		if(tasting) {
			notes.put((-1) * id, text);
		}
		else {
			notes.put(id, text);
		}
	}

	public void deleteNote(int id) {
		notes.remove(id);
	}

	public void modifyNote(int id, String text) {
		notes.put(id, text);
	}	
	
	@Override
	public String toString() {
		return "id = " + id + ", startDate = " + startDate + ", finishDate = " + finishDate + ", notes = " + notes;
	}
}
