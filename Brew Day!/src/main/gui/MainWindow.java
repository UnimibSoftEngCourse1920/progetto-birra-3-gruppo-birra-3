package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import main.instrument.EquipmentController;
import main.recipes.Brew;
import main.recipes.BrewController;
import main.recipes.Recipe;
import main.recipes.RecipeController;
import main.resources.StorageController;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener{

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static MainWindow instance;

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "View your recipes":
			    setVisible(false);
				new RecipeWindow().setVisible(true);
			    break;
			case "View your brews":
				setVisible(false);
				new BrewWindow().setVisible(true);
				break;
			case "View your equipment":
				setVisible(false);
				new EquipmentWindow().setVisible(true);
				break;
			case "View your storage":
				setVisible(false);
				new StorageWindow().setVisible(true);
			default:;
		}
	}

	private MainWindow() {
		super("Brew Day!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 0));

		JPanel resourcesPanel = new JPanel();
		resourcesPanel.setLayout(new GridLayout(1, 4));

		JPanel wsibtPanel = new JPanel();
		wsibtPanel.setBackground(Color.ORANGE);
		wsibtPanel.setLayout(new GridLayout(2, 0));

		JPanel recipePanel = new JPanel();
		recipePanel.setBackground(Color.CYAN);
		recipePanel.setLayout(new GridLayout(2, 0));
		resourcesPanel.add(recipePanel);

		JPanel brewPanel = new JPanel();
		brewPanel.setBackground(Color.GREEN);
		brewPanel.setLayout(new GridLayout(2, 0));
		resourcesPanel.add(brewPanel);

		JPanel equipmentPanel = new JPanel();
		equipmentPanel.setBackground(Color.YELLOW);
		equipmentPanel.setLayout(new GridLayout(2, 0));
		resourcesPanel.add(equipmentPanel);

		JPanel storagePanel = new JPanel();
		storagePanel.setLayout(new GridLayout(2, 0));
		storagePanel.setBackground(Color.RED);
		resourcesPanel.add(storagePanel);

		Font f = new Font("TimesRoman",Font.BOLD,25);
		JLabel recipeLabel = new JLabel("Recipes");
		recipeLabel.setFont(f);
		recipeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel brewLabel = new JLabel("Brews");
		brewLabel.setFont(f);
		brewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel equipmentLabel = new JLabel("Equipment");
		equipmentLabel.setFont(f);
		equipmentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel storageLabel = new JLabel("Storage");
		storageLabel.setFont(f);
		storageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recipePanel.add(recipeLabel);
		brewPanel.add(brewLabel);
		equipmentPanel.add(equipmentLabel);
		storagePanel.add(storageLabel);

		JPanel recipeButtonPanel = new JPanel();
		recipeButtonPanel.setLayout(new FlowLayout());
		recipeButtonPanel.setBackground(Color.CYAN);
		recipePanel.add(recipeButtonPanel);

		JPanel brewButtonPanel = new JPanel();
		brewButtonPanel.setLayout(new FlowLayout());
		brewButtonPanel.setBackground(Color.GREEN);
		brewPanel.add(brewButtonPanel);

		JPanel equipmentButtonPanel = new JPanel();
		equipmentButtonPanel.setLayout(new FlowLayout());
		equipmentButtonPanel.setBackground(Color.YELLOW);
		equipmentPanel.add(equipmentButtonPanel);

		JPanel storageButtonPanel = new JPanel();
		storageButtonPanel.setLayout(new FlowLayout());
		storageButtonPanel.setBackground(Color.RED);
		storagePanel.add(storageButtonPanel);

		getContentPane().add(mainPanel);
		mainPanel.add(resourcesPanel);
		mainPanel.add(wsibtPanel);

		Dimension d = new Dimension(200, 70);
		
		JButton recipeButton = new JButton("View your recipes");
		recipeButton.addActionListener(this);
		recipeButton.setPreferredSize(d);
		recipeButtonPanel.add(recipeButton);
		
		JButton brewButton = new JButton("View your brews");
		brewButton.addActionListener(this);
		brewButton.setPreferredSize(d);
		brewButtonPanel.add(brewButton);
		
		JButton equipmentButton = new JButton("View your equipment");
		equipmentButton.addActionListener(this);
		equipmentButton.setPreferredSize(d);
		equipmentButtonPanel.add(equipmentButton);
		
		JButton storageButton = new JButton("View your storage");
		storageButton.addActionListener(this);
		storageButton.setPreferredSize(d);
		storageButtonPanel.add(storageButton);

		String filepathRecipe = System.getProperty("user.dir") + "\\src\\Files\\Recipe.txt";
		File fileRecipe = new File(filepathRecipe);
		String wsibtRecipe = null;
		if(fileRecipe.exists()) {
			RecipeController recipeController = RecipeController.getInstance();
			int size = recipeController.extractRecipe().size();
			if(size != 0) {
				wsibtRecipe = getWSIBT().getName();
			}
		}else {
			wsibtRecipe = "Nessuna recipe salvata!";
		}
			
		JLabel wsibtLabel = new JLabel ("What should I brew today? " + wsibtRecipe);
		wsibtLabel.setFont(f);
		wsibtLabel.setHorizontalAlignment(JLabel.CENTER);
		wsibtPanel.add(wsibtLabel);


		JPanel wsibtButtonPanel = new JPanel();
		wsibtButtonPanel.setBackground(Color.ORANGE);
		wsibtButtonPanel.setLayout(new FlowLayout());
		wsibtPanel.add(wsibtButtonPanel);

		JButton wsibtButton = new JButton("Brew it!");
		wsibtButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecipeController recipeController = RecipeController.getInstance();
				Recipe wsibtRecipe = getWSIBT();
				recipeController.createBrew(wsibtRecipe.getId());
				
				BrewWindow brewWin = new BrewWindow();
				brewWin.setVisible(true);
				dispose();
			}
		});
		wsibtButton.setPreferredSize(d);
		wsibtButtonPanel.add(wsibtButton);
	}

	public Recipe getWSIBT() {
		RecipeController recipeController = RecipeController.getInstance();
		return recipeController.featureWSIBT();
	}

	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}

		return instance;
	}

	public static void main(String[] args)
	{
		//Only for testing purposes
		Map<String, Double> instruments = new HashMap<>();
		instruments.put("Kettle", 25.0);
		instruments.put("Fermenter", 10.0);	
		EquipmentController equipmentController = EquipmentController.getInstance();
		equipmentController.createEquipment(instruments);
		
		//create storage
		Map<String,Double> ingredients = new HashMap<>();
	    ingredients.put("Malt", 1000.0);
		ingredients.put("Yeast", 35.0);
		ingredients.put("Hop", 189.0);
		ingredients.put("Sugar", 50.0);
		StorageController storageController = StorageController.getInstance();
		storageController.createStorage(ingredients);
	
		//create recipe1
		RecipeController recipeController = RecipeController.getInstance();
		HashMap<String,Double> ingredients2 = new HashMap<>();
		ingredients2.put("Malt", 10.0); 
		ingredients2.put("Hop", 20.0); 
		Recipe recipe1 = new Recipe("Recipe1", ingredients2);
		recipeController.store(recipe1);
				
		//create recipe2
		HashMap<String,Double> ingredients3 = new HashMap<>();
	    ingredients3.put("Malt", 100.0); 
		ingredients3.put("Yeast", 25.0);
		Recipe recipe2 = new Recipe("Recipe2", ingredients3);
		recipeController.store(recipe2);
				
		//create recipe3
		HashMap<String,Double> ingredients4 = new HashMap<>();
		ingredients4.put("Yeast", 10.0); 
		ingredients4.put("Sugar", 20.0); 
		Recipe recipe3 = new Recipe("Recipe3", ingredients4);
		recipeController.store(recipe3);
				
		//create brew from recipe1
		BrewController brewController = BrewController.getInstance();
		
		brewController.deleteFile();
		
		Brew brew1 = recipe1.createBrew();
		brewController.store(brew1);
				
		//create brew from recipe2
		Brew brew2 = recipe2.createBrew();
		brewController.store(brew2);
		
		brewController.addNote(brew1.getId(), "Note 1", false);
		brewController.addNote(brew1.getId(), "Note 2", true);
		brewController.addNote(brew2.getId(), "Note 2", true);
				
		//Only for testing purpose
		MainWindow gui = getInstance();
		gui.setVisible(true);
				
		//Only for testing purposes
		//recipeController.deleteFile();
	}
}
