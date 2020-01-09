package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOController {

	public IOController() {

	}
	 
    public void WriteObjectToFile(Object serObj, String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Object ReadObjectFromFile(String filepath) {
    	 
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
