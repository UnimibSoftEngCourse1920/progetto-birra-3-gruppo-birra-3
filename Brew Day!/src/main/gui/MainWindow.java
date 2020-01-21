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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	
	private class recipeListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e)
		{}
	}
	
	private class brewListener implements ActionListener {
			
		public void actionPerformed(ActionEvent e)
		{}
	}
	
	private class equipmentListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e)
		{}
	}
	
	private class storageListener implements ActionListener {
			
		public void actionPerformed(ActionEvent e)
		{}
	}

	public MainWindow() throws HeadlessException {
	    super("Brew Day!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 4));
		
		JPanel recipePanel = new JPanel();
		recipePanel.setBackground(Color.CYAN);
		recipePanel.setLayout(new GridLayout(2, 0));
		mainPanel.add(recipePanel);
		
		JPanel brewPanel = new JPanel();
		brewPanel.setBackground(Color.GREEN);
		brewPanel.setLayout(new GridLayout(2, 0));
		mainPanel.add(brewPanel);
		
		JPanel equipmentPanel = new JPanel();
		equipmentPanel.setBackground(Color.YELLOW);
		equipmentPanel.setLayout(new GridLayout(2, 0));
		mainPanel.add(equipmentPanel);
		
		JPanel storagePanel = new JPanel();
		storagePanel.setLayout(new GridLayout(2, 0));
		storagePanel.setBackground(Color.RED);
		mainPanel.add(storagePanel);
		
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
		
		add(mainPanel, BorderLayout.CENTER);
		
		Dimension d = new Dimension(200, 70);
		JButton recipeButton = new JButton("Recipe menu");
		recipeButton.addActionListener(new recipeListener());
		recipeButton.setPreferredSize(d);
		recipeButtonPanel.add(recipeButton, BorderLayout.CENTER);
		JButton brewButton = new JButton("Brew menu");
		brewButton.addActionListener( new brewListener());
		brewButton.setPreferredSize(d);
		brewButtonPanel.add(brewButton, BorderLayout.CENTER);
		JButton equipmentButton = new JButton("Equipment menu");
		equipmentButton.addActionListener(new equipmentListener());
		equipmentButton.setPreferredSize(d);
		equipmentButtonPanel.add(equipmentButton, BorderLayout.CENTER);
		JButton storageButton = new JButton("Storage menu");
		storageButton.addActionListener(new storageListener());
		storageButton.setPreferredSize(d);
		storageButtonPanel.add(storageButton, BorderLayout.CENTER);
	}
	
	public static void main(String[] args)
	{
		MainWindow gui = new MainWindow();
		gui.setVisible( true);
	}
}
