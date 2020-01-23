package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.recipes.Recipe;
import main.recipes.RecipeController;

@SuppressWarnings("serial")
public class RecipeWindow extends JFrame implements ActionListener{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public RecipeWindow(){
		super("Recipes");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 0));
		
		JPanel viewRecipesPanel = new JPanel();
		viewRecipesPanel.setLayout(new BorderLayout());
		mainPanel.add(viewRecipesPanel);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		mainPanel.add(bottomPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		
		Font f = new Font("TimesRoman",Font.BOLD,25);
		JTable recipesTable = new JTable(createRecipesTable());
		recipesTable.getTableHeader().setFont(f);
		recipesTable.setFont(f);
		recipesTable.setRowHeight(30);
		recipesTable.setFillsViewportHeight(true);
		recipesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		RecipeController recipeController = RecipeController.getInstance();
		@SuppressWarnings("unused")
		ButtonColumn createBrewColumn = new ButtonColumn(recipesTable, recipeController, 3);
		@SuppressWarnings("unused")
		ButtonColumn modifyColumn = new ButtonColumn(recipesTable, recipeController, 4);
		@SuppressWarnings("unused")
		ButtonColumn deleteColumn = new ButtonColumn(recipesTable, recipeController, 5);
		
        
		viewRecipesPanel.add(recipesTable);
		
		Dimension d = new Dimension(200, 70);
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setPreferredSize(d);
		bottomPanel.add(backButton);
		
		JButton addRecipeButton = new JButton("New recipe");
		addRecipeButton.addActionListener(recipeController);
		addRecipeButton.setPreferredSize(d);
		bottomPanel.add(addRecipeButton);
	}
	
	
	public static DefaultTableModel createRecipesTable() {
		
		RecipeController recipeController = RecipeController.getInstance();

		//Only for testing purposes
		HashMap<String,Double> ingredients1 = new HashMap<>();
		ingredients1.put("Malt", 5.0); 
		ingredients1.put("Hop", 60.0); 
		Recipe recipe1 = new Recipe("Recipe1", ingredients1);
		recipeController.store(recipe1);
		
		HashMap<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Malt", 10.0); 
		ingredients2.put("Hop", 20.0); 
		Recipe recipe2 = new Recipe("Recipe2", ingredients2);
		recipeController.store(recipe2);
		//Only for testing purposes
		
		ArrayList<Recipe> recipes = recipeController.extractRecipe();
		
		//Only for testing purposes
		recipeController.deleteFile();
		//Only for testing purposes 
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Number","Name","Ingredients","","",""}, 0) {
			@Override
		    public boolean isCellEditable(int row, int column) {
				switch (column) {
				 case 3:
		         case 4:
		         case 5:
		             return true;
		         default:
		             return false;
		      }
		    }
		};

		String ingredients;
		for(Recipe r : recipes) {
			ingredients = "";
			for(Entry<String, Double> i : r.getIngredients().entrySet()) {
				ingredients +=  "  " + i.getKey() + "= " + Double.toString(i.getValue());
			}
			model.addRow(new String[] {Integer.toString(r.getId()),r.getName(),ingredients,"Brew it!","Modify","Delete"});
		}

		return model;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Back":
				setVisible(false);
				MainWindow.getInstance().setVisible(true);
				break;
		}
	}
	
	public static void main(String[] args){
		RecipeWindow gui = new RecipeWindow();
		gui.setVisible(true);
	}

}
