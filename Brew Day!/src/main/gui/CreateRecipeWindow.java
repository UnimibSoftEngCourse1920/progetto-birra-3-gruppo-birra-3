package main.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.recipes.Recipe;
import main.recipes.RecipeController;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class CreateRecipeWindow extends JFrame implements ActionListener {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;

	public CreateRecipeWindow(){
		super("Brew Day! - Create recipe");
        
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setBounds(200, 200, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertTheName = new JLabel("Set the ingredients needed for the new recipe:");
		panel.add(lblInsertTheName);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Ingredient name", "Quantity"}, 0) {
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
		
		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblModifyTheName = new JLabel("Set the name of the new recipe: ");
		panel2.add(lblModifyTheName);
		
		textField = new JTextField();
		panel2.add(textField);
		textField.setColumns(10);
		panel2.add(btnSave);
		
		JButton btnBack = new JButton("Back");
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
				String name = createName();
				Map<String, Double> ingredients = createIngredients();
				
				if(name != null && ingredients != null) {
					if (table.isEditing())
					    table.getCellEditor().stopCellEditing();
					
					Recipe recipe = new Recipe(createName(),createIngredients());
					
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
	
	private Map<String, Double> createIngredients(){
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
				throw new nullInputException();
			}
			return ingredients;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,"Insert only positive number in quantity field");
			return null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"Insert only string in ingredient name field");
			return null;
		} catch (nullInputException e) {
			JOptionPane.showMessageDialog(this,"Insert at least an ingredient");
			return null;
		}
	}
	
	private String createName(){
		try {
			String name = textField.getText();
			if(name.equals("")) {
				throw new nullInputException();
			}
			else if (!name.matches("[a-zA-Z_]+")) {
				throw new IllegalArgumentException();
			}
			return name;
		} catch (nullInputException e) {
			JOptionPane.showMessageDialog(this,"Insert a string name for the new recipe");
			return null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"Insert only string in recipe name field");
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
