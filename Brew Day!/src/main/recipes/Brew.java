package main.recipes;

import main.resources.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Brew implements Serializable{

	private static final long serialVersionUID = 2L;
	private Double id;
	private Date startDate;
	private Date finishDate;
	private Map<Integer,String> notes = new HashMap<>();
	private Recipe recipe;
	private Storage storage;
	
	public Brew(Recipe recipe, Date startDate) {
		super();
		this.recipe = recipe;
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
	
	//Created only for testing purpose
	@Override
	public String toString() {
		String notesString = "";
		for(Entry<Integer,String> i : notes.entrySet()) {
			notesString += i.getKey() + "   " + notes.get(i.getKey()) + ", ";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return "Brew [id=" + id + ", startDate=" + formatter.format(startDate) + ", finishDate=" + finishDate + ", notes=" + notesString + "]";
	}
}
