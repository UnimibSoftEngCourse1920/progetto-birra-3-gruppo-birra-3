package main.java.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.java.instruments.EquipmentController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class InsertInstrumentsWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;

	public InsertInstrumentsWindow(int numberInstruments) {
		super("Brew Day! - Insert instruments");
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
		
		JLabel lblInsertTheName = new JLabel("Insert the name and the capacity of instruments:");
		lblInsertTheName.setFont(boldFont);
		panel.add(lblInsertTheName);
		
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(252, 255, 166));
		contentPane.add(panel1, BorderLayout.CENTER);
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name","Capacity (l)"}, 0);
		for(int i=0; i<numberInstruments; i++) {
			model.addRow(new String[] {"","0.0"});
		}
		
		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(new Color(252, 255, 166));
		scrollPane.getViewport().setBackground(new Color(252, 255, 166));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(252, 255, 166));
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(boldFont);
		btnSave.addActionListener(this);
		
		panel2.add(btnSave);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(boldFont);
		btnBack.addActionListener(this);
		panel2.add(btnBack);
	}
	
	private Map<String, Double> createInstruments(){
		try {
			Map<String, Double> instruments = new HashMap<>();
			for (int i = 0; i < table.getRowCount(); i++) {
				String instrumentName = table.getValueAt(i, 0).toString();
				String instrumentCapacity = table.getValueAt(i, 1).toString();
				if (!instrumentName.matches("[a-zA-Z_]+") || instrumentName.length() >= 13) {
					if(instrumentName.length() == 0) throw new IllegalArgumentException("Please insert a name to identify the instrument");
					else throw new IllegalArgumentException("The instrument name is too long or contains non-literal characters");
				}
				else if(!instrumentCapacity.matches("[0-9]+.{0,1}[0-9]*")) {
					throw new NumberFormatException("Insert only positive numbers in capacity field, separated by dot (e.g. Kettle 10.50)");
				}
				
				instruments.put(instrumentName, Double.parseDouble(instrumentCapacity));
			}
			return instruments;
		}catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage());
			return null;
		}catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			return null;
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			CreateEquipmentWindow crtEquipmentWin = new CreateEquipmentWindow();
			crtEquipmentWin.setVisible(true);
			dispose();
		}
		else {
			if (table.isEditing())
			    table.getCellEditor().stopCellEditing();
			
			if(createInstruments() != null) {
				EquipmentController equipmentController = EquipmentController.getInstance();
				equipmentController.createEquipment(createInstruments());
				
				new ShowEquipmentWindow().setVisible(true);
				dispose();
			}
		}
	}
}
