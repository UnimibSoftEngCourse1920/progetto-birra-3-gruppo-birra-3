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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import main.recipes.BrewController;
import main.recipes.Recipe;
import main.recipes.RecipeController;

@SuppressWarnings("serial")
public class RecipeWindow extends JFrame implements ActionListener{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public RecipeWindow(){
		super("Brew Day! - Recipes");
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
		JScrollPane scrollPane = new JScrollPane(recipesTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		
		TableColumnModel columnModel = recipesTable.getColumnModel();
		TableColumn column = columnModel.getColumn(2);
        MultiRowCell multiRowCell = new MultiRowCell();
        column.setCellEditor(multiRowCell);
        column.setCellRenderer(multiRowCell);

        int height = multiRowCell.getTableCellRendererComponent(recipesTable, "Test", true, true, 0, 0).getPreferredSize().height;
        recipesTable.setRowHeight(height);
		
		recipesTable.getTableHeader().setFont(f);
		recipesTable.setFont(f);
		recipesTable.setRowHeight(60);
		recipesTable.setFillsViewportHeight(true);
		recipesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		@SuppressWarnings("unused")
		ButtonColumn createBrewColumn = new ButtonColumn(recipesTable, this, 3);
		@SuppressWarnings("unused")
		ButtonColumn modifyColumn = new ButtonColumn(recipesTable, this, 4);
		@SuppressWarnings("unused")
		ButtonColumn deleteColumn = new ButtonColumn(recipesTable, this, 5);
		
        
		viewRecipesPanel.add(scrollPane);
		
		Dimension d = new Dimension(200, 70);
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setPreferredSize(d);
		bottomPanel.add(backButton);
		
		JButton addRecipeButton = new JButton("New recipe");
		addRecipeButton.addActionListener(this);
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
		
		/*
		Recipe recipe3 = new Recipe("Recipe3", ingredients2);
		Recipe recipe4 = new Recipe("Recipe4", ingredients2);
		Recipe recipe5 = new Recipe("Recipe5", ingredients2);
		Recipe recipe6 = new Recipe("Recipe6", ingredients2);
		Recipe recipe7 = new Recipe("Recipe7", ingredients2);
		Recipe recipe8 = new Recipe("Recipe8", ingredients2);
		Recipe recipe9 = new Recipe("Recipe9", ingredients2);
		Recipe recipe10 = new Recipe("Recipe10", ingredients2);
		Recipe recipe11 = new Recipe("Recipe11", ingredients2);
		Recipe recipe12 = new Recipe("Recipe12", ingredients2);
		recipeController.store(recipe2);
		recipeController.store(recipe3);
		recipeController.store(recipe4);
		recipeController.store(recipe5);
		recipeController.store(recipe6);
		recipeController.store(recipe7);
		recipeController.store(recipe8);
		recipeController.store(recipe9);
		recipeController.store(recipe10);
		recipeController.store(recipe11);
		recipeController.store(recipe12);
		*/
		//Only for testing purposes
		
		ArrayList<Recipe> recipes = recipeController.extractRecipe();
		
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

		String ingredients;
		for(Recipe r : recipes) {
			ingredients = "";
			for(Entry<String, Double> i : r.getIngredients().entrySet()) {
				ingredients +=  i.getKey() + ": " + Double.toString(i.getValue()) + "\n";
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
				dispose();
				break;
			case "New recipe":
				setVisible(false);
				new CreateRecipeWindow().setVisible(true);
				dispose();
				break;
			default:
				String[] tokens = e.getActionCommand().split("/");
				int recipeId = Integer.parseInt(tokens[0]);
				String command = tokens[1];
				int row = Integer.parseInt(tokens[2]);
				
				RecipeController recipeController = RecipeController.getInstance();
				switch(command) {
					case "Brew it!":
						setVisible(false);
						recipeController.createBrew(recipeId);
					    //Only for testing purposes
						BrewController brewController = BrewController.getInstance();
						brewController.deleteFile();
						//Only for testing purposes
						new CreateBrewWindow(recipeId).setVisible(true);
						dispose();
						break;
					case "Modify":
						setVisible(false);
						new ModifyRecipeWindow(recipeId).setVisible(true);
						dispose();
						break;
					case "Delete":
						recipeController.delete(recipeId);
						JTable table = (JTable)e.getSource();
				        ((DefaultTableModel)table.getModel()).removeRow(row);
			}
		}
	}
	
	public static void main(String[] args){
		RecipeWindow gui = new RecipeWindow();
		gui.setVisible(true);
	}

}
