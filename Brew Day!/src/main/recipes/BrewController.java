package main.recipes;

import main.IOController;
import main.resources.Storage;
import main.resources.StorageController;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class BrewController implements Serializable {

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
	public List<Brew> extractBrew() {
		if (ioController.readObjectFromFile(filepath) != null) {
			return (ArrayList<Brew>) ioController.readObjectFromFile(filepath);
		}

		return new ArrayList<>();
	}

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
			ioController.writeObjectToFile(brews, filepath);
		}
	}

	public void updateNote(Double id, int noteId, String noteText) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(id) == 0) {
					brew.modifyNote(noteId, noteText);
					found = true;
					ioController.writeObjectToFile(brews, filepath);
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

	public void deleteNote(Double id, int noteId) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (int i = 0; i < brews.size(); i++) {
				if (brews.get(i).getId().compareTo(id) == 0) {
					brews.get(i).deleteNote(noteId);
					found = true;
					ioController.writeObjectToFile(brews, filepath);
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

	public void addNote(Double id, String text, Boolean tasting) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(id) == 0) {
					brew.addNote(text, tasting);
					found = true;
					ioController.writeObjectToFile(brews, filepath);
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


	public void delete(Double id) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (int i = 0; i < brews.size(); i++) {
				if (brews.get(i).getId().compareTo(id) == 0) {
					brews.remove(i);
					i--;
					found = true;
					ioController.writeObjectToFile(brews, filepath);
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

	public void cancel(Double id) {
		List<Brew> brews = extractBrew();
		Brew brew = brews.get(0);
		for (int i = 0; i < brews.size(); i++) {
			if (brews.get(i).getId().compareTo(id) == 0) {
				brew = brews.get(i);
				if (brew.getFinishDate() != null) {
					return;
				}
				brews.remove(i);
				break;
			}
		}

		ioController.writeObjectToFile(brews, filepath);

		Map<String,Double> bIngredients = brew.getRecipe().getIngredients();
		Storage storage = Storage.getInstance();
		Map<String,Double> sIngredients = storage.getIngredients();

		for (Entry<String,Double> i : bIngredients.entrySet()) {
			double bIngredientValue = sIngredients.get(i.getKey()).doubleValue();
			sIngredients.put(i.getKey(), i.getValue() + bIngredientValue);
		}

		storage.updateIngredients(sIngredients);
		StorageController storageController = StorageController.getInstance();
		storageController.store(storage);
	}

	public void setFinishDate(Double id) {
		List<Brew> brews = extractBrew();
		boolean found = false;
		try {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(id) == 0) {
					Date currentDate = new Date(System.currentTimeMillis());
					brew.setFinishDate(currentDate);
					found = true;
					break;
				}
			}

			if(!found) {
				throw new BrewNotFoundException();
			}

		} catch(NoteNotFoundException e) {
			System.out.println(e.getMessage());
		}
		ioController.writeObjectToFile(brews, filepath);
	}

	//for only testing purpose
	public void deleteFile() {
		File file = new File(filepath);

		if (file.exists()) {
			if (file.delete()) {
				System.out.println("\nFile deleted");
			} else {
				System.out.println("\nImpossible delete file");
			}
		}
	}
}
