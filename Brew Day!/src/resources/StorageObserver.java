package resources;

public class StorageObserver {

	private StorageListeners[] listeners;
	
	public StorageObserver(StorageListeners[] listeners) {
		super();
		this.listeners = listeners;
	}

	public void notify(Storage storage) {
		if (this.listeners.length != 0) {
			for (int i = 0; i < this.listeners.length; i++) {
				listeners[i].update();
			}
		}
	} 
}
