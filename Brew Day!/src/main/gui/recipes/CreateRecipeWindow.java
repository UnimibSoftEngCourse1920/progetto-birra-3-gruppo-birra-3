package main.gui.recipes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
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
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class CreateRecipeWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;

	public CreateRecipeWindow(){
		super("Brew Day! - Create recipe");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setExtendedState(Frame.MAXIMIZED_BOTH); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) (screenSize.width/1.5), (int) (screenSize.height/1.5));
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(189, 216, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Font plainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(189, 216, 255));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblInsertTheName = new JLabel("Set the ingredients needed for the new recipe:");
		lblInsertTheName.setFont(boldFont);
		panel.add(lblInsertTheName);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(189, 216, 255));
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
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(30);
		
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
		
		JLabel lblModifyTheName = new JLabel("Set the name of the new recipe: ");
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
			if (table.isEditing())
			    table.getCellEditor().stopCellEditing();
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
			JOptionPane.showMessageDialog(this,"Insert only positive number in quantity field");
			return null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this,"Insert only string in ingredient name field");
			return null;
		} catch (NullInputException e) {
			JOptionPane.showMessageDialog(this,"Insert at least an ingredient");
			return null;
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
	
	private double fromStringToDouble(String str) {
		if (str.contains("-")) {
			throw new NumberFormatException();
		} 
		return Double.parseDouble(str);
	}
}
