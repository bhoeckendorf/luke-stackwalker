package de.uni_heidelberg.cos.agw.stackwalker.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataTypePanel extends JPanel implements ActionListener, TableModelListener {

    protected final DataTypeTableModel dataTypeTableModel = new DataTypeTableModel();
    private final JButton
            addButton = new JButton("+"),
            removeButton = new JButton("-"),
            upButton = new JButton("up"),
            downButton = new JButton("down");
    private final JComboBox presetComboBox;
    private final JTable dataTypeTable;

    public DataTypePanel() {
        dataTypeTable = new JTable(dataTypeTableModel);
        dataTypeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        dataTypeTable.doLayout();

        presetComboBox = new JComboBox(dataTypeTableModel.presetComboBoxModel);

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        upButton.addActionListener(this);
        downButton.addActionListener(this);
        presetComboBox.addActionListener(this);
        dataTypeTableModel.addTableModelListener(this);

        setLayout(new MigLayout("", "[][][grow][][]", "[][][][grow]"));
        add(new JLabel("Preset"), "cell 0 1");
        add(presetComboBox, "cell 1 1 4 1,growx");
        add(addButton, "cell 0 2");
        add(removeButton, "cell 1 2");
        add(upButton, "cell 3 2");
        add(downButton, "cell 4 2");
        add(new JScrollPane(dataTypeTable), "cell 0 3 5 1,grow");
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final Object source = event.getSource();
        final int rowIndex = dataTypeTable.getSelectedRow();
        if (source == addButton)
            dataTypeTableModel.addRow(rowIndex);
        else if (source == removeButton)
            dataTypeTableModel.removeRow(rowIndex);
        else if (source == upButton) {
            if (dataTypeTableModel.moveUp(rowIndex))
                dataTypeTable.getSelectionModel().setSelectionInterval(rowIndex - 1, rowIndex - 1);
        } else if (source == downButton) {
            if (dataTypeTableModel.moveDown(rowIndex))
                dataTypeTable.getSelectionModel().setSelectionInterval(rowIndex + 1, rowIndex + 1);
        } else if (source == presetComboBox) {
            dataTypeTableModel.removeTableModelListener(this);
            dataTypeTableModel.setPreset(((JComboBox) source).getSelectedIndex());
            dataTypeTableModel.addTableModelListener(this);
        }
    }

    public void tableChanged(TableModelEvent event) {
        presetComboBox.setSelectedIndex(0);
    }
}
