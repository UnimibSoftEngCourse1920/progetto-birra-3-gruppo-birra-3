package main.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import main.instrument.EquipmentController;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class InsertInstrumentsWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public InsertInstrumentsWindow(int numberInstruments) {
		super("Brew Day! - Insert instruments");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(200, 200, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertTheName = new JLabel("Insert the name and the capacity of instruments:");
		panel.add(lblInsertTheName);
		
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name","Capacity (l)"}, 0);
		for(int i=0; i<numberInstruments; i++) {
			model.addRow(new String[] {"",""});
		}
		
		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (table.isEditing())
				    table.getCellEditor().stopCellEditing();
				
				EquipmentController equipmentController = EquipmentController.getInstance();
				equipmentController.createEquipment(createInstruments());
				
				EquipmentWindow equipmentWin = new EquipmentWindow();
				equipmentWin.setVisible(true);
				dispose();
			}
		});
		
		panel2.add(btnSave);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EquipmentWindow equipmentWin = new EquipmentWindow();
				equipmentWin.setVisible(true);
				dispose();
			}
		});
		panel2.add(btnBack);
	}
	
	private Map<String, Double> createInstruments(){
		try {
			Map<String, Double> instruments = new HashMap<>();
			for (int i = 0; i < table.getRowCount(); i++) {
				String instrumentName = table.getValueAt(i, 0).toString();
				if (!instrumentName.matches("[a-zA-Z_]+")) {
					throw new IllegalArgumentException();
				}
				
				instruments.put(table.getValueAt(i, 0).toString(), fromStringToDouble(table.getValueAt(i, 1).toString()));
			}
			return instruments;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,"Insert only positive number in quantity field");
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"Insert only string in name field");
		}
		return null;
	}
	
	private double fromStringToDouble(String str) {
		if (str.contains("-")) {
			throw new NumberFormatException();
		} 
		return Double.parseDouble(str);
	}
}
