package main.java.gui.instruments;

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
import main.java.instruments.EquipmentController;


@SuppressWarnings("serial")
public class ModifyEquipmentWindow extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;

	public ModifyEquipmentWindow() {
		super("Brew Day! - Modify equipment");
		
		Color color = new Color(252, 255, 166);
		
		contentPane = WindowEditor.showWindow(this,color);
		
		Map<String, Double> instruments = WindowEditor.initializeSMEquipmentWindow(contentPane, color, "Modify the capacity of instruments:");
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name", "Capacity (l)"}, 0) {
				@Override
				   public boolean isCellEditable(int row, int column) {
				       return column == 1;
				   }
				};
		for(Entry<String, Double> i : instruments.entrySet()) {
			model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}
		
		table = WindowEditor.createTable(model, this, color, 30);
		
		WindowEditor.createBackAndOther(null, contentPane, this, color,"Save");
	}
	
	private Map<String, Double> updateInstruments(){
		try {
			Map<String, Double> instruments = new HashMap<>();
			for (int i = 0; i < table.getRowCount(); i++) {
				String instrumentCapacity = table.getValueAt(i, 1).toString();
				if (!instrumentCapacity.matches("[0-9]+.{0,1}[0-9]*")) {
					throw new IllegalArgumentException();
				}
				instruments.put(table.getValueAt(i, 0).toString(), Double.parseDouble(instrumentCapacity));
			}
			return instruments;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"Insert only positive numbers, separated by dot (e.g. Kettle 10.50)");
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			WindowEditor.back(this,new EquipmentWindow());
		}
		else {
			if (table.isEditing())
			    table.getCellEditor().stopCellEditing();
			
			if(updateInstruments() != null) {
				EquipmentController equipmentController = EquipmentController.getInstance();
				equipmentController.update(updateInstruments());
				
				new ShowEquipmentWindow().setVisible(true);
				dispose();
			}
		}
	}

}
