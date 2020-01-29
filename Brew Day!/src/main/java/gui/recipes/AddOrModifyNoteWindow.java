package main.java.gui.recipes;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JTextArea;

import main.java.gui.WindowEditor;
import main.java.recipes.Brew;
import main.java.recipes.BrewController;

import javax.swing.JButton;
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
		
		contentPane = WindowEditor.showWindow(this, new Color(189, 255, 178));

		this.brewId = brewId;
		this.noteId = noteId;
		
		Font plainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);

		textArea = new JTextArea(16,20);
		contentPane.add(textArea, BorderLayout.CENTER);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(plainFont);

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

		JPanel panel = new JPanel();
		panel.setBackground(new Color(189, 255, 178));
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnSave = new JButton("Save");
		btnSave.setFont(boldFont);
		btnSave.addActionListener(this);
		panel.add(btnSave);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(boldFont);
		btnCancel.addActionListener(this);
		panel.add(btnCancel);
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
		setVisible(false);
		new ViewNotesWindow(brewId).setVisible(true);
		dispose();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == 1) {
			tasting = true;
		} else {
			tasting = false;
		}
	}
}
