package main.java.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.gui.WindowEditor;
import main.java.instruments.Equipment;
import main.java.instruments.EquipmentController;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class DeleteInstrumentWindow extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel panel1;

	public DeleteInstrumentWindow() {
		super("Brew Day! - Delete instrument");
		
		Color color = new Color(252, 255, 166);
		
		contentPane = WindowEditor.showWindow(this, color);
		
		JPanel panel = new JPanel();
		panel.setBackground(color);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblSelectTheInstrument = new JLabel("Select the instrument you want to delete from Equipment:");
		lblSelectTheInstrument.setFont(WindowEditor.boldFont);
		panel.add(lblSelectTheInstrument);
		
		panel1 = new JPanel();
		panel1.setBackground(color);
		contentPane.add(panel1, BorderLayout.WEST);
		
		WindowEditor.createBack(null, contentPane, this, color);
		
		EquipmentController equipmentController = EquipmentController.getInstance();
		Equipment equipment = equipmentController.extractEquipment();
		ArrayList<JButton> instrumentsDelete = new ArrayList<>();
		
		for(Entry<String, Double> i : equipment.getInstruments().entrySet()) {
			instrumentsDelete.add(new JButton(i.getKey()));
		}
		for(JButton b : instrumentsDelete) {
			b.setFont(WindowEditor.boldFont);
			panel1.add(b);
			b.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			WindowEditor.back(this,new EquipmentWindow());
		}
		else {
			EquipmentController equipmentController = EquipmentController.getInstance();
			String instrumentDel = e.getActionCommand();
			equipmentController.delete(instrumentDel);
			
			JButton clickedButton = (JButton)e.getSource();
			panel1.remove(clickedButton);
			repaint();
		}
	}
}
