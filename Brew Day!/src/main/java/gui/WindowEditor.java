package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.java.gui.recipes.NullInputException;
import main.java.instruments.EquipmentController;

/**
 *  The WindoEditor class provides initialization 
 *  and components creation methods to the JFrame classes (that represent 
 *  the GUI windows) to avoid duplicated code
 */
public class WindowEditor {
	
	public static final Font plainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	public static final Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);

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
	
	public static Map<String, Double> initializeSMEquipmentWindow(JPanel contentPane, Color color, String label) {
		WindowEditor.initializeWindow(contentPane, color, label);
		
		Map<String, Double> instruments;
		EquipmentController equipmentController = EquipmentController.getInstance();
		instruments = equipmentController.extractEquipment().getInstruments();
		
		return instruments;
	}
	
	public static void initializeWindow(JPanel contentPane, Color color, String label) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(color);
		contentPane.add(panel1, BorderLayout.CENTER);
		
		JLabel lbl = new JLabel(label);
		lbl.setFont(boldFont);
		panel.add(lbl);
	}
	
	public static JTable createTable(DefaultTableModel model, JFrame window, Color color, int height) {
		JPanel contentPane = (JPanel) window.getContentPane();
		
		JTable table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(height);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(color);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		return table;
	}
	
	public static void back(JFrame window, JFrame backWindow) {
		backWindow.setVisible(true);
		window.dispose();
	}
	
	public static JPanel createBackAndOther(JPanel panel, JPanel contentPane, JFrame window, Color color, String otherLabel) {
		if(panel == null) {
			panel = new JPanel();
			panel.setBackground(color);
			contentPane.add(panel, BorderLayout.SOUTH);
		}
		
		JButton btn = new JButton(otherLabel);
		btn.addActionListener((ActionListener) window);
		btn.setFont(WindowEditor.boldFont);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(btn);
		
		createBack(panel,contentPane,window,color);
		
		return panel;
	}
	
	public static JPanel createBack(JPanel panel, JPanel contentPane, JFrame window, Color color) {
		if(panel == null) {
			panel = new JPanel();
			panel.setBackground(color);
			contentPane.add(panel, BorderLayout.SOUTH);
		}
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(WindowEditor.boldFont);
		btnBack.addActionListener((ActionListener) window);
		panel.add(btnBack); 
		
		return panel;
	}
	
	public static JTextField createTextField(JFrame window, String label, Color color) {
		JPanel panel2 = new JPanel();
		panel2.setBackground(color);
		window.getContentPane().add(panel2, BorderLayout.SOUTH);
		
		JLabel lbl = new JLabel(label);
		lbl.setFont(WindowEditor.boldFont);
		panel2.add(lbl);
		
		JTextField textField = new JTextField();
		textField.setFont(WindowEditor.plainFont);
		panel2.add(textField);
		textField.setColumns(10);
		
		return textField;
	}
	
	public static JPanel createGridBagPanel(JPanel contentPane, Color color, int[] columnWidths, int[] rowHeights, double[] columnWeights, double[] rowWeights) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gblPanel = new GridBagLayout();
		gblPanel.columnWidths = columnWidths;
		gblPanel.rowHeights = rowHeights;
		gblPanel.columnWeights = columnWeights;
		gblPanel.rowWeights = rowWeights;
		panel.setLayout(gblPanel);
		
		return panel;
	}
	
	public static JButton createGridBagButton(String label, JFrame window, JPanel panel, int y, boolean fill) {
		JButton btn = new JButton(label);
		btn.setFont(boldFont);
		btn.addActionListener((ActionListener) window);
		
		GridBagConstraints gbcBtn = new GridBagConstraints();
		if(fill) {
			gbcBtn.fill = GridBagConstraints.BOTH;
		}
		gbcBtn.insets = new Insets(0, 0, 5, 0);
		gbcBtn.gridx = 0;
		gbcBtn.gridy = y;
		panel.add(btn, gbcBtn);
		
		return btn;
	}
	
	public static JLabel createGridBagLabel(String label, JPanel panel, int y) {
		JLabel lbl = new JLabel(label);
		lbl.setFont(WindowEditor.plainFont);
		GridBagConstraints gbcLbl = new GridBagConstraints();
		gbcLbl.fill = GridBagConstraints.VERTICAL;
		gbcLbl.insets = new Insets(0, 0, 5, 0);
		gbcLbl.gridx = 0;
		gbcLbl.gridy = y;
		panel.add(lbl, gbcLbl);
		
		return lbl;
	}
	
	public static JTextField createGridBagTextField(JPanel panel, int y, boolean fill) {
		JTextField textField = new JTextField();
		textField.setFont(WindowEditor.plainFont);
		textField.setColumns(10);
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.insets = new Insets(0, 0, 5, 0);
		if(fill) {
			gbcTextField.fill = GridBagConstraints.BOTH;
		}
		gbcTextField.gridx = 0;
		gbcTextField.gridy = y;
		panel.add(textField, gbcTextField);
		
		return textField;
	}
	
	/*
	 * Returns the ingredients map using the data inserted in the given table 
	 * located in the given window. Shows an alert message and returns null if the input format
	 * is not correct or if nothing has been inserted
	 */
	public static Map<String, Double> createIngredients(JFrame window,JTable table){
		try {
			Map<String, Double> ingredients = new HashMap<>();
			
			for (int i = 0; i < table.getRowCount(); i++) {
				String ingredientName = table.getValueAt(i, 0).toString();
				String ingredientQuantity = table.getValueAt(i, 1).toString();
				if (!ingredientName.matches("[a-zA-Z_]+")) {
					throw new IllegalArgumentException();
				}
				else if(!(ingredientQuantity.equals("0.0") || ingredientQuantity.equals("0") || ingredientQuantity.equals(""))) {
					ingredients.put(table.getValueAt(i, 0).toString(), fromStringToDouble(ingredientQuantity));
				}
			}
			if(ingredients.isEmpty()) {
				throw new NullInputException();
			}
			return ingredients;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(window,"Insert only positive numbers in quantity field, separated by dot (e.g. Sugar 10.50)");
			return null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(window,"Insert only string in ingredient name field");
			return null;
		} catch (NullInputException e) {
			JOptionPane.showMessageDialog(window,"Insert at least an ingredient");
			return null;
		}
	}
	
	/*
	 * Parses the given string str into a Double and returns it.
	 * Throws a NumberFormatException if the number is negative
	 */
	public static double fromStringToDouble(String str) {
		if (str.contains("-")) {
			throw new NumberFormatException();
		} 
		return Double.parseDouble(str);
	}
	
	private WindowEditor() {
		super();
	}
}
