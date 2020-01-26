package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import main.gui.instruments.EquipmentWindow;
import main.gui.recipes.BrewWindow;
import main.gui.recipes.RecipeWindow;
import main.gui.resources.StorageWindow;
import main.instruments.EquipmentController;
import main.recipes.BrewController;
import main.recipes.Recipe;
import main.recipes.RecipeController;
import main.resources.Storage;
import main.resources.StorageController;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener{

	private boolean brewIt = false;
	RecipeController recipeController;
	String userDir = "user.dir";

	public MainWindow() {
		super("Brew Day!");
		setExtendedState(Frame.MAXIMIZED_BOTH); 
		setVisible(true);
		
		ImageIcon icon = new ImageIcon(System.getProperty(userDir) + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
		Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 17);
		
		StorageController sController = StorageController.getInstance();
		File f = new File(System.getProperty(userDir) + "\\src\\Files\\Storage.txt");
		if(!f.exists()) {
			Map<String,Double> defaultIngredients = new HashMap<>();
			defaultIngredients.put("Malt", 0.0);
			defaultIngredients.put("Hop", 0.0);
			defaultIngredients.put("Yeast", 0.0);
			defaultIngredients.put("Sugar", 0.0);
			defaultIngredients.put("Additive", 0.0);
			sController.createStorage(defaultIngredients);
		}else {
			Map<String,Double> storageIngredients = sController.extractStorage().getIngredients();
			sController.createStorage(storageIngredients);
		}

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 0));

		JPanel resourcesPanel = new JPanel();
		resourcesPanel.setLayout(new GridLayout(1, 4));

		JPanel wsibtPanel = new JPanel();
		wsibtPanel.setBackground(new Color(255, 200, 174));
		wsibtPanel.setLayout(new GridLayout(2, 0));

		JPanel recipePanel = new JPanel();
		recipePanel.setBackground(new Color(189, 216, 255));
		recipePanel.setLayout(new GridLayout(2, 0));
		resourcesPanel.add(recipePanel);

		JPanel brewPanel = new JPanel();
		brewPanel.setBackground(new Color(189, 255, 178));
		brewPanel.setLayout(new GridLayout(2, 0));
		resourcesPanel.add(brewPanel);

		JPanel equipmentPanel = new JPanel();
		equipmentPanel.setBackground(new Color(252, 255, 166));
		equipmentPanel.setLayout(new GridLayout(2, 0));
		resourcesPanel.add(equipmentPanel);

		JPanel storagePanel = new JPanel();
		storagePanel.setLayout(new GridLayout(2, 0));
		storagePanel.setBackground(new Color(255, 154, 162));
		resourcesPanel.add(storagePanel);

		JLabel recipeLabel = new JLabel("Recipes");
		recipeLabel.setFont(labelFont);
		recipeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel brewLabel = new JLabel("Brews");
		brewLabel.setFont(labelFont);
		brewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel equipmentLabel = new JLabel("Equipment");
		equipmentLabel.setFont(labelFont);
		equipmentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel storageLabel = new JLabel("Storage");
		storageLabel.setFont(labelFont);
		storageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recipePanel.add(recipeLabel);
		brewPanel.add(brewLabel);
		equipmentPanel.add(equipmentLabel);
		storagePanel.add(storageLabel);

		JPanel recipeButtonPanel = new JPanel();
		recipeButtonPanel.setLayout(new FlowLayout());
		recipeButtonPanel.setBackground(new Color(189, 216, 255));
		recipePanel.add(recipeButtonPanel);

		JPanel brewButtonPanel = new JPanel();
		brewButtonPanel.setLayout(new FlowLayout());
		brewButtonPanel.setBackground(new Color(189, 255, 178));
		brewPanel.add(brewButtonPanel);

		JPanel equipmentButtonPanel = new JPanel();
		equipmentButtonPanel.setLayout(new FlowLayout());
		equipmentButtonPanel.setBackground(new Color(252, 255, 166));
		equipmentPanel.add(equipmentButtonPanel);

		JPanel storageButtonPanel = new JPanel();
		storageButtonPanel.setLayout(new FlowLayout());
		storageButtonPanel.setBackground(new Color(255, 154, 162));
		storagePanel.add(storageButtonPanel);

		getContentPane().add(mainPanel);
		mainPanel.add(resourcesPanel);
		mainPanel.add(wsibtPanel);

		Dimension d = new Dimension(200, 70);

		JButton recipeButton = new JButton("View recipes");
		recipeButton.setFont(buttonFont);
		recipeButton.addActionListener(this);
		recipeButton.setPreferredSize(d);
		recipeButtonPanel.add(recipeButton);

		JButton brewButton = new JButton("View brews");
		brewButton.setFont(buttonFont);
		brewButton.addActionListener(this);
		brewButton.setPreferredSize(d);
		brewButtonPanel.add(brewButton);

		JButton equipmentButton = new JButton("View equipment");
		equipmentButton.setFont(buttonFont);
		equipmentButton.addActionListener(this);
		equipmentButton.setPreferredSize(d);
		equipmentButtonPanel.add(equipmentButton);

		JButton storageButton = new JButton("View storage");
		storageButton.setFont(buttonFont);
		storageButton.addActionListener(this);
		storageButton.setPreferredSize(d);
		storageButtonPanel.add(storageButton);

		String filepathRecipe = System.getProperty(userDir) + "\\src\\Files\\Recipe.txt";
		File fileRecipe = new File(filepathRecipe);
		String filepathStorage = System.getProperty(userDir) + "\\src\\Files\\Storage.txt";
		File fileStorage = new File(filepathStorage);
		recipeController = RecipeController.getInstance();
		
		String wsibtRecipe = null;
		if(fileRecipe.exists() && fileStorage.exists()) {
			int sizeR = recipeController.extractRecipe().size();
			int sizeS = Storage.getInstance().getIngredients().size();
			if(sizeR != 0 && sizeS != 0) {
				Recipe r = getWSIBT();
				if(r != null) {
					wsibtRecipe = r.getName();
					brewIt = true;
				}else {
					wsibtRecipe = "No recipe match the availability";
				}
			}
			else { 
				wsibtRecipe = "None, you have no recipes saved or no storage ingredients!";
			}
		}else{
			wsibtRecipe = "None, you have no recipes or no storage!";
		}

		JLabel wsibtLabel = new JLabel ("What should I brew today? " + wsibtRecipe);
		wsibtLabel.setFont(labelFont);
		wsibtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wsibtPanel.add(wsibtLabel);

		JPanel wsibtButtonPanel = new JPanel();
		wsibtButtonPanel.setBackground(new Color(255, 200, 174));
		wsibtButtonPanel.setLayout(new FlowLayout());
		wsibtPanel.add(wsibtButtonPanel);

		JButton wsibtButton = new JButton("Brew it!");
		wsibtButton.setFont(buttonFont);
		wsibtButton.addActionListener(this);
		wsibtButton.setPreferredSize(d);
		wsibtButtonPanel.add(wsibtButton);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		
		helpMenu.add(resetButton);
		
		
	}

	public Recipe getWSIBT() {
		RecipeController recipeController = RecipeController.getInstance();
		return recipeController.featureWSIBT();
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "View recipes":
			setVisible(false);
			new RecipeWindow().setVisible(true);
			break;
		case "View brews":
			File f = new File(System.getProperty(userDir) + "\\src\\Files\\Brew.txt");

			if (!f.exists() || BrewController.getInstance().extractBrew().isEmpty()) {
				JOptionPane.showMessageDialog(this, "No brews to show");
			} else {
				setVisible(false);
				new BrewWindow().setVisible(true);
				dispose();
			}
			break;
		case "View equipment":
			setVisible(false);
			new EquipmentWindow().setVisible(true);
			break;
		case "View storage":
			setVisible(false);
			new StorageWindow().setVisible(true);
			break;
		case "Brew it!":
			if(brewIt) {
				RecipeController recipeController = RecipeController.getInstance();
				Recipe wsibtRecipe = getWSIBT();
				recipeController.createBrew(wsibtRecipe.getId());

				BrewWindow brewWin = new BrewWindow();
				brewWin.setVisible(true);
				dispose();
			}
			else
				JOptionPane.showMessageDialog(this, "You can't Brew it!");
			break;
		case "Reset":
			recipeController.deleteFile();
			BrewController.getInstance().deleteFile();
			StorageController.getInstance().deleteFile();
			EquipmentController.getInstance().deleteFile();
			try (FileOutputStream fos = new FileOutputStream(System.getProperty(userDir) + "\\src\\Files\\CounterId.txt");
				DataOutputStream dos = new DataOutputStream(fos)) {
				dos.writeInt(0); 
			} catch (IOException ioe) {
				System.out.println("IOException : " + ioe);
			}
		default:
		}
	}
}
