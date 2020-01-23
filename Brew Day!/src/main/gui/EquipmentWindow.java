package main.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EquipmentWindow frame = new EquipmentWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EquipmentWindow() {
		super("Brew Day! - Equipment menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblEquipment = new JLabel("Equipment");
		panel.add(lblEquipment);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{121, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnShowEquipment = new JButton("Show Equipment");
		btnShowEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(panel_1, "You have no equipment to show");
				}
				else {
					EquipmentController equipmentController = EquipmentController.getInstance();
					int size = equipmentController.extractEquipment().getInstruments().size();
					if(size == 0) {
						JOptionPane.showMessageDialog(panel_1, "You have no instruments in your equipment");
					}
					else {
						ShowEquipmentWindow showEqWin = new ShowEquipmentWindow();
						showEqWin.setVisible(true);
						dispose();
					}
					
				}
			}
		});
		
		
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
						JOptionPane.showMessageDialog(panel_1, "You already have an equipment");
					}
				}
			}
		});
		GridBagConstraints gbc_btnCreateEquipment = new GridBagConstraints();
		gbc_btnCreateEquipment.fill = GridBagConstraints.BOTH;
		gbc_btnCreateEquipment.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateEquipment.gridx = 0;
		gbc_btnCreateEquipment.gridy = 0;
		panel_1.add(btnCreateEquipment, gbc_btnCreateEquipment);
		GridBagConstraints gbc_btnShowEquipment = new GridBagConstraints();
		gbc_btnShowEquipment.fill = GridBagConstraints.BOTH;
		gbc_btnShowEquipment.insets = new Insets(0, 0, 5, 0);
		gbc_btnShowEquipment.gridx = 0;
		gbc_btnShowEquipment.gridy = 1;
		panel_1.add(btnShowEquipment, gbc_btnShowEquipment);
		
		JButton btnModifyEquipment = new JButton("Modify Equipment");
		btnModifyEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(panel_1, "You have no equipment");
				}
				else {
					EquipmentController equipmentController = EquipmentController.getInstance();
					int size = equipmentController.extractEquipment().getInstruments().size();
					if(size == 0) {
						JOptionPane.showMessageDialog(panel_1, "You have no instruments in your equipment to modify");
					}
					else {
						ModifyEquipmentWindow modifyEqWin = new ModifyEquipmentWindow();
						modifyEqWin.setVisible(true);
						dispose();
					}
					
				}
			}
		});
		
		GridBagConstraints gbc_btnModifyEquipment = new GridBagConstraints();
		gbc_btnModifyEquipment.fill = GridBagConstraints.BOTH;
		gbc_btnModifyEquipment.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifyEquipment.gridx = 0;
		gbc_btnModifyEquipment.gridy = 2;
		panel_1.add(btnModifyEquipment, gbc_btnModifyEquipment);
		
		
		JButton btnInsertNewInstrument = new JButton("Insert New Instrument");
		btnInsertNewInstrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(filepath);
				if(!f.exists()) {
					JOptionPane.showMessageDialog(panel_1, "You should create a new equipment first");
				}
				else {
					InsertNewInstrumentWindow insertNewInstrumentWin = new InsertNewInstrumentWindow();
					insertNewInstrumentWin.setVisible(true);
					dispose();
				}
			}
		});
		
		GridBagConstraints gbc_btnInsertNewInstrument = new GridBagConstraints();
		gbc_btnInsertNewInstrument.insets = new Insets(0, 0, 5, 0);
		gbc_btnInsertNewInstrument.gridx = 0;
		gbc_btnInsertNewInstrument.gridy = 3;
		panel_1.add(btnInsertNewInstrument, gbc_btnInsertNewInstrument);
		
		
		JButton btnDeleteInstrument = new JButton("Delete Instrument");
		btnDeleteInstrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(filepath);
				if(f.exists()) {
					DeleteInstrumentWindow deleteInstrumentWin = new DeleteInstrumentWindow();
					deleteInstrumentWin.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(panel_1, "You have no equipment to delete");
				}
				
			}
		});
		
		GridBagConstraints gbc_btnDeleteInstrument = new GridBagConstraints();
		gbc_btnDeleteInstrument.fill = GridBagConstraints.BOTH;
		gbc_btnDeleteInstrument.gridx = 0;
		gbc_btnDeleteInstrument.gridy = 4;
		panel_1.add(btnDeleteInstrument, gbc_btnDeleteInstrument);
	}
}
