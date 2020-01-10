package main.resources;

public class TestStorage {

	public static void main(String[] args) {
		
		StorageController storageController = StorageController.getInstance();
		
		//test create equipment and store equipment
		System.out.println("Creating Storage...");
		storageController.createStorage();
        
        //test read
        System.out.println("Storage is composed by: " + storageController.extractStorage().toString());
        
        //test update
        storageController.update();
        System.out.println("Storage has been modified, now is composed by this " + storageController.extractStorage().toString());
        
        //test delete
        storageController.delete();
        System.out.println("Ingredient specified deleted, now Storage is composed by " + storageController.extractStorage().toString());
        
        //test insert
        storageController.insertIngredients();
        System.out.println("Storage has been modified, now is composed by this " + storageController.extractStorage().toString());
        
        storageController.deleteFile();

	}

}
