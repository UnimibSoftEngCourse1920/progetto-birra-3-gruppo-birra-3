package main.recipes;

@SuppressWarnings("serial")
public class StorageNotFoundWSIBTException extends Exception {

	public StorageNotFoundWSIBTException() {
		super("Storage not found for WSIBT feature");
	}

	public StorageNotFoundWSIBTException(String message) {
		super(message);
	}
}
