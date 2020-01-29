package main.java.gui.recipes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class CreateRecipeWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;

	public CreateRecipeWindow(){
		super("Brew Day! - Create recipe");
		
		Color color = new Color(189, 216, 255);
		
		contentPane = WindowEditor.showWindow(this, color);
		
	    WindowEditor.initializeWindow(contentPane, color, "Set the ingredients needed for the new recipe:");
	
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Ingredient name", "Quantity (g)"}, 0) {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       return column == 1;
			   }
			};
			
		model.addRow(new String[] {"Malt","0.0"});
		model.addRow(new String[] {"Hop","0.0"});
		model.addRow(new String[] {"Yeast","0.0"});
		model.addRow(new String[] {"Sugar","0.0"});
		model.addRow(new String[] {"Additive","0.0"});
		
		table = WindowEditor.createTable(model, this, color, 40);
		
		textField = WindowEditor.createTextField(this, "Set the name of the new recipe: ", color);
		
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
				
				String name = createName();
				Map<String, Double> ingredients = WindowEditor.createIngredients(this,table);
				
				if(name != null && ingredients != null) {
					Recipe recipe = new Recipe(name,ingredients);
					
					RecipeController recipeController = RecipeController.getInstance();
					recipeController.store(recipe);
					recipeWindow = new RecipeWindow();
					
					recipeWindow.setVisible(true);
					dispose();
				}
				break;
			default:
		}
	}
	
	private String createName(){
		try {
			String name = textField.getText();
			if(name.equals("")) {
				throw new NullInputException();
			}
			else if (!name.matches("^[a-zA-Z0-9]*$") || name.length() >= 17) {
				throw new IllegalArgumentException();
			}
			return name;
		} catch (NullInputException e) {
			JOptionPane.showMessageDialog(this,"Insert a string name for the new recipe");
			return null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"The recipe name is not alphanumeric or too long");
			return null;
		}
	}
}
