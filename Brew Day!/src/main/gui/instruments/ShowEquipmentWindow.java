package main.gui.instruments;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.instruments.EquipmentController;

@SuppressWarnings("serial")
public class ShowEquipmentWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;

	public ShowEquipmentWindow() {
		super("Brew Day! - Show equipment");
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
		
		JLabel lblInsertTheName = new JLabel("The equipment is composed by:");
		lblInsertTheName.setFont(boldFont);
		panel.add(lblInsertTheName);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(252, 255, 166));
		contentPane.add(panel1, BorderLayout.CENTER);
		
		Map<String, Double> instruments = new HashMap<>();
		EquipmentController equipmentController = EquipmentController.getInstance();
		instruments = equipmentController.extractEquipment().getInstruments();
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name","Capacity (l)"}, 0) {
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
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(252, 255, 166));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(252, 255, 166));
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(boldFont);
		btnBack.addActionListener(this);
		panel2.add(btnBack);
		
		JButton btnModifyEquipment = new JButton("Modify Equipment");
		btnModifyEquipment.addActionListener(this);
		btnModifyEquipment.setFont(boldFont);
		panel2.add(btnModifyEquipment);
		
		JButton btnDeleteInstrument = new JButton("Delete Instrument");
		btnDeleteInstrument.setFont(boldFont);
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
