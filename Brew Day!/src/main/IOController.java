package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;	

public class IOController {
	
	FileOutputStream fileOut;
	ObjectOutputStream objectOut;
	FileInputStream fileIn;
	ObjectInputStream objectIn;
 
    public void writeObjectToFile(Object serObj, String filepath) {
        try {
            fileOut = new FileOutputStream(filepath);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	try {
				fileOut.close();
				objectOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public Object readObjectFromFile(String filepath) {
    	 
        try {
            fileIn = new FileInputStream(filepath);
            objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            return obj;
        } catch (Exception ex) {
        	ex.printStackTrace();
            return null;
        } finally {
        	try {
				fileIn.close();
				objectIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

        }
    }
    
}
