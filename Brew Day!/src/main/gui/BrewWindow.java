package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import main.recipes.Brew;
import main.recipes.BrewController;


@SuppressWarnings("serial")
public class BrewWindow extends JFrame implements ActionListener {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public BrewWindow(){
		super("Brews");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel viewBrewsPanel = new JPanel();

		Font f = new Font("TimesRoman",Font.BOLD,25);
		JTable brewsTable = new JTable(createbrewsTable());
		brewsTable.setBorder(null);
		brewsTable.getTableHeader().setFont(f);
		brewsTable.setFont(f);
		brewsTable.setRowHeight(30);
		brewsTable.setFillsViewportHeight(true);
		brewsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scrollPane = new JScrollPane(brewsTable);

		@SuppressWarnings("unused")
		ButtonColumn viewNotesColumn = new ButtonColumn(brewsTable, this, 7);

		viewBrewsPanel.add(brewsTable);

		JPanel bottomPanel = new JPanel();

		Dimension d = new Dimension(200, 70);
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setPreferredSize(d);
		bottomPanel.add(backButton);

		this.add(bottomPanel, BorderLayout.CENTER);
		this.add(scrollPane, BorderLayout.NORTH);
	}


	public static DefaultTableModel createbrewsTable() {

		BrewController brewController = BrewController.getInstance();

		ArrayList<Brew> Brews = brewController.extractBrew();

		//Only for testing purposes
		brewController.deleteFile();
		//Only for testing purposes 

		DefaultTableModel model = new DefaultTableModel(new String[]{"Brew Number","Recipe Number", "Recipe Name","Ingredients","Note's number","Start date", "Finish date",""}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 7;
			}
		};

		String ingredients;
		for(Brew r : Brews) {
			ingredients = "";
			for(Entry<String, Double> i : r.getRecipe().getIngredients().entrySet()) {
				ingredients +=  "  " + i.getKey() + "= " + Double.toString(i.getValue());
			}
			model.addRow(new String[] {Double.toString(r.getId()),Integer.toString(r.getRecipe().getId()),r.getRecipe().getName(),ingredients,Integer.toString(r.getNotes().size()), new SimpleDateFormat("MM/dd/yyyy").format(r.getStartDate()),new SimpleDateFormat("MM/dd/yyyy").format(r.getFinishDate()),"View Notes","Modify","Delete"});
		}

		return model;
	}

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Back":
			setVisible(false);
			MainWindow.getInstance().setVisible(true);
			break;
		}
	}

	public static void main(String[] args){
		BrewWindow gui = new BrewWindow();
		gui.setVisible( true);
	}
}
