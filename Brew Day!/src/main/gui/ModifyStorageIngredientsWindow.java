package main.gui;

import java.awt.BorderLayout;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 800, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("The storage is:");
		label.setFont(new Font(label.getFont().getName(),Font.BOLD, 17));
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		sController = StorageController.getInstance();
			
		Storage storage = sController.extractStorage();
		
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
		table.getTableHeader().setFont(new Font(table.getFont().getName(), Font.PLAIN, 16));
		table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 13));
		table.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton modifyButton = new JButton("Save");
		modifyButton.setFont(new Font(modifyButton.getFont().getName(), Font.BOLD, 20));
		modifyButton.addActionListener(this);
		
		panel_2.add(modifyButton);
		
		JButton backButton = new JButton("Cancel");
		backButton.setFont(new Font(modifyButton.getFont().getName(), Font.BOLD, 20));
		backButton.addActionListener(this);
		
		panel_2.add(backButton);
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

	private double fromStringToDouble(String str) {
		if (str.contains("-")) {
			throw new NumberFormatException();
		} 
		return Double.parseDouble(str);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			if (extractIngredients() != null) {
				sController.update(extractIngredients());

				StorageWindow sWindow = new StorageWindow();

				sController.deleteFile();

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
