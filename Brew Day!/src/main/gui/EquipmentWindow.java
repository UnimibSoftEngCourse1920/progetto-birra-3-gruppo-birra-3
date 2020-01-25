package main.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import main.instrument.EquipmentController;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class EquipmentWindow extends JFrame {

	private JPanel contentPane;
	private String filepath = System.getProperty("user.dir") + "\\src\\Files\\Equipment.txt";

	public EquipmentWindow() {
		super("Brew Day! - Equipment menu");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 150, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblEquipment = new JLabel("Equipment menu");
		panel.add(lblEquipment);

		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		GridBagLayout gblPanel1 = new GridBagLayout();
		gblPanel1.columnWidths = new int[]{121};
		gblPanel1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gblPanel1.columnWeights = new double[]{0.0};
		gblPanel1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel1.setLayout(gblPanel1);

		JButton btnShowEquipment = new JButton("Show Equipment");

		btnShowEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(panel1, "You have no equipment to show");
				}
				else {
					EquipmentController equipmentController = EquipmentController.getInstance();
					int size = equipmentController.extractEquipment().getInstruments().size();
					if(size == 0) {
						JOptionPane.showMessageDialog(panel1, "You have no instruments in your equipment");
					}
					else {
						ShowEquipmentWindow showEqWin = new ShowEquipmentWindow();
						showEqWin.setVisible(true);
						dispose();
					}

				}
			}
		});
		
		GridBagConstraints gbcBtnShowEquipment = new GridBagConstraints();
		gbcBtnShowEquipment.fill = GridBagConstraints.BOTH;
		gbcBtnShowEquipment.insets = new Insets(0, 0, 5, 0);
		gbcBtnShowEquipment.gridx = 0;
		gbcBtnShowEquipment.gridy = 1;
		panel1.add(btnShowEquipment, gbcBtnShowEquipment);


		JButton btnCreateEquipment = new JButton("Create Equipment");

		btnCreateEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(filepath);
				if(!f.exists()) {
					CreateEquipmentWindow createEqWin = new CreateEquipmentWindow();
					createEqWin.setVisible(true);
					dispose();
				}
				else {
					EquipmentController equipmentController = EquipmentController.getInstance();
					int size = equipmentController.extractEquipment().getInstruments().size();
					if(size == 0) {
						CreateEquipmentWindow createEqWin = new CreateEquipmentWindow();
						createEqWin.setVisible(true);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(panel1, "You already have an equipment");
					}
				}
			}
		});

		GridBagConstraints gbcBtnCreateEquipment = new GridBagConstraints();
		gbcBtnCreateEquipment.fill = GridBagConstraints.BOTH;
		gbcBtnCreateEquipment.insets = new Insets(0, 0, 5, 0);
		gbcBtnCreateEquipment.gridx = 0;
		gbcBtnCreateEquipment.gridy = 0;
		panel1.add(btnCreateEquipment, gbcBtnCreateEquipment);
		
		JButton btnModifyEquipment = new JButton("Modify Equipment");

		btnModifyEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(panel1, "You have no equipment");
				}
				else {
					EquipmentController equipmentController = EquipmentController.getInstance();
					int size = equipmentController.extractEquipment().getInstruments().size();
					if(size == 0) {
						JOptionPane.showMessageDialog(panel1, "You have no instruments in your equipment to modify");
					}
					else {
						ModifyEquipmentWindow modifyEqWin = new ModifyEquipmentWindow();
						modifyEqWin.setVisible(true);
						dispose();
					}

				}
			}
		});

		GridBagConstraints gbcBtnModifyEquipment = new GridBagConstraints();
		gbcBtnModifyEquipment.fill = GridBagConstraints.BOTH;
		gbcBtnModifyEquipment.insets = new Insets(0, 0, 5, 0);
		gbcBtnModifyEquipment.gridx = 0;
		gbcBtnModifyEquipment.gridy = 2;
		panel1.add(btnModifyEquipment, gbcBtnModifyEquipment);

		JButton btnInsertNewInstrument = new JButton("Insert New Instrument");
		
		btnInsertNewInstrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(panel1, "You should create a new equipment first");
				}
				else {
					InsertNewInstrumentWindow insertNewInstrumentWin = new InsertNewInstrumentWindow();
					insertNewInstrumentWin.setVisible(true);
					dispose();
				}
			}
		});

		GridBagConstraints gbcBtnInsertNewInstrument = new GridBagConstraints();
		gbcBtnInsertNewInstrument.fill = GridBagConstraints.BOTH;
		gbcBtnInsertNewInstrument.insets = new Insets(0, 0, 5, 0);
		gbcBtnInsertNewInstrument.gridx = 0;
		gbcBtnInsertNewInstrument.gridy = 3;
		panel1.add(btnInsertNewInstrument, gbcBtnInsertNewInstrument);

		JButton btnDeleteInstrument = new JButton("Delete Instrument");
		
		btnDeleteInstrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EquipmentController equipmentController = EquipmentController.getInstance();
				File f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(panel1, "You have no equipment to delete");
				}else {
						int size = equipmentController.extractEquipment().getInstruments().size();
						if(size != 0) {
							DeleteInstrumentWindow deleteInstrumentWin = new DeleteInstrumentWindow();
							deleteInstrumentWin.setVisible(true);
							dispose();
						}else {
						JOptionPane.showMessageDialog(panel1, "You have no equipment to delete");
					}
				}
			}
		});

		GridBagConstraints gbcBtnDeleteInstrument = new GridBagConstraints();
		gbcBtnDeleteInstrument.insets = new Insets(0, 0, 5, 0);
		gbcBtnDeleteInstrument.fill = GridBagConstraints.BOTH;
		gbcBtnDeleteInstrument.gridx = 0;
		gbcBtnDeleteInstrument.gridy = 4;
		panel1.add(btnDeleteInstrument, gbcBtnDeleteInstrument);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow mainWin = new MainWindow();
				mainWin.setVisible(true);
				dispose();
			}
		});
		panel2.add(btnBack);
	}
}
