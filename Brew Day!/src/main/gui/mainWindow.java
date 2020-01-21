package main.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class mainWindow extends JFrame {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	
	private class EndingListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e)
		{
		   System.exit(0);
		}
	}

	public mainWindow() throws HeadlessException {
	    super();
		setSize(WIDTH, HEIGHT);
		setTitle("Brew Day!");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JButton endButton = new JButton("Click to end program.");
		endButton.addActionListener( new EndingListener());
		add(endButton);
	}
	
	public static void main(String[] args)
	{
		mainWindow gui = new mainWindow();
		gui.setVisible( true);
	}
}
