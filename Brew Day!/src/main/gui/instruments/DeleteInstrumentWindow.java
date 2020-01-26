package main.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import main.instruments.EquipmentController;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class DeleteInstrumentWindow extends JFrame implements ActionListener{

	private JPanel contentPane;

	public DeleteInstrumentWindow() {
		super("Brew Day! - Delete instrument");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
		setIconImage(icon.getImage());
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblSelectTheInstrument = new JLabel("Select the instrument you want to delete from Equipment:");
		lblSelectTheInstrument.setFont(boldFont);
		panel.add(lblSelectTheInstrument);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.WEST);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnBack.setFont(boldFont);
		panel2.add(btnBack);
		
		
		Map<String, Double> instruments = new HashMap<>();
		EquipmentController equipmentController = EquipmentController.getInstance();
		instruments = equipmentController.extractEquipment().getInstruments();
		ArrayList<JButton> instrumentsDelete = new ArrayList<>();
		
		for(Entry<String, Double> i : instruments.entrySet()) {
			instrumentsDelete.add(new JButton(i.getKey()));
		}
		for(JButton b : instrumentsDelete) {
			b.setFont(boldFont);
			panel1.add(b);
			b.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			EquipmentWindow equipmentWin = new EquipmentWindow();
			equipmentWin.setVisible(true);
			dispose();
		}
		else {
			EquipmentController equipmentController = EquipmentController.getInstance();
			String instrumentDel = e.getActionCommand();
			equipmentController.delete(instrumentDel);
			
			EquipmentWindow equipmentWin = new EquipmentWindow();
			equipmentWin.setVisible(true);
			dispose();
		}
	}
}
