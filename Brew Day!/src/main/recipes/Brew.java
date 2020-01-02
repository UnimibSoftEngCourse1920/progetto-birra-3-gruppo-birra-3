package main.recipes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Brew {
	
	private int id;
	private Date startDate;
	private Date finishDate;
	private Map<Integer,String> notes = new HashMap<>();
	private Recipe recipe;
	private Storage storage;
	private DatabaseController DBcontroller;
	private BrewController Bcontroller;
	
	public Brew(int id, Recipe recipe) {
		super();
		this.id = id;
		this.recipe = recipe;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
    
    public void modifyNote(int id, String text, boolean tasting) {
    	deleteNote(id);
    	addNote(id,text,tasting);
	}
}
