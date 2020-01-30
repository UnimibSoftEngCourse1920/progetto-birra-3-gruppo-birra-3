package main.java.gui.resources;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.gui.WindowEditor;
import main.java.resources.Storage;
import main.java.resources.StorageController;

@SuppressWarnings("serial")
public class ModifyStorageIngredientsWindow extends JFrame implements ActionListener{

	private StorageController sController;

	private JPanel contentPane;
	private JTable table;

	public ModifyStorageIngredientsWindow() {
		super("Brew Day! - Storage");
		
		Color color = new Color(255, 154, 162);
		
		contentPane = WindowEditor.showWindow(this, color);
		
		WindowEditor.initializeWindow(contentPane, color, "The storage is:");

		sController = StorageController.getInstance();

		Storage storage = Storage.getInstance();

		DefaultTableModel model = new DefaultTableModel(new String[]{"Name","Quantity (g)"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};

		for(Entry<String, Double> i : storage.getIngredients().entrySet()) {
			model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}

		table = WindowEditor.createTable(model, this, color, 30);

		WindowEditor.createBackAndOther(null, contentPane, this, color,"Save");
	}

	/*
	 * Returns the ingredients map using the data inserted in the table.
	 * Shows an alert message if the input format is not correct
	 */
	private Map<String,Double> extractIngredients() {
		try {
			Map<String,Double> newIngredients = new HashMap<>();
			for (int i = 0; i < table.getRowCount(); i++) {
				newIngredients.put(table.getValueAt(i, 0).toString(), WindowEditor.fromStringToDouble(table.getValueAt(i, 1).toString()));
			}
			return newIngredients;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,"Insert only positive numbers in quantity field, separated by dot (e.g. Sugar 10.50)");
			return null;
		} 
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			if (table.isEditing())
			    table.getCellEditor().stopCellEditing();
			
			if (extractIngredients() != null) {
				sController.update(extractIngredients());

				StorageWindow sWindow = new StorageWindow();
				sWindow.setVisible(true);
				dispose();
			}
		} else {
			StorageWindow sWindow = new StorageWindow();
			WindowEditor.back(this,sWindow);
		}
	}

}
