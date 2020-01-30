package main.java.gui.instruments;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import main.java.gui.WindowEditor;
import main.java.instruments.EquipmentController;

import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class InsertInstrumentsWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;

	public InsertInstrumentsWindow(int numberInstruments) {
		super("Brew Day! - Insert instruments");
		
		Color color = new Color(252, 255, 166);
		
		contentPane = WindowEditor.showWindow(this, color);
		
		WindowEditor.initializeWindow(contentPane, color, "Insert the name and the capacity of instruments:");
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name","Capacity (l)"}, 0);
		for(int i=0; i<numberInstruments; i++) {
			model.addRow(new String[] {"","0.0"});
		}
		
		table = WindowEditor.createTable(model, this, color, 30);
		
		WindowEditor.createBackAndOther(null, contentPane, this, color,"Save");
	}
	
	/*
	 * Returns the instruments map using the data inserted in the table. Shows 
	 * an alert message and returns null if the input format is not correct or if something
	 * hasn't been inserted
	 */
	private Map<String, Double> createInstruments(){
		try {
			Map<String, Double> instruments = new HashMap<>();
			for (int i = 0; i < table.getRowCount(); i++) {
				String instrumentName = table.getValueAt(i, 0).toString();
				String instrumentCapacity = table.getValueAt(i, 1).toString();
				if (!instrumentName.matches("[a-zA-Z_]+[0-9]*") || instrumentName.length() >= 13) {
					if(instrumentName.length() == 0) throw new IllegalArgumentException("Please insert a name to identify the instrument");
					else throw new IllegalArgumentException("The instrument name is too long or is not alphanumeric");
				}
				else if(!instrumentCapacity.matches("[0-9]+.{0,1}[0-9]*")) {
					throw new NumberFormatException("Insert only positive numbers in capacity field, separated by dot (e.g. Kettle 10.50)");
				}
				
				instruments.put(instrumentName, Double.parseDouble(instrumentCapacity));
			}
			return instruments;
		}catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage());
			return null;
		}catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			return null;
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			CreateEquipmentWindow crtEquipmentWin = new CreateEquipmentWindow();
			WindowEditor.back(this,crtEquipmentWin);
			dispose();
		}
		else {
			if (table.isEditing())
			    table.getCellEditor().stopCellEditing();
			
			if(createInstruments() != null) {
				EquipmentController equipmentController = EquipmentController.getInstance();
				equipmentController.createEquipment(createInstruments());
				
				new ShowEquipmentWindow().setVisible(true);
				dispose();
			}
		}
	}
}
