package main.gui.instruments;

import java.awt.BorderLayout;
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
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class CreateEquipmentWindow extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;

	public CreateEquipmentWindow() {
		super("Brew Day! - Create equipment");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblCreateEquipment = new JLabel("Create Equipment");
		panel.add(lblCreateEquipment);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.CENTER);
		GridBagLayout gblPanel2 = new GridBagLayout();
		gblPanel2.columnWidths = new int[]{301, 0};
		gblPanel2.rowHeights = new int[]{0, 0, 0, 0};
		gblPanel2.columnWeights = new double[]{10.0, Double.MIN_VALUE};
		gblPanel2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel2.setLayout(gblPanel2);
		
		JLabel lblHowManyInstruments = new JLabel("How many instruments do you want to insert?");
		GridBagConstraints gbcLblHowManyInstruments = new GridBagConstraints();
		gbcLblHowManyInstruments.insets = new Insets(0, 0, 5, 0);
		gbcLblHowManyInstruments.gridx = 0;
		gbcLblHowManyInstruments.gridy = 0;
		panel2.add(lblHowManyInstruments, gbcLblHowManyInstruments);
		
		textField = new JTextField(10);
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.insets = new Insets(0, 0, 5, 0);
		gbcTextField.gridx = 0;
		gbcTextField.gridy = 1;
		panel2.add(textField, gbcTextField);
		
		JButton btnNext = new JButton("Next >");
		btnNext.addActionListener(this);
		
		GridBagConstraints gbcBtnNext = new GridBagConstraints();
		gbcBtnNext.gridx = 0;
		gbcBtnNext.gridy = 2;
		panel2.add(btnNext, gbcBtnNext);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		panel1.add(btnBack);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			EquipmentWindow equipmentWin = new EquipmentWindow();
			equipmentWin.setVisible(true);
			dispose();
		}
		else {
			try {
				String inputString = textField.getText();
				int input = Integer.parseInt(inputString);
				InsertInstrumentsWindow insertInstrumentsWin = new InsertInstrumentsWindow(input);
				insertInstrumentsWin.setVisible(true);
				dispose();
			} catch (NumberFormatException|NullPointerException e1) {
				JOptionPane.showMessageDialog(this,"You must insert a number");
			}	
		}
	}
}
