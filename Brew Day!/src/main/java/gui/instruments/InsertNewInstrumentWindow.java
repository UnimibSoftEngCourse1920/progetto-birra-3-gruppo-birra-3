package main.java.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.gui.WindowEditor;
import main.java.instruments.EquipmentController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class InsertNewInstrumentWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField1;

	public InsertNewInstrumentWindow() {
		super("Brew Day! - Insert new instrument");
		
		Color color = new Color(252, 255, 166);
		
		contentPane = WindowEditor.showWindow(this,color);
		
		JPanel panel = new JPanel();
		panel.setBackground(color);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertNewInstrument = new JLabel("Insert new instrument in your equipment:");
		lblInsertNewInstrument.setFont(WindowEditor.boldFont);
		panel.add(lblInsertNewInstrument);
		
     	JPanel panel1 = WindowEditor.createGridBagPanel(contentPane, color, new int[]{121}, new int[]{0, 0, 0, 0, 0, 0, 0},
				new double[]{0.0}, new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
	
		WindowEditor.createGridBagLabel("Instrument name:", panel1, 0);
		textField = WindowEditor.createGridBagTextField(panel1, 1,true);
		WindowEditor.createGridBagLabel("Capacity (l):", panel1, 2);
		textField1 = WindowEditor.createGridBagTextField(panel1, 3,true);
		
		WindowEditor.createBackAndOther(null, contentPane, this, color,"Save");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			WindowEditor.back(this,new EquipmentWindow());
		}
		else {
			//retrieve the information from the textAreas and if the input is legal update the equipment
			try {
				EquipmentController equipmentController = EquipmentController.getInstance();
				Map<String, Double> instruments = equipmentController.extractEquipment().getInstruments();
				
				boolean b = true;
				String name = textField.getText();
				String capacity = textField1.getText();
				
				if(!name.matches("[a-zA-Z_]+[0-9]*") || name.length() >= 13) {
					if(name.length() == 0) throw new IllegalArgumentException("Please insert a name to identify the instrument");
					else throw new IllegalArgumentException("The instrument name is too long or is not alphanumeric");
				}
				else if(!capacity.matches("[0-9]+.{0,1}[0-9]*")) {
					throw new NumberFormatException("Insert only positive numbers in capacity field, separated by dot (e.g. Kettle 10.50)");
				}
				for(Entry<String, Double> i : instruments.entrySet()) {
					if(name.equals(i.getKey())) {
						JOptionPane.showMessageDialog(this, "You already have this instrument in your equipment");
						b = false;
					}
				}
				if(b) {
					instruments.put(name, Double.parseDouble(capacity));
					equipmentController.update(instruments);
					
					new ShowEquipmentWindow().setVisible(true);
					dispose();
				}
			}catch (IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		}
	}

}
