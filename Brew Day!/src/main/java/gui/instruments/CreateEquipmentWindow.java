package main.java.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.gui.WindowEditor;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CreateEquipmentWindow extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;

	public CreateEquipmentWindow() {
		super("Brew Day! - Create equipment");
		
		Color color = new Color(252, 255, 166);
		
		contentPane = WindowEditor.showWindow(this, color);
		
		JPanel panel = new JPanel();
		panel.setBackground(color);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblCreateEquipment = new JLabel("Create Equipment");
		lblCreateEquipment.setFont(WindowEditor.boldFont);
		panel.add(lblCreateEquipment);
		
		JPanel panel2 = WindowEditor.createGridBagPanel(contentPane, color, new int[]{301, 0}, new int[]{0, 0, 0, 0},
				new double[]{10.0, Double.MIN_VALUE}, new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE});
		
		WindowEditor.createGridBagLabel("How many instruments do you want to insert?", panel2, 0);
		textField = WindowEditor.createGridBagTextField(panel2, 1,false);
		WindowEditor.createGridBagButton("Next >", this, panel2, 2,false);
	
		WindowEditor.createBack(null, contentPane, this, color);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			WindowEditor.back(this,new EquipmentWindow());
		}
		else {
			try {
				String inputString = textField.getText();
				int input = Integer.parseInt(inputString);
				if (input < 0){
					throw new NumberFormatException();
				} 
				InsertInstrumentsWindow insertInstrumentsWin = new InsertInstrumentsWindow(input);
				insertInstrumentsWin.setVisible(true);
				dispose();
			} catch (NumberFormatException|NullPointerException e1) {
				JOptionPane.showMessageDialog(this,"You must insert a positive integer number");
			}	
		}
	}
}
