package main.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CreateEquipmentWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateEquipmentWindow frame = new CreateEquipmentWindow();
					frame.setVisible(true);
					frame.setSize(600, 400);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateEquipmentWindow() {
		super("Brew Day! - Create equipment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblCreateEquipment = new JLabel("Create Equipment");
		panel.add(lblCreateEquipment);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{301, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{10.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblHowManyInstruments = new JLabel("How many instruments do you want to insert?");
		GridBagConstraints gbc_lblHowManyInstruments = new GridBagConstraints();
		gbc_lblHowManyInstruments.insets = new Insets(0, 0, 5, 0);
		gbc_lblHowManyInstruments.gridx = 0;
		gbc_lblHowManyInstruments.gridy = 0;
		panel_2.add(lblHowManyInstruments, gbc_lblHowManyInstruments);
		
		textField = new JTextField(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		panel_2.add(textField, gbc_textField);
		
		JButton btnNext = new JButton("Next >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String inputString = textField.getText();
					int input = Integer.parseInt(inputString);
					InsertInstrumentsWindow insertInstrumentsWin = new InsertInstrumentsWindow(input);
					insertInstrumentsWin.setVisible(true);
					dispose();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(panel_2,"You must insert a number");
				}catch(NullPointerException e1) {
					JOptionPane.showMessageDialog(panel_2, "You must insert a number");
				}
				
				
				
			}
		});
		
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridx = 0;
		gbc_btnNext.gridy = 2;
		panel_2.add(btnNext, gbc_btnNext);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EquipmentWindow equipmentWin = new EquipmentWindow();
				equipmentWin.setVisible(true);
				dispose();
			}
		});
		panel_1.add(btnBack);
	}

}
