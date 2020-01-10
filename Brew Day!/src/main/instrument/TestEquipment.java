package main.instrument;

public class TestEquipment {
	public static void main(String[] args) {
		
		EquipmentController equipmentController = EquipmentController.getInstance();
		
		//test create equipment and store equipment
		System.out.println("Creating Equipment...");
		equipmentController.createEquipment();
		
		System.out.println("Done successfully.");
		
		//test read
        System.out.println("Equipment is composed by this " + equipmentController.extractEquipment().toString());
		
        //test update
        equipmentController.update();
        System.out.println("Equipment has been modified, now is composed by this " + equipmentController.extractEquipment().toString());
		
        //test delete
        equipmentController.delete();
        System.out.println("Instrument specified deleted, now Equipment is composed by " + equipmentController.extractEquipment().toString());
        
        //test insert
        equipmentController.insertInstruments();
        System.out.println("Equipment has been modified, now is composed by this " + equipmentController.extractEquipment().toString());
        
        equipmentController.deleteFile();
	
	}
}
