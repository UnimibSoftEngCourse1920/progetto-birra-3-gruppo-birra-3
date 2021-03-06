package main.java.gui;

import java.awt.Component;
import java.awt.Font;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *  The MultiRowCell class can be used to make table cells scrollable
 *  providing a renderer and an editor
 */
@SuppressWarnings("serial")
public class MultiRowCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private JScrollPane scrollPane;
    private JTextArea textArea;

    public MultiRowCell() {
        textArea = new JTextArea(25, 50);
        textArea.setEditable(false);
        
        Font f = new Font("TimesRoman",Font.BOLD,25);
        textArea.setFont(f);

        textArea.setFont(new Font(textArea.getFont().getName(), Font.PLAIN, 14));
        textArea.setLineWrap(true);
        
        scrollPane = new JScrollPane(textArea);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public Object getCellEditorValue() {
        return textArea.getText();
    }

    protected void setCellValue(Object value) {
        if (value == null) {
            textArea.setText(null);
        } else {
            textArea.setText(value.toString());
        }
        textArea.setCaretPosition(0);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
       setCellValue(value);
       return scrollPane;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setCellValue(value);
        return scrollPane;
    }

}
