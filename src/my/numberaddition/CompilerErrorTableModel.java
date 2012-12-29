/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.numberaddition;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jantic
 */
public class CompilerErrorTableModel extends AbstractTableModel {

    private final List<String> columnNames = new ArrayList();
    private final List<List> data = new ArrayList();

    {
        columnNames.add("File");
        columnNames.add("Message");
        columnNames.add("Line");
        columnNames.add("Column");
    }

    public void addRow(String fileName, String message, String lineNumber, String columnNumber) {
        final List<String> rowData = new ArrayList<>();
        rowData.add(fileName);
        rowData.add(message);
        rowData.add(lineNumber);
        rowData.add(columnNumber);
        data.add(rowData);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
        try {
            return columnNames.get(col);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
