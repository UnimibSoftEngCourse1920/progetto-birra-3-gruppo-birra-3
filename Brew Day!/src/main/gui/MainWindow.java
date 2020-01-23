package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
				//setVisible(false);
				//EquipmentWindow.getInstance().setVisible(true);
				break;
			case "View your storage":
				//setVisible(false);
				//StorageWindow.getInstance().setVisible(true);
		}
	}

	private MainWindow() throws HeadlessException {
	    super("Brew Day!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
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
		recipeLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel brewLabel = new JLabel("Brews");
		brewLabel.setFont(f);
		brewLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel equipmentLabel = new JLabel("Equipment");
		equipmentLabel.setFont(f);
		equipmentLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel storageLabel = new JLabel("Storage");
		storageLabel.setFont(f);
		storageLabel.setHorizontalAlignment(JLabel.CENTER);
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
		
		add(mainPanel);
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
		
		Recipe wsibtRecipe = getWSIBT();
		JLabel wsibtLabel = new JLabel("What should I brew today? " + wsibtRecipe.getName());
		wsibtLabel.setFont(f);
		wsibtLabel.setHorizontalAlignment(JLabel.CENTER);
		wsibtPanel.add(wsibtLabel);
		
		JPanel wsibtButtonPanel = new JPanel();
		wsibtButtonPanel.setBackground(Color.ORANGE);
		wsibtButtonPanel.setLayout(new FlowLayout());
		wsibtPanel.add(wsibtButtonPanel);
		
		JButton wsibtButton = new JButton("Brew it!");
		//wsibtButton.addActionListener(new createBrewListener(wsibtRecipe));
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
		
		MainWindow gui = new MainWindow();
		gui.setVisible( true);
		
		//Only for testing purposes
		recipeController.deleteFile();
		storageController.deleteFile();
		//Only for testing purposes
	}
}
