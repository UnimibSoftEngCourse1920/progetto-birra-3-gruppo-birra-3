package main.java.gui.recipes;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JTextArea;

import main.java.gui.WindowEditor;
import main.java.recipes.Brew;
import main.java.recipes.BrewController;

import javax.swing.JPanel;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class AddOrModifyNoteWindow extends JFrame implements ActionListener, ItemListener{

	private JPanel contentPane;
	private int noteId;
	private Double brewId;
	private boolean tasting;
	BrewController brewController;
	JTextArea textArea;

	public AddOrModifyNoteWindow(Double brewId,int noteId) {
		super("Brew Day! - Modify Note");
		
		Color color = new Color(189, 255, 178);
				
		contentPane = WindowEditor.showWindow(this, new Color(189, 255, 178));

		this.brewId = brewId;
		this.noteId = noteId;

		textArea = new JTextArea(16,20);
		contentPane.add(textArea, BorderLayout.CENTER);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(WindowEditor.plainFont);

		brewController = BrewController.getInstance();

		List<Brew> brews = brewController.extractBrew();

		if (noteId != 0) {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(brewId) == 0) {
					textArea.setText(brew.getNotes().get(this.noteId));
				}
			}
		} else {
			JCheckBox chckbxNewCheckBox = new JCheckBox("Tasting", false);
			contentPane.add(chckbxNewCheckBox, BorderLayout.NORTH);
			chckbxNewCheckBox.addItemListener(this);
		}

		WindowEditor.createBackAndOther(null, contentPane, this, color,"Save");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			if (noteId != 0) {
				brewController.updateNote(brewId, noteId, textArea.getText());
			} else {
				brewController.addNote(brewId, textArea.getText(), tasting);
			}
		}
		ViewNotesWindow vWindow = new ViewNotesWindow(brewId);
		WindowEditor.back(this,vWindow);
		new ViewNotesWindow(brewId);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		//if the checkbox is ticked or unticked
		if (e.getStateChange() == 1) {
			tasting = true;
		} else {
			tasting = false;
		}
	}
}
