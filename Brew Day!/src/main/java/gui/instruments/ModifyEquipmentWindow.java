package main.java.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
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

import main.java.instruments.EquipmentController;

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
		
		setExtendedState(Frame.MAXIMIZED_BOTH); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) (screenSize.width/1.5), (int) (screenSize.height/1.5));
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(252, 255, 166));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(252, 255, 166));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertTheName = new JLabel("Modify the capacity of instruments:");
		lblInsertTheName.setFont(boldFont);
		panel.add(lblInsertTheName);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(252, 255, 166));
		contentPane.add(panel1, BorderLayout.CENTER);
		
		Map<String, Double> instruments = new HashMap<>();
		EquipmentController equipmentController = EquipmentController.getInstance();
		instruments = equipmentController.extractEquipment().getInstruments();
		
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name", "Capacity (l)"}, 0) {
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
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(252, 255, 166));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(252, 255, 166));
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		btnSave.setFont(boldFont);
		panel2.add(btnSave);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnBack.setFont(boldFont);
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
			JOptionPane.showMessageDialog(this,"Insert only positive numbers, separated by dot (e.g. Kettle 10.50)");
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
