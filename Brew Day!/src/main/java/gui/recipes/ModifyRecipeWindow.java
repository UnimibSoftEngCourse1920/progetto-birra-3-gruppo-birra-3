package main.java.gui.recipes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.gui.WindowEditor;
import main.java.recipes.Recipe;
import main.java.recipes.RecipeController;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class ModifyRecipeWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	Recipe recipe;
	private JTextField textField;

	public ModifyRecipeWindow(int recipeId){
		super("Brew Day! - Modify recipe");
		
		contentPane = WindowEditor.showWindow(this, new Color(189, 216, 255));
		
		Font plainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        
		RecipeController recipeController = RecipeController.getInstance();
		ArrayList<Recipe> recipes = (ArrayList<Recipe>) recipeController.extractRecipe();
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).getId() == recipeId) {
				recipe = recipes.get(i);
			}
		}
		
		Map<String, Double> ingredients = new HashMap<>();
		String recipeName = recipe.getName();
		ingredients = recipe.getIngredients();
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(189, 216, 255));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertTheName = new JLabel("Modify the ingredients of the recipe " + recipeName + ":");
		lblInsertTheName.setFont(boldFont);
		panel.add(lblInsertTheName);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(189, 216, 255));
		contentPane.add(panel1, BorderLayout.CENTER);
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Ingredient name", "Quantity (g)"}, 0) {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       return column == 1;
			   }
			};
		ArrayList<String> otherIngredients = new ArrayList<>(5);
		otherIngredients.add("Malt");
		otherIngredients.add("Hop");
		otherIngredients.add("Yeast");
		otherIngredients.add("Sugar");
		otherIngredients.add("Additive");
		
		for (int i = 0; i < otherIngredients.size(); i++) {
				if(ingredients.get(otherIngredients.get(i)) != null) {
					model.addRow(new String[] {otherIngredients.get(i),Double.toString(ingredients.get(otherIngredients.get(i)))});
				}
				else {
					model.addRow(new String[] {otherIngredients.get(i),"0.0"});
				}
		}
		
		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(40);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(189, 216, 255));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(189, 216, 255));
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		btnSave.setFont(boldFont);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblModifyTheName = new JLabel("Modify the name of the recipe " + recipeName + ":");
		lblModifyTheName.setFont(boldFont);
		panel2.add(lblModifyTheName);
		
		textField = new JTextField();
		textField.setFont(plainFont);
		panel2.add(textField);
		textField.setColumns(10);
		panel2.add(btnSave);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(boldFont);
		btnBack.addActionListener(this);
		panel2.add(btnBack); 
	}
	
	public void actionPerformed(ActionEvent e) {
		RecipeWindow recipeWindow;
		switch(e.getActionCommand()) {
			case "Back":
				setVisible(false);
				recipeWindow = new RecipeWindow();
				recipeWindow.setVisible(true);
				dispose();
				break;
			case "Save":
				if (table.isEditing()) {
					table.getCellEditor().stopCellEditing();
				}
				
				String name = updateName();
				Map<String, Double> ingredients = updateIngredients();
				
				if(name != null && ingredients != null) {
					RecipeController recipeController = RecipeController.getInstance();
					recipeController.update(recipe.getId(),name,ingredients);
					recipeWindow = new RecipeWindow();
					
					recipeWindow.setVisible(true);
					dispose();
				}
				break;
			default:
		}
	}
	
	private Map<String, Double> updateIngredients(){
		try {
			Map<String, Double> ingredients = new HashMap<>();
			
			for (int i = 0; i < table.getRowCount(); i++) {
				String ingredientName = table.getValueAt(i, 0).toString();
				String ingredientQuantity = table.getValueAt(i, 1).toString();
				if (!ingredientName.matches("[a-zA-Z_]+")) {
					throw new IllegalArgumentException();
				}
				else if(!(ingredientQuantity.equals("0.0") || ingredientQuantity.equals("0") || ingredientQuantity.equals(""))) {
					ingredients.put(table.getValueAt(i, 0).toString(), fromStringToDouble(ingredientQuantity));
				}
			}
			if(ingredients.isEmpty()) {
				throw new NullInputException();
			}
			return ingredients;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,"Insert only positive numbers in quantity field, separated by dot (e.g. Sugar 10.50)");
			return null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"Insert only string in ingredient name field");
			return null;
		} catch (NullInputException e) {
			JOptionPane.showMessageDialog(this,"Insert at least an ingredient");
			return null;
		}
	}
	
	private String updateName(){
		try {
			String name = textField.getText();
			if(name.equals("")) {
				name = recipe.getName();
			}
			else if (!name.matches("^[a-zA-Z0-9]*$") || name.length() >= 17) {
				throw new IllegalArgumentException();
			}
			return name;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"The recipe name is not alphanumeric or too long");
			return null;
		}
	}
	
	private double fromStringToDouble(String str) {
		if (str.contains("-")) {
			throw new NumberFormatException();
		} 
		return Double.parseDouble(str);
	}
}
