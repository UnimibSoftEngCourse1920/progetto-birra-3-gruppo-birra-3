package main.recipes;

import main.IOController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	protected ArrayList<Brew> extractBrew() {
	    if (ioController.readObjectFromFile(filepath) != null) {
	      return (ArrayList<Brew>) ioController.readObjectFromFile(filepath);
	    }

	    return new ArrayList<>();
	  }

	  protected void store(Brew brew) {
	    List<Brew> brews = extractBrew();
	    if (!brews.contains(brew)) {
	      brews.add(brew);
	      ioController.writeObjectToFile(brews, filepath);
	    }
	  }

	protected void updateNote(Double id, int noteId, String noteText) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(id) == 0) {
					brew.modifyNote(noteId, noteText);
					found = true;
				}
			}
			if(!found) {
				throw new brewNotFoundException("Brew not found");
			}
		} catch(noteNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		ioController.writeObjectToFile(brews, filepath);
	}
	
	protected void deleteNote(Double id, int noteId) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (int i = 0; i < brews.size(); i++) {
				if (brews.get(i).getId().compareTo(id) == 0) {
					brews.get(i).deleteNote(noteId);
					found = true;
				}
			}
			if(!found) {
				throw new brewNotFoundException("Brew not found");
			}
		} catch(noteNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		ioController.writeObjectToFile(brews, filepath);
	}

	
	protected void delete(Double id) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (int i = 0; i < brews.size(); i++) {
				if (brews.get(i).getId().compareTo(id) == 0) {
					brews.remove(i);
					found = true;
				}
			}
			if(!found) {
				throw new brewNotFoundException("Brew not found");
			}
		} catch(noteNotFoundException e) {
			System.out.println(e.getMessage());
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
