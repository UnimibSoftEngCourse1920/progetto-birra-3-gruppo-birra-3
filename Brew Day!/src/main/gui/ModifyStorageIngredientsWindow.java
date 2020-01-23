package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.resources.Storage;
import main.resources.StorageController;

@SuppressWarnings("serial")
public class ModifyStorageIngredientsWindow extends JFrame implements ActionListener{

	private StorageController sController;
	private JTable ingredientsTable;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 600;

	public ModifyStorageIngredientsWindow() {
		super();
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Modfy Ingredeints");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		sController = StorageController.getInstance();

		Storage storage = sController.extractStorage();

		ingredientsTable = new JTable(createIngredientsTable(storage.getIngredients()));

		ingredientsTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 25));
		ingredientsTable.setFont(new Font("Arial", Font.PLAIN, 20));
		ingredientsTable.setRowHeight(30);
		ingredientsTable.setFillsViewportHeight(true);
		ingredientsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(ingredientsTable));

		this.add(tablePanel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel();

		JButton saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(250, 50));
		saveButton.setFont(new Font("Arial", Font.BOLD, 20));
		saveButton.addActionListener(this);

		buttonPanel.add(saveButton);

		JPanel cancelButtonPanel = new JPanel();

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Arial", Font.PLAIN, 15));
		cancelButton.addActionListener(this);

		cancelButtonPanel.add(cancelButton);

		this.add(buttonPanel, BorderLayout.CENTER);
		this.add(cancelButtonPanel, BorderLayout.SOUTH);
	}

	private DefaultTableModel createIngredientsTable(Map<String,Double> ingredients) {
		DefaultTableModel model = new DefaultTableModel(new String[]{"Name","Quantity (g)"}, 0);

		for(Entry<String, Double> i : ingredients.entrySet()) {
			model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}

		return model;
	}

	private Map<String,Double> extractIngredients() {
		try {
			Map<String,Double> newIngredients = new HashMap<>();
			for (int i = 0; i < ingredientsTable.getRowCount(); i++) {
				String ingredientName = ingredientsTable.getValueAt(i, 0).toString();
				if (!ingredientName.matches("[a-zA-Z_]+")) {
					throw new IllegalArgumentException();
				}
				newIngredients.put(ingredientsTable.getValueAt(i, 0).toString(), fromStringToDouble(ingredientsTable.getValueAt(i, 1).toString()));
			}
			return newIngredients;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,"Insert only positive number in quantity field");
			return null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"Insert only string in name field");
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
