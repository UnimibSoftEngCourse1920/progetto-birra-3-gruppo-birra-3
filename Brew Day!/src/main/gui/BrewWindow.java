package main.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.recipes.Brew;
import main.recipes.BrewController;
import main.recipes.Recipe;
import main.recipes.RecipeController;
import main.resources.StorageController;

@SuppressWarnings("serial")
public class BrewWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrewWindow frame = new BrewWindow();
					frame.setVisible(true);
					frame.setSize(1600, 900);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BrewWindow() {
		super("Brew Day! - Brews");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1600, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		//////////////////////////////////////////////
		//Only for testing purposes
		Map<String,Double> ingredients = new HashMap<String, Double>();
		ingredients.put("Malt", 10.0);
		ingredients.put("Yeast", 35.0);
		ingredients.put("Hop", 189.0);
		ingredients.put("Sugar", 50.0);
		StorageController storageController = StorageController.getInstance();
		storageController.createStorage(ingredients);

		RecipeController recipeController = RecipeController.getInstance();
		HashMap<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Malt", 10.0); 
		ingredients2.put("Hop", 20.0); 
		Recipe recipe = new Recipe("Test Recipe", ingredients2);
		recipeController.store(recipe);

		//Only for testing purposes
		BrewController brewController = BrewController.getInstance();
		brewController.store(recipe.createBrew());

		HashMap<String,Double> ingredients3 = new HashMap<>();
		ingredients3.put("Yeast", 10.0); 
		ingredients3.put("Sugar", 20.0); 
		Recipe recipe2 = new Recipe("Recipe2", ingredients3);
		Brew brew2 = recipe2.createBrew();
		brewController.store(brew2);
		//Only for testing purposes
		////////////////////////////////////////////////



		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("The brews are:");
		label.setFont(new Font(label.getFont().getName(),Font.BOLD, 17));
		panel.add(label);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);

		List<Brew> brews = brewController.extractBrew();
		
		brewController.deleteFile();

		DefaultTableModel model = new DefaultTableModel(new String[]{"Brew Number","Recipe Number", "Recipe Name","Ingredients","Note's number","Start Date","Finish Date","","",""}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				switch (column) {
				case 7:
				case 8:
				case 9:
					return true;
				default:
					return false;
				}
			}
		};

		String ingredient;
		for(Brew r : brews) {
			ingredient = "";
			for(Entry<String, Double> i : r.getRecipe().getIngredients().entrySet()) {
				ingredient +=  "  " + i.getKey() + "= " + Double.toString(i.getValue());
			}
			model.addRow(new String[] {Double.toString(r.getId()),Integer.toString(r.getRecipe().getId()),r.getRecipe().getName(),ingredient,Integer.toString(r.getNotes().size()),fromDatetoString(r.getStartDate()),fromDatetoString(r.getFinishDate()),"View Notes","Terminate","Delete"});
		}

		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font(table.getFont().getName(), Font.PLAIN, 16));
		table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 13));
		table.setRowHeight(30);
		
		@SuppressWarnings("unused")
		ButtonColumn viewNotesColumn = new ButtonColumn(table, this, 7);
		@SuppressWarnings("unused")
		ButtonColumn terminateColumn = new ButtonColumn(table, this, 8);
		@SuppressWarnings("unused")
		ButtonColumn deleteColumn = new ButtonColumn(table, this, 9);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font(btnBack.getFont().getName(),Font.BOLD, 17));
		btnBack.addActionListener(this);
		panel_2.add(btnBack);
	}

	public static String fromDatetoString(Date date) {
		if (date == null) {
			return "";
		}

		String pattern = "dd/MM/yyyy";

		DateFormat df = new SimpleDateFormat(pattern);

		return df.format(date);
	}

	public void actionPerformed(ActionEvent e) {
		MainWindow.getInstance().setVisible(true);
		dispose();
	}
}
