package main.java.recipes;

import main.java.IOController;
import main.java.resources.Storage;
import main.java.resources.StorageController;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class BrewController implements Serializable {

	private Path filepath;
	private IOController ioController;
	private static BrewController instance;

	private BrewController() {
		super();
		this.filepath = Paths.get(System.getProperty("user.dir") + "\\src\\Files\\Brew.txt");
		this.ioController = new IOController();
	}

	public static BrewController getInstance() {
		if (instance == null) {
			instance = new BrewController();
		}

		return instance;
	}

	/*
	 * Returns the List of Brew objects stored in the Brew.txt file
	 */
	@SuppressWarnings("unchecked")
	public List<Brew> extractBrew() {
		if (ioController.readObjectFromFile(filepath.toString()) != null) {
			return (ArrayList<Brew>) ioController.readObjectFromFile(filepath.toString());
		}

		return new ArrayList<>();
	}

	/*
	 * Stores the given brew object in the Brew.txt file if it isn't stored already
	 */
	public void store(Brew brew) {
		List<Brew> brews = extractBrew();

		boolean found = false;

		for (Brew b : brews) {
			if (b.getId().compareTo(brew.getId()) == 0) {
				found = true;
				break;
			}
		}

		if (!found) {
			brews.add(brew);
			ioController.writeObjectToFile(brews, filepath.toString());
		}
	}

	/*
	 * Updates with the given noteText the text of the note with the given noteId 
	 * and associated to the brew with the given id, if the brew exists in 
	 * the Brew.txt file (otherwise an exception is thrown)
	 */
	public void updateNote(Double id, int noteId, String noteText) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(id) == 0) {
					brew.modifyNote(noteId, noteText);
					found = true;
					ioController.writeObjectToFile(brews, filepath.toString());
					break;
				}
			}
			if(!found) {
				throw new BrewNotFoundException();
			}
		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Deletes the note with the given noteId and associated to the brew 
	 * with the given id, if the brew exists in the Brew.txt file (otherwise an 
	 * exception is thrown)
	 */
	public void deleteNote(Double id, int noteId) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (int i = 0; i < brews.size(); i++) {
				if (brews.get(i).getId().compareTo(id) == 0) {
					brews.get(i).deleteNote(noteId);
					found = true;
					ioController.writeObjectToFile(brews, filepath.toString());
					break;
				}
			}

			if(!found) {
				throw new BrewNotFoundException();
			}

		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Adds to the brew with the given id a new note with the given text and of
	 * the type depending on the tasting value, if the brew exists in 
	 * the Brew.txt file (otherwise an exception is thrown)
	 */
	public void addNote(Double id, String text, Boolean tasting) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(id) == 0) {
					brew.addNote(text, tasting);
					found = true;
					ioController.writeObjectToFile(brews, filepath.toString());
					break;
				}
			}

			if(!found) {
				throw new BrewNotFoundException();
			}

		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}


	/*
	 * Deletes the brew with the given id, if the brew exists in the Brew.txt file
	 * (otherwise an exception is thrown)
	 */
	public void delete(Double id) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (int i = 0; i < brews.size(); i++) {
				if (brews.get(i).getId().compareTo(id) == 0) {
					brews.remove(i);
					
					found = true;
					ioController.writeObjectToFile(brews, filepath.toString());
					break;
				}
			}
			if(!found) {
				throw new BrewNotFoundException();
			}
		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Deletes the brew with the given id, if the brew exists in the Brew.txt file
	 * (otherwise an exception is thrown) and it isn't finished yet. If the 
	 * brew is deleted it adds the relative ingredients to the storage
	 */
	public void cancel(Double id) {
		List<Brew> brews = extractBrew();
		Brew brew = brews.get(0);
		boolean found = false;
		try {
			for (int i = 0; i < brews.size(); i++) {
				if (brews.get(i).getId().compareTo(id) == 0) {
					brew = brews.get(i);
					if (brew.getFinishDate() != null) {
						return;
					}
					brews.remove(i);
					
					found = true;
					ioController.writeObjectToFile(brews, filepath.toString());
					break;
				}
			}
			if(!found) {
				throw new BrewNotFoundException();
			}
		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}

		Map<String,Double> bIngredients = brew.getRecipe().getIngredients();
		Storage storage = Storage.getInstance();
		Map<String,Double> sIngredients = storage.getIngredients();

		for (Entry<String,Double> i : bIngredients.entrySet()) {
			double bIngredientValue = sIngredients.get(i.getKey()).doubleValue();
			sIngredients.put(i.getKey(), Math.round((i.getValue() + bIngredientValue)*100)/100.0);
		}

		storage.updateIngredients(sIngredients);
		StorageController storageController = StorageController.getInstance();
		storageController.store(storage);
	}

	/*
	 * Sets to the actual date the finish date of the brew with the given id, 
	 * if the brew exists in the Brew.txt file (otherwise an exception is thrown)
	 */
	public void setFinishDate(Double id) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(id) == 0) {
					Date currentDate = new Date(System.currentTimeMillis());
					brew.setFinishDate(currentDate);
					
					found = true;
					ioController.writeObjectToFile(brews, filepath.toString());
					break;
				}
			}

			if(!found) {
				throw new BrewNotFoundException();
			}

		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Deletes the Brew.txt file
	 */
	public void deleteFile() {
		File file = new File(filepath.toString());

		if (file.exists()) {
			try{
				Files.delete(filepath);
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
