package main.gui.recipes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.gui.ButtonColumn;
import main.gui.MainWindow;
import main.recipes.Brew;
import main.recipes.BrewController;

@SuppressWarnings("serial")
public class BrewWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private BrewController brewController;

	public BrewWindow() {
		super("Brew Day! - Brews");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setExtendedState(Frame.MAXIMIZED_BOTH); 
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(189, 255, 178));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(189, 255, 178));
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("The brews are:");
		label.setFont(boldFont);
		panel.add(label);

		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(189, 255, 178));
		contentPane.add(panel1, BorderLayout.CENTER);

		brewController = BrewController.getInstance();
		List<Brew> brews = brewController.extractBrew();

		DefaultTableModel model = new DefaultTableModel(new String[]{"Brew Number","Recipe Number", "Recipe Name","Ingredients","Note's Number","Start Date","Finish Date","","","",""}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				switch (column) {
				case 7:
				case 8:
				case 9:
				case 10:
					return true;
				default:
					return false;
				}
			}
		};

		StringBuilder ingredient = new StringBuilder();
		for(Brew b : brews) {
			for(Entry<String, Double> i : b.getRecipe().getIngredients().entrySet()) {
				ingredient.append("   " + i.getKey() + "= " + Double.toString(i.getValue()));
			}
			model.addRow(new String[] {Double.toString(b.getId()),Integer.toString(b.getRecipe().getId()),b.getRecipe().getName(),ingredient.toString(),Integer.toString(b.getNotes().size()),fromDatetoString(b.getStartDate()),fromDatetoString(b.getFinishDate()),"View Notes","Terminate","Cancel","Delete"});
			ingredient = new StringBuilder();
		}

		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getColumnModel().getColumn(3).setPreferredWidth(350);
		table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		table.setRowHeight(40);

	    new ButtonColumn(table, this, 7);
		new ButtonColumn(table, this, 8);
		new ButtonColumn(table, this, 9);
		new ButtonColumn(table, this, 10);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(189, 255, 178));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(189, 255, 178));
		contentPane.add(panel2, BorderLayout.SOUTH);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(boldFont);
		btnBack.addActionListener(this);
		panel2.add(btnBack);
	}

	public static String fromDatetoString(Date date) {
		if (date == null) {
			return "";
		}

		String pattern = "dd/MM/yyyy";

		DateFormat df = new SimpleDateFormat(pattern);

		return df.format(date);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Back")) {
			new MainWindow().setVisible(true);
			dispose();
		} else {
			String[] tokens = e.getActionCommand().split("/");
			Double brewId = Double.parseDouble(tokens[0]);
			String command = tokens[1];
			int row = Integer.parseInt(tokens[2]);

			switch(command) {
			case "View Notes":
				setVisible(false);
				ViewNotesWindow vNoteWindow = new ViewNotesWindow(brewId);
				vNoteWindow.setVisible(true);
				dispose();
				break;
			case "Terminate":
				List<Brew> brews = brewController.extractBrew();

				boolean terminated = false;

				for (Brew b : brews) {
					if (b.getId().compareTo(brewId) == 0 && b.getFinishDate() != null) { 
						terminated = true;
					}
				}
				if (!terminated) {
					brewController.setFinishDate(brewId);
					JTable table1 = (JTable)e.getSource();
					((DefaultTableModel)table1.getModel()).setValueAt(fromDatetoString(new Date(System.currentTimeMillis())), row, 6);
				} else {
					JOptionPane.showMessageDialog(this,"You can't terminate a terminated brew");
				}
				break;
			case "Cancel":
				List<Brew> brews1 = brewController.extractBrew();

				boolean terminated1 = false;

				for (Brew b : brews1) {
					if (b.getId().compareTo(brewId) == 0 && b.getFinishDate() != null) {
							terminated1 = true;
					}
				}
				if (!terminated1) {
					brewController.cancel(brewId);
					JTable table2 = (JTable)e.getSource();
					((DefaultTableModel)table2.getModel()).removeRow(row);
				} else {
					JOptionPane.showMessageDialog(this,"You can't cancel a terminated brew");
				}

				break;
			case "Delete":
				brewController.delete(brewId);
				JTable table3 = (JTable)e.getSource();
				((DefaultTableModel)table3.getModel()).removeRow(row);
				break;
			default:
			}
		}
	}
}
