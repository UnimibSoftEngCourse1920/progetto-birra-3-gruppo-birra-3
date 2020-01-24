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
import javax.swing.JOptionPane;
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
	private BrewController brewController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrewWindow frame = new BrewWindow();
					frame.setVisible(true);
					frame.setSize(1600, 700);
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
		setBounds(150, 200, 1600, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		
		
		BrewController brewController = BrewController.getInstance();
		/*
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

<<<<<<< HEAD
		brewController = BrewController.getInstance();
		Brew brew1 = recipe.createBrew();
		brewController.store(brew1);
=======
		//Only for testing purposes
		
		brewController.store(recipe.createBrew());
>>>>>>> branch 'GUI' of https://github.com/UnimibSoftEngCourse1920/progetto-birra-3-gruppo-birra-3.git

		HashMap<String,Double> ingredients3 = new HashMap<>();
		ingredients3.put("Yeast", 10.0); 
		ingredients3.put("Sugar", 20.0); 
		Recipe recipe2 = new Recipe("Recipe2", ingredients3);
		Brew brew2 = recipe2.createBrew();
		brewController.store(brew2);

		brewController.addNote(brew1.getId(), "Note 1", false);
		brewController.addNote(brew1.getId(), "Note 2", true);
		brewController.addNote(brew2.getId(), "Note 2", true);
		//Only for testing purposes
		////////////////////////////////////////////////
		*/


		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("The brews are:");
		label.setFont(new Font(label.getFont().getName(),Font.BOLD, 17));
		panel.add(label);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);

		List<Brew> brews = brewController.extractBrew();

		DefaultTableModel model = new DefaultTableModel(new String[]{"Brew Number","Recipe Number", "Recipe Name","Ingredients","Note's number","Start Date","Finish Date","","","",""}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				switch (column) {
				case 7:
				case 8:
				case 9:
				case 10:
					return true;
				default:
					return false;
				}
			}
		};

		String ingredient;
		for(Brew b : brews) {
			ingredient = "";
			for(Entry<String, Double> i : b.getRecipe().getIngredients().entrySet()) {
				ingredient +=  "  " + i.getKey() + "= " + Double.toString(i.getValue());
			}
			model.addRow(new String[] {Double.toString(b.getId()),Integer.toString(b.getRecipe().getId()),b.getRecipe().getName(),ingredient,Integer.toString(b.getNotes().size()),fromDatetoString(b.getStartDate()),fromDatetoString(b.getFinishDate()),"View Notes","Terminate","Cancel","Delete"});
		}

		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getColumnModel().getColumn(3).setPreferredWidth(450);
		table.getTableHeader().setFont(new Font(table.getFont().getName(), Font.PLAIN, 16));
		table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 13));
		table.setRowHeight(30);

		@SuppressWarnings("unused")
		ButtonColumn viewNotesColumn = new ButtonColumn(table, this, 7);
		@SuppressWarnings("unused")
		ButtonColumn terminateColumn = new ButtonColumn(table, this, 8);
		@SuppressWarnings("unused")
		ButtonColumn cancelColumn = new ButtonColumn(table, this, 9);
		@SuppressWarnings("unused")
		ButtonColumn deleteColumn = new ButtonColumn(table, this, 10);

		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font(btnBack.getFont().getName(),Font.BOLD, 18));
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
		if (e.getActionCommand().equals("Back")) {
			MainWindow.getInstance().setVisible(true);
			dispose();
		} else {
			String[] tokens = e.getActionCommand().split("/");
			Double brewId = Double.parseDouble(tokens[0]);
			String command = tokens[1];
			int row = Integer.parseInt(tokens[2]);

			switch(command) {
			case "View Notes":
				setVisible(false);
				ViewNotesWindow vNoteWindow = new ViewNotesWindow(brewId);
				vNoteWindow.setVisible(true);
				dispose();
				break;
			case "Terminate":
				List<Brew> brews = brewController.extractBrew();

				boolean terminated = false;

				for (Brew b : brews) {
					if (b.getId().compareTo(brewId) == 0) {
						if (b.getFinishDate() != null) terminated = true;
					}
				}
				if (terminated == false) {
					brewController.setFinishDate(brewId);
					JTable table = (JTable)e.getSource();
					((DefaultTableModel)table.getModel()).setValueAt(fromDatetoString(new Date(System.currentTimeMillis())), row, 6);
				} else {
					JOptionPane.showMessageDialog(this,"You can't terminate a terminated brew");
				}
				break;
			case "Cancel":
				List<Brew> brews1 = brewController.extractBrew();

				boolean terminated1 = false;

				for (Brew b : brews1) {
					if (b.getId().compareTo(brewId) == 0) {
						if (b.getFinishDate() != null) terminated = true;
					}
				}

				if (terminated1 == false) {
					brewController.cancel(brewId);
					JTable table1 = (JTable)e.getSource();
					((DefaultTableModel)table1.getModel()).removeRow(row);
				} else {
					JOptionPane.showMessageDialog(this,"You can't cancel a not terminated brew");
				}

				break;
			case "Delete":
				brewController.delete(brewId);
				JTable table2 = (JTable)e.getSource();
				((DefaultTableModel)table2.getModel()).removeRow(row);
			}
		}
	}
}
