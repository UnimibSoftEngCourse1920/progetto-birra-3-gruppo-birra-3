package main.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.resources.*;

@SuppressWarnings("serial")
public class StorageWindow extends JFrame implements ActionListener {

	private String filepath = System.getProperty("user.dir") + "\\src\\Files\\Storage.txt";
	private StorageController sController;
	
	private JPanel contentPane;
	private JTable table;

	public StorageWindow() {
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
		       return false;
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
		
		JButton modifyButton = new JButton("Modify ingredients");
		modifyButton.setFont(new Font(modifyButton.getFont().getName(), Font.BOLD, 20));
		modifyButton.addActionListener(this);
		
		panel_2.add(modifyButton);
		
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font(modifyButton.getFont().getName(), Font.BOLD, 15));
		backButton.addActionListener(this);
		
		panel_2.add(backButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		File f = new File(filepath);
		if(!f.exists()) {
			Map<String,Double> defaultIngredients = new HashMap<>();
			defaultIngredients.put("Malt", 0.0);
			defaultIngredients.put("Hop", 0.0);
			defaultIngredients.put("Yeast", 0.0);
			defaultIngredients.put("Sugar", 0.0);
			defaultIngredients.put("Additive", 0.0);
			sController.createStorage(defaultIngredients);
		}
		if (e.getActionCommand().equals("Back")) {
			MainWindow mainWindow = MainWindow.getInstance();
			mainWindow.setVisible(true);
			dispose();
		} else {
			ModifyStorageIngredientsWindow msiWindow = new ModifyStorageIngredientsWindow();
			msiWindow.setVisible(true);
			dispose();
		}
	}
}