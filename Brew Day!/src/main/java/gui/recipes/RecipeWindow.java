package main.java.gui.recipes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.gui.ButtonColumn;
import main.java.gui.MainWindow;
import main.java.gui.WindowEditor;
import main.java.instruments.EquipmentController;
import main.java.recipes.Brew;
import main.java.recipes.Recipe;
import main.java.recipes.RecipeController;

@SuppressWarnings("serial")
public class RecipeWindow extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JTable table;
	
	public RecipeWindow(){
		super("Brew Day! - Recipes");
		
		Color color = new Color(189, 216, 255);
		
		contentPane = WindowEditor.showWindow(this, color);
		
		WindowEditor.initializeWindow(contentPane, color, "The Recipes are:");
		
		RecipeController recipeController = RecipeController.getInstance();

		ArrayList<Recipe> recipes = (ArrayList<Recipe>) recipeController.extractRecipe();
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Number","Name","Ingredients","","",""}, 0) {
			@Override
		    public boolean isCellEditable(int row, int column) {
				switch (column) {
				 case 2:
				 case 3:
		         case 4:
		         case 5:
		             return true;
		         default:
		             return false;
		      }
		    }
		};

		StringBuilder ingredients = new StringBuilder();
		for(Recipe r : recipes) {
			for(Entry<String, Double> i : r.getIngredients().entrySet()) {
				ingredients.append("   " + i.getKey() + " = " + Double.toString(i.getValue()) + "g");
			}
			model.addRow(new String[] {Integer.toString(r.getId()),r.getName(),ingredients.toString(),"Brew it!","Modify","Delete"});
			ingredients = new StringBuilder();
		}
        
		table = WindowEditor.createTable(model, this, color, 30);
		table.getColumnModel().getColumn(2).setPreferredWidth(350);
		
		new ButtonColumn(table, this, 3);
		new ButtonColumn(table, this, 4);
		new ButtonColumn(table, this, 5);
		
		WindowEditor.createBackAndOther(null, contentPane, this, color,"New recipe");
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Back":
				WindowEditor.back(this,new MainWindow());
				break;
			case "New recipe":
				File f = new File(System.getProperty("user.dir") + "\\src\\Files\\Equipment.txt");

				if (!f.exists() || EquipmentController.getInstance().extractEquipment().getInstruments().isEmpty()) {
					JOptionPane.showMessageDialog(this, "You need to insert your equipment first!");
				} else if(EquipmentController.getInstance().extractEquipment().getCapacity().compareTo(0.0) == 0){
					JOptionPane.showMessageDialog(this, "Your equipment has a capacity equal to zero, change it!");
				}else {
					setVisible(false);
					new CreateRecipeWindow().setVisible(true);
					dispose();
				}
				break;
			default:
				String[] tokens = e.getActionCommand().split("/");
				int recipeId = Integer.parseInt(tokens[0]);
				String command = tokens[1];
				int row = Integer.parseInt(tokens[2]);
				
				RecipeController recipeController = RecipeController.getInstance();
				switch(command) {
					case "Brew it!":
						Brew brew = recipeController.createBrew(recipeId);
						if(brew == null) {
							Map<String,Double> missingIngredients = recipeController.getMissingIngredients(recipeId);
							StringBuilder missingAlert = new StringBuilder();
							missingAlert.append("Some ingredients are missing from your storage! \nYou should buy:");
							for(Entry<String, Double> i : missingIngredients.entrySet()) {
								missingAlert.append("\n" + i.getKey() + " : " + missingIngredients.get(i.getKey()));
							}
							
							JOptionPane.showMessageDialog(this, missingAlert.toString());
						}
						else {
							setVisible(false);
							new BrewWindow().setVisible(true);
							dispose();
						}
						break;
					case "Modify":
						setVisible(false);
						new ModifyRecipeWindow(recipeId).setVisible(true);
						dispose();
						break;
					case "Delete":
						recipeController.delete(recipeId);
						JTable table1 = (JTable)e.getSource();
				        ((DefaultTableModel)table1.getModel()).removeRow(row);
				        break;
				    default:
			}
		}
	}
}