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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.instrument.EquipmentController;

@SuppressWarnings("serial")
public class ShowEquipmentWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;

	public ShowEquipmentWindow() {
		super("Brew Day! - Show equipment");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 150, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertTheName = new JLabel("The equipment is composed by:");
		panel.add(lblInsertTheName);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		
		Map<String, Double> instruments = new HashMap<>();
		EquipmentController equipmentController = EquipmentController.getInstance();
		instruments = equipmentController.extractEquipment().getInstruments();
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name","Quantity"}, 0) {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
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
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		panel2.add(btnBack);
		
		JButton btnModifyEquipment = new JButton("Modify Equipment");
		btnModifyEquipment.addActionListener(this);
		panel2.add(btnModifyEquipment);
		
		JButton btnDeleteInstrument = new JButton("Delete Instrument");
		btnDeleteInstrument.addActionListener(this);
		panel2.add(btnDeleteInstrument);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Back":
			EquipmentWindow equipmentWin = new EquipmentWindow();
			equipmentWin.setVisible(true);
			dispose();
			break;
		case "Modify Equipment":
			ModifyEquipmentWindow modifyEqWin = new ModifyEquipmentWindow();
			modifyEqWin.setVisible(true);
			dispose();
			break;
		case "Delete Instrument":
			DeleteInstrumentWindow deleteInstrumentWin = new DeleteInstrumentWindow();
			deleteInstrumentWin.setVisible(true);
			dispose();
		default:
		}
	}

}
