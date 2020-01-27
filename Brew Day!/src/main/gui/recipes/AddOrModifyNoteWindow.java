package main.gui.recipes;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import main.recipes.Brew;
import main.recipes.BrewController;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class AddOrModifyNoteWindow extends JFrame implements ActionListener, ItemListener{

	private int noteId;
	private Double brewId;
	private boolean tasting;
	BrewController brewController;
	JTextArea textArea;

	public AddOrModifyNoteWindow(Double brewId,int noteId) {
		super("Brew Day! - Modify Note");
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH); 
		setVisible(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().setBackground(new Color(189, 255, 178));
		this.brewId = brewId;
		this.noteId = noteId;
		
		Font plainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

		textArea = new JTextArea(16,20);
		getContentPane().add(textArea, BorderLayout.CENTER);
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
			getContentPane().add(chckbxNewCheckBox, BorderLayout.NORTH);
			chckbxNewCheckBox.addItemListener(this);
		}

		JPanel panel = new JPanel();
		panel.setBackground(new Color(189, 255, 178));
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
