package main.java.gui.recipes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.ImageIcon;
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

import main.java.gui.ButtonColumn;
import main.java.gui.MainWindow;
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
		setExtendedState(Frame.MAXIMIZED_BOTH); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) (screenSize.width/1.5), (int) (screenSize.height/1.5));
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(189, 216, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(189, 216, 255));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("The Recipes are:");
		label.setFont(boldFont);
		panel.add(label);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(189, 216, 255));
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
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(40);
		
		new ButtonColumn(table, this, 3);
		new ButtonColumn(table, this, 4);
		new ButtonColumn(table, this, 5);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(189, 216, 255));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(189, 216, 255));
		contentPane.add(panel2, BorderLayout.SOUTH);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setFont(boldFont);
		panel2.add(backButton);
		
		JButton addRecipeButton = new JButton("New recipe");
		addRecipeButton.addActionListener(this);
		addRecipeButton.setFont(boldFont);
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