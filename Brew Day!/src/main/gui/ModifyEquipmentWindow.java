package main.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.instrument.EquipmentController;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ModifyEquipmentWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public ModifyEquipmentWindow() {
		super("Brew Day! - Modify equipment");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 150, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertTheName = new JLabel("Modify the capacity of instruments:");
		panel.add(lblInsertTheName);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		
		Map<String, Double> instruments = new HashMap<>();
		EquipmentController equipmentController = EquipmentController.getInstance();
		instruments = equipmentController.extractEquipment().getInstruments();
		
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name", "Capacity"}, 0) {
				@Override
				   public boolean isCellEditable(int row, int column) {
				       return column == 1;
				   }
				};
		for(Entry<String, Double> i : instruments.entrySet()) {
			model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
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
				
				if(updateInstruments() != null) {
					EquipmentController equipmentController = EquipmentController.getInstance();
					equipmentController.update(updateInstruments());
					
					ModifyEquipmentWindow modEquipmentWin = new ModifyEquipmentWindow();
					modEquipmentWin.setVisible(true);
					dispose();
				}else {
					ModifyEquipmentWindow modEquipmentWin = new ModifyEquipmentWindow();
					modEquipmentWin.setVisible(true);
					dispose();
				}
				
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
			JOptionPane.showMessageDialog(this,"Insert only positive numbers, separated by dot");
			return null;
		}
	}

}
