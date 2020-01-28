package main.java;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;	

@SuppressWarnings("serial")
public class IOController implements Serializable {
	
	Logger logger = Logger.getLogger(IOController.class.getName());
 
    public void writeObjectToFile(Object serObj, String filepath) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filepath))) {
            objectOut.writeObject(serObj);
        } catch (IOException e) {
        	logger.log(Level.FINE,e.getMessage());
        }
    }
    
    public Object readObjectFromFile(String filepath) {
    	 
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filepath))) {
            return objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
        	logger.log(Level.FINE,e.getMessage());
            return null;
        }        
    }
    
}