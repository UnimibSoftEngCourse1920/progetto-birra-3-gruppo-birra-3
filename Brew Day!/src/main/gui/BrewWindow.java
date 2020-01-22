package main.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class BrewWindow extends JFrame {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static BrewWindow instance;

	private BrewWindow(){
		super("Brew Day!");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	}
	
	public static BrewWindow getInstance() {
		if (instance == null) {
			instance = new BrewWindow();
		}

		return instance;
	}
	
	public static void main(String[] args){
		BrewWindow gui = new BrewWindow();
		gui.setVisible( true);
	}
}
