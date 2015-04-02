package de.uni_heidelberg.cos.agw.stackwalker.ui;

import de.uni_heidelberg.cos.agw.stackwalker.DataType;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DataTypeTableModel extends AbstractTableModel {

    protected final DefaultComboBoxModel<String> presetComboBoxModel = new DefaultComboBoxModel<String>();
    private final List<DataTypeModelPreset> presets = new ArrayList<DataTypeModelPreset>();
    private final String[] columnHeaders = {
            "Active",
            "Name",
            "Filename tag",
            "Fixed start",
            "Fixed size"
    };
    private int currentPresetIdx = 0;

    public DataTypeTableModel() {
        addPreset(new DataTypeModelPreset("", new ArrayList<DataType>()));
        for (final DataTypeModelPreset preset : DataTypeModelPreset.read())
            addPreset(preset);
    }

    private void addPreset(final DataTypeModelPreset preset) {
        presetComboBoxModel.addElement(preset.name);
        presets.add(preset);
    }

    public void setPreset(final int presetIdx) {
        if (presetIdx == 0)
            return;

        DataType.LIST.clear();
        DataTypeModelPreset preset = presets.get(presetIdx);
        for (DataType dataType : preset.dataTypes)
            addRow(dataType);
        currentPresetIdx = presetIdx;
    }

    public void addRow() {
        addRow(DataType.LIST.size());
    }

    public void addRow(final int rowIndex) {
        if (rowIndex < 0 || rowIndex > DataType.LIST.size()) {
            addRow();
            return;
        }
        DataType.LIST.add(rowIndex, new DataType());
        fireTableRowsInserted(rowIndex, rowIndex);
    }

    public void addRow(final DataType type) {
        final int rowIndex = DataType.LIST.size();
        DataType.LIST.add(type);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

    public void removeRow(final int rowIndex) {
        DataType.LIST.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public boolean moveUp(final int rowIndex) {
        if (rowIndex == 0)
            return false;
        final DataType type = DataType.LIST.remove(rowIndex);
        DataType.LIST.add(rowIndex - 1, type);
        fireTableRowsUpdated(rowIndex - 1, rowIndex);
        return true;
    }

    public boolean moveDown(final int rowIndex) {
        if (rowIndex >= DataType.LIST.size() - 1)
            return false;
        final DataType type = DataType.LIST.remove(rowIndex);
        DataType.LIST.add(rowIndex + 1, type);
        fireTableRowsUpdated(rowIndex, rowIndex + 1);
        return true;
    }

    @Override
    public int getColumnCount() {
        return columnHeaders.length;
    }

    @Override
    public String getColumnName(final int index) {
        return columnHeaders[index];
    }

    @Override
    public Class<?> getColumnClass(final int index) {
        return getValueAt(0, index).getClass();
    }

    @Override
    public int getRowCount() {
        return DataType.LIST.size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int colIndex) {
        final DataType type = DataType.LIST.get(rowIndex);
        switch (colIndex) {
            case 0:
                return type.isActive();
            case 1:
                return type.getName();
            case 2:
                return type.getFileNameTag();
            case 3:
                return type.hasFixedNumBlockStart();
            case 4:
                return type.hasFixedNumBlockSize();
            default:
                assert false;
                return null;
        }
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int colIndex) {
        return true;
    }

    @Override
    public void setValueAt(final Object value, final int rowIndex, final int colIndex) {
        final DataType type = DataType.LIST.get(rowIndex);
        switch (colIndex) {
            case 0:
                type.setActive(Boolean.parseBoolean(value.toString()));
                break;
            case 1:
                type.setName(value.toString());
                break;
            case 2:
                type.setFileNameTag(value.toString());
                break;
            case 3:
                type.setFixedNumBlockStart(Boolean.parseBoolean(value.toString()));
                break;
            case 4:
                type.setFixedNumBlockSize(Boolean.parseBoolean(value.toString()));
                break;
            default:
                assert false;
        }
        fireTableCellUpdated(rowIndex, colIndex);
    }
}
