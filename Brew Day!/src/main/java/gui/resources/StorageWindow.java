package main.java.gui.resources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.gui.MainWindow;
import main.java.gui.WindowEditor;
import main.java.resources.*;

@SuppressWarnings("serial")
public class StorageWindow extends JFrame implements ActionListener {

	private StorageController sController;
	private JPanel contentPane;
	private JTable table;

	public StorageWindow() {
		super("Brew Day! - Storage");
		
		contentPane = WindowEditor.showWindow(this, new Color(255, 154, 162));
		
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
			
		Storage storage;
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Name","Quantity (g)"}, 0) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};

		storage = sController.extractStorage();
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
		
		JButton modifyButton = new JButton("Modify ingredients");
		modifyButton.setFont(boldFont);
		modifyButton.addActionListener(this);
		
		panel2.add(modifyButton);
		
		JButton backButton = new JButton("Back");
		backButton.setFont(boldFont);
		backButton.addActionListener(this);
		
		panel2.add(backButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Back")) {
			MainWindow mainWindow = new MainWindow();
			mainWindow.setVisible(true);
			dispose();
		} else {
			ModifyStorageIngredientsWindow msiWindow = new ModifyStorageIngredientsWindow();
			msiWindow.setVisible(true);
			dispose();
		}
	}
}