package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	
	private class viewRecipesListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e)
		{}
	}
	
	private class viewBrewsListener implements ActionListener {
			
		public void actionPerformed(ActionEvent e)
		{}
	}
	
	private class viewEquipmentListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e)
		{}
	}
	
	private class viewStorageListener implements ActionListener {
			
		public void actionPerformed(ActionEvent e)
		{}
	}

	public MainWindow() throws HeadlessException {
	    super("Brew Day!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout( new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 4));
		
		JPanel recipePanel = new JPanel();
		recipePanel.setBackground(Color.CYAN);
		recipePanel.setLayout(new GridLayout(5, 0));
		mainPanel.add(recipePanel);
		
		JPanel brewPanel = new JPanel();
		brewPanel.setBackground(Color.GREEN);
		brewPanel.setLayout(new GridLayout(5, 0));
		mainPanel.add(brewPanel);
		
		JPanel equipmentPanel = new JPanel();
		equipmentPanel.setBackground(Color.YELLOW);
		equipmentPanel.setLayout(new GridLayout(5, 0));
		mainPanel.add(equipmentPanel);
		
		JPanel storagePanel = new JPanel();
		storagePanel.setBackground(Color.RED);
		storagePanel.setLayout(new GridLayout(5, 0));
		mainPanel.add(storagePanel);
		
		add(mainPanel, BorderLayout.CENTER);
		
		Font f = new Font("TimesRoman",Font.BOLD,25);
		JLabel recipeLabel = new JLabel("Recipes");
		recipeLabel.setFont(f);
		recipeLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel brewLabel = new JLabel("Brew");
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
		
		JButton viewRecipesButton = new JButton("View your recipes");
	    viewRecipesButton.addActionListener( new viewRecipesListener());
	    viewRecipesButton.setPreferredSize(new Dimension(40, 40));
		recipePanel.add(viewRecipesButton);
		JButton viewBrewsButton = new JButton("View your brews");
		viewBrewsButton.addActionListener( new viewBrewsListener());
		viewBrewsButton.setPreferredSize(new Dimension(40, 40));
		brewPanel.add(viewBrewsButton);
		JButton viewEquipmentButton = new JButton("View your equipment");
		viewEquipmentButton.addActionListener( new viewEquipmentListener());
		viewEquipmentButton.setPreferredSize(new Dimension(40, 40));
		equipmentPanel.add(viewEquipmentButton);
		JButton viewStorageButton = new JButton("View your storage");
		viewStorageButton.addActionListener( new viewStorageListener());
		viewStorageButton.setPreferredSize(new Dimension(40, 40));
		storagePanel.add(viewStorageButton);
	}
	
	public static void main(String[] args)
	{
		MainWindow gui = new MainWindow();
		gui.setVisible( true);
	}
}
