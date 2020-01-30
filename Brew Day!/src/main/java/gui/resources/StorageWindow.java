package main.java.gui.resources;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import main.java.gui.MainWindow;
import main.java.gui.WindowEditor;
import main.java.resources.*;

@SuppressWarnings("serial")
public class StorageWindow extends JFrame implements ActionListener {

	private StorageController sController;
	private JPanel contentPane;

	public StorageWindow() {
		super("Brew Day! - Storage");
		
		Color color = new Color(255, 154, 162);
		
		contentPane = WindowEditor.showWindow(this, color);
		
		WindowEditor.initializeWindow(contentPane, color, "The storage is:");
		
		sController = StorageController.getInstance();
			
		Storage storage;
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Name","Quantity (g)"}, 0) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};

		storage = sController.extractStorage();
		for(Entry<String, Double> i : storage.getIngredients().entrySet()) {
				model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}
		
		WindowEditor.createTable(model, this, color, 30);

		WindowEditor.createBackAndOther(null, contentPane, this, color,"Modify Ingredients");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Back")) {
			MainWindow mainWindow = new MainWindow();
			WindowEditor.back(this,mainWindow);
		} else {
			ModifyStorageIngredientsWindow msiWindow = new ModifyStorageIngredientsWindow();
			msiWindow.setVisible(true);
			dispose();
		}
	}
}