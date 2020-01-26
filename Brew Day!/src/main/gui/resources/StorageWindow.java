package main.gui.resources;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.gui.MainWindow;
import main.resources.*;

@SuppressWarnings("serial")
public class StorageWindow extends JFrame implements ActionListener {

	private StorageController sController;
	private JPanel contentPane;
	private JTable table;

	public StorageWindow() {
		super("Brew Day! - Storage");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("The storage is:");
		label.setFont(new Font(label.getFont().getName(),Font.BOLD, 17));
		panel.add(label);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		
		sController = StorageController.getInstance();
			
		Storage storage;
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Name","Quantity (g)"}, 0) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};

		storage = sController.extractStorage();
		for(Entry<String, Double> i : storage.getIngredients().entrySet()) {
				model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}
		
		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setFont(new Font(table.getFont().getName(), Font.PLAIN, 16));
		table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 13));
		table.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton modifyButton = new JButton("Modify ingredients");
		modifyButton.setFont(new Font(modifyButton.getFont().getName(), Font.BOLD, 20));
		modifyButton.addActionListener(this);
		
		panel2.add(modifyButton);
		
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font(modifyButton.getFont().getName(), Font.BOLD, 20));
		backButton.addActionListener(this);
		
		panel2.add(backButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Back")) {
			MainWindow mainWindow = new MainWindow();
			mainWindow.setVisible(true);
			dispose();
		} else {
			ModifyStorageIngredientsWindow msiWindow = new ModifyStorageIngredientsWindow();
			msiWindow.setVisible(true);
			dispose();
		}
	}
}