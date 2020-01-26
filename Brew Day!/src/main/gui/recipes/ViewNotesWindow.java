package main.gui.recipes;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import main.gui.ButtonColumn;
import main.gui.MultiRowCell;
import main.recipes.Brew;
import main.recipes.BrewController;

@SuppressWarnings("serial")
public class ViewNotesWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private BrewController brewController;
	private Double brewId;

	public ViewNotesWindow(Double id) {
		super("Brew Day! - Brew Notes");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 150, 1280, 720);
		this.brewId = id;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("The Notes are:");
		label.setFont(new Font(label.getFont().getName(),Font.BOLD, 17));
		panel.add(label);

		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);

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

		table = new JTable(model);
		table.setBorder(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumn column = table.getColumnModel().getColumn(3);
		MultiRowCell multiRowCell = new MultiRowCell();
		column.setPreferredWidth(600);
		column.setCellEditor(multiRowCell);
		column.setCellRenderer(multiRowCell);
		//This is for hide first column
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getTableHeader().setFont(new Font(table.getFont().getName(), Font.PLAIN, 14));
		table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 12));
		table.setFillsViewportHeight(true);
		table.setRowHeight(50);

		new ButtonColumn(table, this, 5);
		new ButtonColumn(table, this, 6);

		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);

		JButton addButton = new JButton("Add");
		addButton.setFont(new Font(addButton.getFont().getName(),Font.BOLD, 18));
		addButton.addActionListener(this);
		panel2.add(addButton);

		JButton backButton = new JButton("Back");
		backButton.setFont(new Font(backButton.getFont().getName(),Font.BOLD, 18));
		backButton.addActionListener(this);
		panel2.add(backButton);
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
		switch(e.getActionCommand()) {
		case "Back":
			setVisible(false);
			new BrewWindow().setVisible(true);
			dispose();
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