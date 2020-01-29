package main.java.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.gui.MainWindow;
import main.java.gui.WindowEditor;
import main.java.instruments.EquipmentController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@SuppressWarnings("serial")
public class EquipmentWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private String filepath = System.getProperty("user.dir") + "\\src\\Files\\Equipment.txt";

	public EquipmentWindow() {
		super("Brew Day! - Equipment menu");
		
		Color color = new Color(252, 255, 166);
		
		contentPane = WindowEditor.showWindow(this, color);

		JPanel panel = new JPanel();
		panel.setBackground(color);
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblEquipment = new JLabel("Equipment menu");
		lblEquipment.setFont(WindowEditor.boldFont);
		panel.add(lblEquipment);

		JPanel panel1 = WindowEditor.createGridBagPanel(contentPane, color, new int[]{121}, new int[]{0, 0, 0, 0, 0, 0, 0},
				new double[]{0.0}, new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
		
		WindowEditor.createGridBagButton("Show Equipment", this, panel1, 1,true);
		WindowEditor.createGridBagButton("Create Equipment", this, panel1, 0,true);
		WindowEditor.createGridBagButton("Modify Equipment", this, panel1, 2,true);
		WindowEditor.createGridBagButton("Insert New Instrument", this, panel1, 3,true);
		WindowEditor.createGridBagButton("Delete Instrument", this, panel1, 4,true);
		
		WindowEditor.createBack(null, contentPane, this, color);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File f;
	
		switch(e.getActionCommand()) {
			case "Show Equipment":
				f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(this, "You have no equipment to show");
				}
				else {
					EquipmentController equipmentController = EquipmentController.getInstance();
					int size = equipmentController.extractEquipment().getInstruments().size();
					if(size == 0) {
						JOptionPane.showMessageDialog(this, "You have no instruments in your equipment");
					}
					else {
						ShowEquipmentWindow showEqWin = new ShowEquipmentWindow();
						showEqWin.setVisible(true);
						dispose();
					}
				}
				break;
			case "Create Equipment":
				f = new File(filepath);
				if(!f.exists()) {
					CreateEquipmentWindow createEqWin = new CreateEquipmentWindow();
					createEqWin.setVisible(true);
					dispose();
				}
				else {
					EquipmentController equipmentController = EquipmentController.getInstance();
					int size = equipmentController.extractEquipment().getInstruments().size();
					if(size == 0) {
						CreateEquipmentWindow createEqWin = new CreateEquipmentWindow();
						createEqWin.setVisible(true);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(this, "You already have an equipment");
					}
				}
				break;
			case "Modify Equipment":
				f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(this, "You have no equipment");
				}
				else {
					EquipmentController equipmentController = EquipmentController.getInstance();
					int size = equipmentController.extractEquipment().getInstruments().size();
					if(size == 0) {
						JOptionPane.showMessageDialog(this, "You have no instruments in your equipment to modify");
					}
					else {
						ModifyEquipmentWindow modifyEqWin = new ModifyEquipmentWindow();
						modifyEqWin.setVisible(true);
						dispose();
					}
				}
				break;
			case "Insert New Instrument":
				f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(this, "You should create a new equipment first");
				}
				else {
					InsertNewInstrumentWindow insertNewInstrumentWin = new InsertNewInstrumentWindow();
					insertNewInstrumentWin.setVisible(true);
					dispose();
				}
				break;
			case "Delete Instrument":
				EquipmentController equipmentController = EquipmentController.getInstance();
				f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(this, "You have no equipment to delete");
				}else {
						int size = equipmentController.extractEquipment().getInstruments().size();
						if(size != 0) {
							DeleteInstrumentWindow deleteInstrumentWin = new DeleteInstrumentWindow();
							deleteInstrumentWin.setVisible(true);
							dispose();
						}else {
						JOptionPane.showMessageDialog(this, "You have no equipment to delete");
					}
				}
				break;
			case "Back":
				WindowEditor.back(this,new MainWindow());
				break;
			default:
		}
	}
}
