package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
public class StorageWindow extends JFrame {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public static void main(String[] args) {
		StorageWindow window = new StorageWindow();
		window.setVisible(true);
	}

	public StorageWindow() {
		super();
		setSize(WIDTH, HEIGHT);
		this.setTitle("Storage");
		this.setBackground(Color.RED);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTable ingredientsTable = new JTable(createIngredientsTable());
		
		ingredientsTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 25));
		ingredientsTable.setFont(new Font("Arial", Font.PLAIN, 20));
		ingredientsTable.setRowHeight(30);
		ingredientsTable.setFillsViewportHeight(true);
		ingredientsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(ingredientsTable));
		
		this.add(tablePanel, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setSize(40, 40);
		
		JButton modifyButton = new JButton("Modify ingredients");
		modifyButton.setPreferredSize(new Dimension(200, 50));
		modifyButton.setFont(new Font("Arial", Font.BOLD, 20));
		
		buttonPanel.add(modifyButton);
		
		this.add(buttonPanel,BorderLayout.CENTER);
	}

	public static DefaultTableModel createIngredientsTable() {
		//only for temporary test
		Storage storage = Storage.getInstance();
		Map<String,Double> ingredients = new HashMap<String,Double>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Hop", 20.0);
		ingredients.put("Yeast", 30.0);
		ingredients.put("Sugar", 40.0);
		ingredients.put("Additive", 50.0);
		storage.setIngredients(ingredients);
		StorageController.getInstance().store(storage);

		Map<String,Double> storageIngredients = StorageController.getInstance().extractStorage().getIngredients();

		StorageController.getInstance().deleteFile();

		DefaultTableModel model = new DefaultTableModel(new String[]{"Name","Quantity"}, 0) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};

		for(Entry<String, Double> i : storageIngredients.entrySet()) {
			model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}

		return model;
	}
}
