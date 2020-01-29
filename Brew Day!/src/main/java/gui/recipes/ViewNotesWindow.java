package main.java.gui.recipes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import main.java.gui.ButtonColumn;
import main.java.gui.MultiRowCell;
import main.java.gui.WindowEditor;
import main.java.recipes.Brew;
import main.java.recipes.BrewController;

@SuppressWarnings("serial")
public class ViewNotesWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private BrewController brewController;
	private Double brewId;

	public ViewNotesWindow(Double id) {
		super("Brew Day! - Brew Notes");
		
		Color color = new Color(189, 255, 178);
		
		contentPane = WindowEditor.showWindow(this, color);
		
		this.brewId = id;
		
	    WindowEditor.initializeWindow(contentPane, color, "The Notes are:");

		brewController = BrewController.getInstance();
		List<Brew> brews = brewController.extractBrew();

		DefaultTableModel model = new DefaultTableModel(new String[]{"Note number","Recipe Name","Brew Number","Note text","Type","",""}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				switch (column) {
				case 3:
				case 5:
				case 6:
					return true;
				default:
					return false;
				}
			}
		};

		for(Brew b : brews) {
			if (b.getId().compareTo(this.brewId) == 0) {
				for(Entry<Integer, String> note : b.getNotes().entrySet()) {
					model.addRow(new String[] {note.getKey().toString(),b.getRecipe().getName(),Double.toString(b.getId()),note.getValue(),b.getNoteType(note.getKey()),"Modify","Delete"});
				}
			}
		}

		table = WindowEditor.createTable(model, this, color, 50);
		TableColumn column = table.getColumnModel().getColumn(3);
		MultiRowCell multiRowCell = new MultiRowCell();
		column.setPreferredWidth(600);
		column.setCellEditor(multiRowCell);
		column.setCellRenderer(multiRowCell);
		//This is for hide first column
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);

		new ButtonColumn(table, this, 5);
		new ButtonColumn(table, this, 6);
		
		WindowEditor.createBackAndOther(null, contentPane, this, color,"Add");
	}

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Back":
			WindowEditor.back(this,new BrewWindow());
			break;
		case "Add":
			setVisible(false);
			new AddOrModifyNoteWindow(brewId,0).setVisible(true);
			dispose();
			break;
		default:
			String[] tokens1 = e.getActionCommand().split("/");
			int noteId1 = Integer.parseInt(tokens1[0]);
			String command = tokens1[1];
			int row = Integer.parseInt(tokens1[2]);

			switch(command) {
			case "Modify":
				setVisible(false);
				new AddOrModifyNoteWindow(brewId,noteId1).setVisible(true);
				dispose();
				break;
			case "Delete":
				brewController.deleteNote(brewId, noteId1);
				JTable table1 = (JTable)e.getSource();
				((DefaultTableModel)table1.getModel()).removeRow(row);
				break;
			default:
			}
		}
	}
}
