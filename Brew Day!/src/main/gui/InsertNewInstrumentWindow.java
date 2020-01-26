package main.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.instrument.EquipmentController;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JButton;
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
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(150, 150, 600, 400);
		//setBounds(300, 150, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertNewInstrument = new JLabel("Insert new instrument in your equipment:");
		panel.add(lblInsertNewInstrument);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		GridBagLayout gblPanel1 = new GridBagLayout();
		gblPanel1.columnWidths = new int[]{121};
		gblPanel1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gblPanel1.columnWeights = new double[]{0.0};
		gblPanel1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel1.setLayout(gblPanel1);
		
		JLabel lblName = new JLabel("Instrument name:");
		GridBagConstraints gbcLblName = new GridBagConstraints();
		gbcLblName.fill = GridBagConstraints.VERTICAL;
		
		gbcLblName.insets = new Insets(0, 0, 5, 0);
		gbcLblName.gridx = 0;
		gbcLblName.gridy = 0;
		panel1.add(lblName, gbcLblName);
		
		textField = new JTextField();
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.insets = new Insets(0, 0, 5, 0);
		gbcTextField.fill = GridBagConstraints.BOTH;
		
		gbcTextField.gridx = 0;
		gbcTextField.gridy = 1;
		panel1.add(textField, gbcTextField);
		textField.setColumns(10);
		
		JLabel lblCapacity = new JLabel("Capacity (l):");
		GridBagConstraints gbcLblCapacity = new GridBagConstraints();
		gbcLblCapacity.fill = GridBagConstraints.VERTICAL;
		
		gbcLblCapacity.insets = new Insets(0, 0, 5, 0);
		gbcLblCapacity.gridx = 0;
		gbcLblCapacity.gridy = 2;
		panel1.add(lblCapacity, gbcLblCapacity);
		
		textField1 = new JTextField();
		GridBagConstraints gbcTextField1 = new GridBagConstraints();
		gbcTextField1.fill = GridBagConstraints.BOTH;
		gbcTextField1.gridx = 0;
		gbcTextField1.gridy = 3;
		panel1.add(textField1, gbcTextField1);
		textField1.setColumns(10);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		panel2.add(btnSave);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		panel2.add(btnBack);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			new EquipmentWindow().setVisible(true);
			dispose();
		}
		else {
			try {
				EquipmentController equipmentController = EquipmentController.getInstance();
				Map<String, Double> instruments = equipmentController.extractEquipment().getInstruments();
				
				boolean b = true;
				String name = textField.getText();
				String capacity = textField1.getText();
				
				if(!name.matches("[a-zA-Z_]+") || !capacity.matches("[0-9]+.{0,1}[0-9]*")) {
					throw new IllegalArgumentException();
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
				}
				new ShowEquipmentWindow().setVisible(true);
				dispose();
			}catch (IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(this,"Insert only string in name field and positive numbers , separated by dot (e.g. Kettle 10.50");
			}
		}
	}

}
