package main.gui.recipes;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.gui.ButtonColumn;
import main.gui.MainWindow;
import main.recipes.Brew;
import main.recipes.Recipe;
import main.recipes.RecipeController;

@SuppressWarnings("serial")
public class RecipeWindow extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JTable table;
	
	public RecipeWindow(){
		super("Brew Day! - Recipes");
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("The Recipes are:");
		label.setFont(new Font(label.getFont().getName(),Font.BOLD, 17));
		panel.add(label);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		
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
				ingredients.append("   " + i.getKey() + " = " + Double.toString(i.getValue()));
			}
			model.addRow(new String[] {Integer.toString(r.getId()),r.getName(),ingredients.toString(),"Brew it!","Modify","Delete"});
			ingredients = new StringBuilder();
		}
        
		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
		table.getColumnModel().getColumn(2).setPreferredWidth(350);
		table.getTableHeader().setFont(new Font(table.getFont().getName(), Font.PLAIN, 14));
		table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 12));
		table.setRowHeight(40);
		table.setFillsViewportHeight(true);
		
		new ButtonColumn(table, this, 3);
		new ButtonColumn(table, this, 4);
		new ButtonColumn(table, this, 5);
		
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setFont(new Font(backButton.getFont().getName(),Font.BOLD, 18));
		panel2.add(backButton);
		
		JButton addRecipeButton = new JButton("New recipe");
		addRecipeButton.addActionListener(this);
		addRecipeButton.setFont(new Font(backButton.getFont().getName(),Font.BOLD, 18));
		panel2.add(addRecipeButton);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Back":
				setVisible(false);
				new MainWindow().setVisible(true);
				dispose();
				break;
			case "New recipe":
				if (table.isEditing())
				    table.getCellEditor().stopCellEditing();
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
}