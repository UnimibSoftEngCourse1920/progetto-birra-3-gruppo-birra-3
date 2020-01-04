package main.recipes;

import main.resources.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Brew implements Serializable{

	private static final long serialVersionUID = 2L;
	private Double id;
	private Date startDate;
	private Date finishDate;
	private Map<Integer,String> notes = new HashMap<>();
	private Recipe recipe;
	private Storage storage;

	public Brew(Double id, Recipe recipe, Date startDate) {
		super();
		this.recipe = recipe;
		//this.id = (Double) recipe.getId() + 0.01; must be review
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

	public void storeBrew() {
		
	}
	
	public void updateBrew() {
		
	}
	
	public void deleteBrew() {
	
	}
}
