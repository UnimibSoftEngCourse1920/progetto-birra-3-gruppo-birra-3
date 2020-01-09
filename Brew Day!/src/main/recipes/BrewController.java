package main.recipes;

import main.IOController;

import java.io.File;
import java.util.ArrayList;

public class BrewController {

	private static String filepath = System.getProperty("user.dir") + "\\src\\Files\\Brew.txt";
	private static IOController ioController = new IOController();
	
	protected static ArrayList<Brew> extractBrew() {
	    if (ioController.ReadObjectFromFile(filepath) != null) {
	      return (ArrayList<Brew>) ioController.ReadObjectFromFile(filepath);
	    }

	    return new ArrayList<Brew>();
	  }

	  protected static void store(Brew brew) {
	    ArrayList<Brew> brews = extractBrew();
	    if (!brews.contains(brew)) {
	      brews.add(brew);
	      ioController.WriteObjectToFile(brews, filepath);
	    }
	  }

	protected static void update(Double id, int noteId, String noteText) {
		ArrayList<Brew> brews = extractBrew();
		for (Brew brew : brews) {
			if (brew.getId().compareTo(id) == 0) {
				brew.modifyNote(noteId, noteText);
			}
		}
		
		ioController.WriteObjectToFile(brews, filepath);
	}
	
	protected static void delete(Double id) {
		ArrayList<Brew> brews = extractBrew();
		for (int i = 0; i < brews.size(); i++) {
			if (brews.get(i).getId() == id) {
				brews.remove(i);
			}
		}
		
		ioController.WriteObjectToFile(brews, filepath);
	}
	
	//for only testing purpose
		public static void deleteFile() {
			File file = new File(filepath);
			file.delete();
		}
}
