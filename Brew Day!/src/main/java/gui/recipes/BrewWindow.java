package main.java.gui.recipes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.gui.ButtonColumn;
import main.java.gui.MainWindow;
import main.java.gui.WindowEditor;
import main.java.recipes.Brew;
import main.java.recipes.BrewController;

@SuppressWarnings("serial")
public class BrewWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private BrewController brewController;

	public BrewWindow() {
		super("Brew Day! - Brews");
		
		Color color = new Color(189, 255, 178);
		
		contentPane = WindowEditor.showWindow(this, color);

		WindowEditor.initializeWindow(contentPane, color, "The brews are:");

		brewController = BrewController.getInstance();
		List<Brew> brews = brewController.extractBrew();

		DefaultTableModel model = new DefaultTableModel(new String[]{"Brew Number", "Recipe Name","Ingredients","Batch Size (l)","Note's Number","Start Date","Finish Date","","","",""}, 0) {
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
				ingredient.append("   " + i.getKey() + "= " + Double.toString(i.getValue()) + "g");
			}
			model.addRow(new String[] {Double.toString(b.getId()),b.getRecipe().getName(),ingredient.toString(),Double.toString(b.getBatchSize()),Integer.toString(b.getNotes().size()),fromDatetoString(b.getStartDate()),fromDatetoString(b.getFinishDate()),"View Notes","Terminate","Cancel","Delete"});
			ingredient = new StringBuilder();
		}

		table = WindowEditor.createTable(model, this, color, 40);
		table.getColumnModel().getColumn(2).setPreferredWidth(350);

	    new ButtonColumn(table, this, 7);
		new ButtonColumn(table, this, 8);
		new ButtonColumn(table, this, 9);
		new ButtonColumn(table, this, 10);

		WindowEditor.createBack(null, contentPane, this, color);
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
			MainWindow mainWindow = new MainWindow();
			WindowEditor.back(this,mainWindow);
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
