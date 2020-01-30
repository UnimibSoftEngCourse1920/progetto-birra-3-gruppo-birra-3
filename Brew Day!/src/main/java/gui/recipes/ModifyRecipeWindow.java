package main.java.gui.recipes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.gui.WindowEditor;
import main.java.recipes.Recipe;
import main.java.recipes.RecipeController;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ModifyRecipeWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	Recipe recipe;
	private JTextField textField;

	public ModifyRecipeWindow(int recipeId){
		super("Brew Day! - Modify recipe");
		
		Color color = new Color(189, 216, 255);
		
		contentPane = WindowEditor.showWindow(this, color);
        
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
		
		WindowEditor.initializeWindow(contentPane, color, "Modify the ingredients of the recipe " + recipeName + ":");
		
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
		
		table = WindowEditor.createTable(model, this, color, 40);
		
		textField = WindowEditor.createTextField(this, "Modify the name of the recipe " + recipeName + ":", color);
		
	    WindowEditor.createBackAndOther((JPanel) textField.getParent(), contentPane, this, color,"Save");
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Back":
				RecipeWindow recipeWindow = new RecipeWindow();
				WindowEditor.back(this,recipeWindow);
				break;
			case "Save":
				if (table.isEditing()) 
					table.getCellEditor().stopCellEditing();
				
				String name = updateName();
				Map<String, Double> ingredients = WindowEditor.createIngredients(this,table);
				
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
	
	/*
	 * Returns the string inserted in the textField. Shows an alert message
	 * and returns null if the input format is not correct or if nothing has 
	 * been inserted
	 */
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
}
