package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RecipeWindow extends JFrame implements ActionListener{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static RecipeWindow instance;
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Back":
				setVisible(false);
				MainWindow.getInstance().setVisible(true);
				break;
		}
	}

	private RecipeWindow(){
		super("Brew Day!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 0));
		mainPanel.setBackground(Color.CYAN);
		
		Font f = new Font("TimesRoman",Font.BOLD,25);
		JLabel titleLabel = new JLabel("Recipes menu");
		titleLabel.setFont(f);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(titleLabel);
		
		JPanel viewRecipesPanel = new JPanel();
		viewRecipesPanel.setLayout(new FlowLayout());
		viewRecipesPanel.setBackground(Color.CYAN);
		mainPanel.add(viewRecipesPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		
		Dimension d = new Dimension(200, 70);
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setPreferredSize(d);
		mainPanel.add(backButton, BorderLayout.SOUTH);
		
		JButton viewRecipesButton = new JButton("Recipe menu");
		//viewRecipesButton.addActionListener(new viewRecipesListener());
		viewRecipesButton.setPreferredSize(d);
		viewRecipesPanel.add(viewRecipesButton, BorderLayout.CENTER);
	}
	
	public static RecipeWindow getInstance() {
		if (instance == null) {
			instance = new RecipeWindow();
		}

		return instance;
	}
	
	public static void main(String[] args){
		RecipeWindow gui = new RecipeWindow();
		gui.setVisible( true);
	}

}
