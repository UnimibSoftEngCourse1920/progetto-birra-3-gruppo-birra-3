package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import main.gui.MainWindow;
import main.recipes.Recipe;


public class Main {

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
