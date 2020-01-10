package main.recipes;

import main.IOController;

import java.io.File;
import java.util.ArrayList;

public class BrewController {

	private String filepath;
	private IOController ioController;
	private static BrewController instance;
	
	private BrewController() {
		super();
		this.filepath = System.getProperty("user.dir") + "\\src\\Files\\Brew.txt";
		this.ioController = new IOController();
	}
	
	public static BrewController getInstance() {
		if (instance == null) {
			instance = new BrewController();
		}
		
		return instance;
	}
	
	protected ArrayList<Brew> extractBrew() {
	    if (ioController.readObjectFromFile(filepath) != null) {
	      return (ArrayList<Brew>) ioController.readObjectFromFile(filepath);
	    }

	    return new ArrayList<>();
	  }

	  protected void store(Brew brew) {
	    ArrayList<Brew> brews = extractBrew();
	    if (!brews.contains(brew)) {
	      brews.add(brew);
	      ioController.writeObjectToFile(brews, filepath);
	    }
	  }

	protected void update(Double id, int noteId, String noteText) {
		ArrayList<Brew> brews = extractBrew();
		for (Brew brew : brews) {
			if (brew.getId().compareTo(id) == 0) {
				brew.modifyNote(noteId, noteText);
			}
		}
		
		ioController.writeObjectToFile(brews, filepath);
	}
	
	protected void delete(Double id) {
		ArrayList<Brew> brews = extractBrew();
		for (int i = 0; i < brews.size(); i++) {
			if (brews.get(i).getId() == id) {
				brews.remove(i);
			}
		}
		
		ioController.writeObjectToFile(brews, filepath);
	}
	
	//for only testing purpose
		public void deleteFile() {
			File file = new File(filepath);
			
			if (file.delete()) {
				System.out.println("\nFile deleted");
			} else {
				System.out.println("\nImpossible delete file");
			}
		}
}
