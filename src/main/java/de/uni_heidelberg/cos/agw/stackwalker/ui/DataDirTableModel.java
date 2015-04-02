package de.uni_heidelberg.cos.agw.stackwalker.ui;

import de.uni_heidelberg.cos.agw.stackwalker.DataDir;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DataDirTableModel extends AbstractTableModel {

    protected final List<DataDir> dataDirs = new ArrayList<DataDir>();
    private final String[] columnHeaders = {"Path", "Recursive?"};

    public void insertRow(final String dirPath, final boolean isRecursive) {
        dataDirs.add(new DataDir(dirPath, isRecursive));
        final int lastRowIdx = getRowCount() - 1;
        fireTableRowsInserted(lastRowIdx, lastRowIdx);
    }

    public void removeRow(final int rowIdx) {
        dataDirs.remove(rowIdx);
        fireTableRowsDeleted(rowIdx, rowIdx);
    }

    @Override
    public int getColumnCount() {
        return columnHeaders.length;
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return columnHeaders[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public int getRowCount() {
        return dataDirs.size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final DataDir dir = dataDirs.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dir.getDirPath();
            case 1:
                return dir.isRecursive();
            default:
                assert false;
                return null;
        }
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(final Object value, final int rowIndex, final int columnIndex) {
        final DataDir dir = dataDirs.get(rowIndex);
        switch (columnIndex) {
            case 0:
                dir.setDirPath(value.toString());
                break;
            case 1:
                dir.setIsRecursive(Boolean.parseBoolean(value.toString()));
                break;
            default:
                assert false;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
