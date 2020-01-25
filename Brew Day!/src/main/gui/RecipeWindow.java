package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import main.recipes.Brew;
import main.recipes.Recipe;
import main.recipes.RecipeController;

@SuppressWarnings("serial")
public class RecipeWindow extends JFrame implements ActionListener{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public RecipeWindow(){
		super("Brew Day! - Recipes");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
		
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
		
		new ButtonColumn(recipesTable, this, 3);
		new ButtonColumn(recipesTable, this, 4);
		new ButtonColumn(recipesTable, this, 5);
		
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

		StringBuilder ingredients = new StringBuilder();
		for(Recipe r : recipes) {
			for(Entry<String, Double> i : r.getIngredients().entrySet()) {
				ingredients.append(i.getKey() + ": " + Double.toString(i.getValue()) + "\n");
			}
			model.addRow(new String[] {Integer.toString(r.getId()),r.getName(),ingredients.toString(),"Brew it!","Modify","Delete"});
		}

		return model;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Back":
				setVisible(false);
				new MainWindow().setVisible(true);
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
						JTable table = (JTable)e.getSource();
				        ((DefaultTableModel)table.getModel()).removeRow(row);
				        break;
				    default:
			}
		}
	}
	
	public static void main(String[] args){
		RecipeWindow gui = new RecipeWindow();
		gui.setVisible(true);
	}

}
