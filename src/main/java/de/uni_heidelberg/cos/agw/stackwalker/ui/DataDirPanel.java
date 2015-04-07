package de.uni_heidelberg.cos.agw.stackwalker.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

public class DataDirPanel extends JPanel implements ActionListener {

    protected final DataDirTableModel dataDirTableModel = new DataDirTableModel();
    private final JTextField filterEdit = new JTextField();
    private final JTable dataDirTable = new JTable();
    private final JButton addDataDirButton = new JButton("+");
    private final JButton removeDataDirButton = new JButton("-");

    public DataDirPanel() {
        dataDirTable.setModel(dataDirTableModel);
        dataDirTable.doLayout();
        addDataDirButton.addActionListener(this);
        removeDataDirButton.addActionListener(this);

        setLayout(new MigLayout("", "[][grow][][]", "[][][grow]"));
        add(new JLabel("Filter"), "cell 0 0");
        add(filterEdit, "cell 1 0 3 1,grow");
        add(new JLabel("Data folders"), "cell 0 1");
        add(addDataDirButton, "cell 2 1");
        add(removeDataDirButton, "cell 3 1");
        add(new JScrollPane(dataDirTable), "cell 0 2 4 1,grow");
    }

    private void addDataDir() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            dataDirTableModel.insertRow(chooser.getSelectedFile().getAbsolutePath(), false);
        }
    }

    public FileFilter getFilter() {
        final String txt = filterEdit.getText().trim();
        if (txt.isEmpty()) {
            return new FileFilter() {
                @Override
                public boolean accept(final File pathname) {
                    return true;
                }
            };
        }
        return new FileFilter() {
            private final String filter = txt;
            @Override
            public boolean accept(final File pathname) {
                return pathname.getAbsolutePath().contains(txt);
            }
        };
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final JButton source = (JButton) event.getSource();
        if (source == addDataDirButton)
            addDataDir();
        else if (source == removeDataDirButton) {
            final int rowIdxs[] = dataDirTable.getSelectedRows();
            for (final int rowIdx : rowIdxs)
                dataDirTableModel.removeRow(rowIdx);
        }
    }
}
