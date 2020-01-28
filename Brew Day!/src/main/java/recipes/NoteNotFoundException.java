package main.java.recipes;

@SuppressWarnings("serial")
public class NoteNotFoundException extends RuntimeException {

	public NoteNotFoundException() {
		super("Note not found");
	}

	public NoteNotFoundException(String message) {
		super(message);
	}
}
