package main.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import main.java.gui.MainWindow;
import main.java.recipes.Recipe;


public class Main {

	/*
	 * Sets the recipes' starting id to the number written in the 
	 * CounterId.txt file and creates the main window.
	 * If the CounterId.txt file doesn't exist it creates it and writes 0 in it
	 */
	public static void main(String[] args) {
		int startingId = 0;
		String counterIdFilepath = System.getProperty("user.dir") + "\\src\\Files\\CounterId.txt";
		
		File file = new File(counterIdFilepath);
		if (!file.exists()) {
			try (FileOutputStream fos = new FileOutputStream(counterIdFilepath);
				DataOutputStream dos = new DataOutputStream(fos)) {
				dos.writeInt(0); 
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	
		try (FileInputStream fin = new FileInputStream(counterIdFilepath);
			DataInputStream din = new DataInputStream(fin)) {
		
	        startingId = din.readInt();
	    }
	    catch(IOException e)
	    {
	    	System.out.println(e.getMessage());
	    }
		
		Recipe.setStartingId(startingId);
		
		MainWindow gui = new MainWindow();
		gui.setVisible(true);
	}
}
