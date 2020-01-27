package main.gui.resources;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.resources.Storage;
import main.resources.StorageController;

@SuppressWarnings("serial")
public class ModifyStorageIngredientsWindow extends JFrame implements ActionListener{

	private StorageController sController;

	private JPanel contentPane;
	private JTable table;

	public ModifyStorageIngredientsWindow() {
		super("Brew Day! - Storage");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setExtendedState(Frame.MAXIMIZED_BOTH); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) (screenSize.width/1.5), (int) (screenSize.height/1.5));
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 154, 162));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 154, 162));
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("The storage is:");
		label.setFont(boldFont);
		panel.add(label);

		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(255, 154, 162));
		contentPane.add(panel1, BorderLayout.CENTER);

		sController = StorageController.getInstance();

		Storage storage = Storage.getInstance();

		DefaultTableModel model = new DefaultTableModel(new String[]{"Name","Quantity (g)"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};

		for(Entry<String, Double> i : storage.getIngredients().entrySet()) {
			model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}

		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(255, 154, 162));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(255, 154, 162));
		contentPane.add(panel2, BorderLayout.SOUTH);

		JButton modifyButton = new JButton("Save");
		modifyButton.setFont(boldFont);
		modifyButton.addActionListener(this);

		panel2.add(modifyButton);

		JButton backButton = new JButton("Cancel");
		backButton.setFont(boldFont);
		backButton.addActionListener(this);

		panel2.add(backButton);
	}

	private Map<String,Double> extractIngredients() {
		try {
			Map<String,Double> newIngredients = new HashMap<>();
			for (int i = 0; i < table.getRowCount(); i++) {
				newIngredients.put(table.getValueAt(i, 0).toString(), fromStringToDouble(table.getValueAt(i, 1).toString()));
			}
			return newIngredients;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,"Insert only positive number in quantity field");
			return null;
		} 
	}

	private Double fromStringToDouble(String str) {
		if (str.contains("-")) {
			throw new NumberFormatException();
		} 
		return Double.parseDouble(str);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			if (table.isEditing())
			    table.getCellEditor().stopCellEditing();
			
			if (extractIngredients() != null) {
				sController.update(extractIngredients());

				StorageWindow sWindow = new StorageWindow();
				sWindow.setVisible(true);
				dispose();
			}
		} else {
			StorageWindow sWindow = new StorageWindow();
			sWindow.setVisible(true);
			dispose();
		}
	}

}
