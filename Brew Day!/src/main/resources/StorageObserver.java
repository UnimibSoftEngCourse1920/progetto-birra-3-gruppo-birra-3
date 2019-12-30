package main.resources;

public class StorageObserver {

	private StorageListeners[] listeners;
	
	public StorageObserver(StorageListeners[] listeners) {
		super();
		this.listeners = listeners;
	}

	public void notify(Storage storage) {
		for (int i = 0; i < this.listeners.length; i++) {
			listeners[i].update();
		}
		
	} 
}
