package main.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import main.recipes.Brew;
import main.recipes.BrewController;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AddOrModifyNoteWindow extends JFrame implements ActionListener{
	
	private int noteId;
	private Double brewId;
	BrewController brewController;
	JTextArea textArea;
	
	public AddOrModifyNoteWindow(Double brewId,int noteId) {
		super("Brew Day! - Modify Note");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(250, 400, 500, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.brewId = brewId;
		this.noteId = noteId;
		
		textArea = new JTextArea();
		getContentPane().add(textArea, BorderLayout.CENTER);
		
		brewController = BrewController.getInstance();
		
		List<Brew> brews = brewController.extractBrew();
		
		if (noteId != 0) {
			for (Brew brew : brews) {
				if (brew.getId().compareTo(brewId) == 0) {
					textArea.setText(brew.getNotes().get(this.noteId));
					break;
				}
			}
		}
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		panel.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		panel.add(btnCancel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel")) {
			setVisible(false);
			new ViewNotesWindow(brewId).setVisible(true);
			dispose();
		} else {
			if (noteId != 0) {
				brewController.updateNote(brewId, noteId, textArea.getText());
			} else {
				brewController.addNote(brewId, textArea.getText(), true);
			}
			
			setVisible(false);
			new ViewNotesWindow(brewId).setVisible(true);
			dispose();
		}
		
	}

}
