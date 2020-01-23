package main.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class CreateBrewWindow extends JFrame {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	int recipeId;

	public CreateBrewWindow(int id){
		super("Brew Day!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		recipeId = id;
	}
	
	public static void main(String[] args){
		CreateBrewWindow gui = new CreateBrewWindow(1);
		gui.setVisible( true);
	}
}