package main.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.instruments.EquipmentController;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ModifyEquipmentWindow extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;

	public ModifyEquipmentWindow() {
		super("Brew Day! - Modify equipment");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertTheName = new JLabel("Modify the capacity of instruments:");
		lblInsertTheName.setFont(new Font(lblInsertTheName.getFont().getName(), Font.BOLD, 20));
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
		table.getTableHeader().setFont(new Font(table.getFont().getName(), Font.PLAIN, 16));
		table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 13));
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		btnSave.setFont(new Font(btnSave.getFont().getName(), Font.BOLD, 15));
		panel2.add(btnSave);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnBack.setFont(new Font(btnBack.getFont().getName(), Font.BOLD, 15));
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			new EquipmentWindow().setVisible(true);
			dispose();
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
