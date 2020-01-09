package main.instrument;

public class testEquipment {
	public static void main(String[] args) {
		
		//test create equipment and store equipment
		System.out.println("Creating Equipment...");
		EquipmentController.createEquipment();
		
		System.out.println("Done successfully.");
		
		//test read
        System.out.println("Equipment is composed by this " + EquipmentController.extractEquipment().toString());
		
        //test update
        EquipmentController.update();
        System.out.println("Equipment has been modified, now is composed by this " + EquipmentController.extractEquipment().toString());
		
        
        //test delete
        EquipmentController.delete();
        System.out.println("Instrument specified deleted, now Equipment is composed by " + EquipmentController.extractEquipment().toString());
	
        EquipmentController.deleteFile();
	
	}
}
