package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import main.resources.*;

@SuppressWarnings("serial")
public class StorageWindow extends JFrame implements ActionListener {

	private StorageController sController;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 600;

	public StorageWindow() {
		super();
		setSize(WIDTH, HEIGHT);
		this.setTitle("Storage");
		this.setBackground(Color.RED);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		sController = StorageController.getInstance();
			
		Storage storage = sController.extractStorage();

		JTable ingredientsTable = new JTable(createIngredientsTable(storage.getIngredients()));
		
		ingredientsTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 25));
		ingredientsTable.setFont(new Font("Arial", Font.PLAIN, 20));
		ingredientsTable.setRowHeight(30);
		ingredientsTable.setFillsViewportHeight(true);
		ingredientsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(ingredientsTable));
		
		this.add(tablePanel, BorderLayout.NORTH);
		
		JPanel modifyButtonPanel = new JPanel();
				
		JButton modifyButton = new JButton("Modify ingredients");
		modifyButton.setPreferredSize(new Dimension(250, 50));
		modifyButton.setFont(new Font("Arial", Font.BOLD, 20));
		modifyButton.addActionListener(this);
		
		JPanel backButtonPanel = new JPanel();
		
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Arial", Font.PLAIN, 15));
		backButton.addActionListener(this);
		
		backButtonPanel.add(backButton);
		
		modifyButtonPanel.add(modifyButton);
		
		this.add(modifyButtonPanel,BorderLayout.CENTER);
		this.add(backButtonPanel, BorderLayout.SOUTH);
	}

	private DefaultTableModel createIngredientsTable(Map<String,Double> ingredients) {
		DefaultTableModel model = new DefaultTableModel(new String[]{"Name","Quantity (g)"}, 0) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};

		for(Entry<String, Double> i : ingredients.entrySet()) {
			model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}

		return model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
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