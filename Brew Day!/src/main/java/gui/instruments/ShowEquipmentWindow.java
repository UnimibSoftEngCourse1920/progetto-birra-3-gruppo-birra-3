package main.java.gui.instruments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import main.java.gui.WindowEditor;

@SuppressWarnings("serial")
public class ShowEquipmentWindow extends JFrame implements ActionListener {

	private JPanel contentPane;

	public ShowEquipmentWindow() {
		super("Brew Day! - Show equipment");
		
		Color color = new Color(252, 255, 166);
		
		contentPane = WindowEditor.showWindow(this, color);
		
		Map<String, Double> instruments = WindowEditor.initializeSMEquipmentWindow(contentPane, color, "The equipment is composed by:");
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Instrument name","Capacity (l)"}, 0) {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
			   }
			};
		for(Entry<String, Double> i : instruments.entrySet()) {
			model.addRow(new String[] {i.getKey(),Double.toString(i.getValue())});
		}
		
        WindowEditor.createTable(model, this, color, 30);
        
        JPanel panel2 = new JPanel();
		panel2.setBackground(color);
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		JButton btnModifyEquipment = new JButton("Modify Equipment");
		btnModifyEquipment.addActionListener(this);
		btnModifyEquipment.setFont(WindowEditor.boldFont);
		panel2.add(btnModifyEquipment);
		
		WindowEditor.createBackAndOther(panel2, contentPane, this, color,"Delete Instrument");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Back":
			WindowEditor.back(this,new EquipmentWindow());
			break;
		case "Modify Equipment":
			ModifyEquipmentWindow modifyEqWin = new ModifyEquipmentWindow();
			modifyEqWin.setVisible(true);
			dispose();
			break;
		case "Delete Instrument":
			DeleteInstrumentWindow deleteInstrumentWin = new DeleteInstrumentWindow();
			deleteInstrumentWin.setVisible(true);
			dispose();
		default:
		}
	}

}
