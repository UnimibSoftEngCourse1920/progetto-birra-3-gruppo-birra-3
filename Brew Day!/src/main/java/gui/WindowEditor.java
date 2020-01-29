package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class WindowEditor {

	public static JPanel showWindow(JFrame window, Color color) {
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setExtendedState(Frame.MAXIMIZED_BOTH); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize((int) (screenSize.width/1.5), (int) (screenSize.height/1.5));

		JPanel contentPane = new JPanel();
		contentPane.setBackground(color);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		if(color != null) {
			contentPane.setBackground(color);
		}
		window.setContentPane(contentPane);
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		window.setIconImage(icon.getImage());
		
		window.setVisible(true);
		
		return contentPane;
	}
}
