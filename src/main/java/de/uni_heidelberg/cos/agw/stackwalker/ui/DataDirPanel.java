package de.uni_heidelberg.cos.agw.stackwalker.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class DataDirPanel extends JPanel implements ActionListener {

    protected final DataDirTableModel dataDirTableModel = new DataDirTableModel();
    private final JTextField extensionsEdit = new JTextField(".klb");
    private final JTextField filterEdit = new JTextField();
    private final JTable dataDirTable = new JTable();
    private final JButton addDataDirButton = new JButton("+");
    private final JButton removeDataDirButton = new JButton("-");

    public DataDirPanel() {
        dataDirTable.setModel(dataDirTableModel);
        dataDirTable.doLayout();
        addDataDirButton.addActionListener(this);
        removeDataDirButton.addActionListener(this);

        setLayout(new MigLayout("", "[][grow][][]", "[][][][grow]"));
        add(new JLabel("File name extensions"), "cell 0 0");
        add(extensionsEdit, "cell 1 0 3 1,grow");
        add(new JLabel("File name filter"), "cell 0 1");
        add(filterEdit, "cell 1 1 3 1,grow");
        add(new JLabel("Data folders"), "cell 0 2");
        add(addDataDirButton, "cell 2 2");
        add(removeDataDirButton, "cell 3 2");
        add(new JScrollPane(dataDirTable), "cell 0 3 4 1,grow");
    }

    private void addDataDir() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            dataDirTableModel.insertRow(chooser.getSelectedFile().getAbsolutePath(), false);
        }
    }

    public FileFilter getFilter() {
        return new FileFilter() {
            private final String[] extensions = extensionsEdit.getText().trim().split("\\s+");
            private final String[] names = filterEdit.getText().trim().split("\\s+");
            @Override
            public boolean accept(final File pathname) {
                final String fn = pathname.getAbsolutePath();
                for (final String ext : extensions) {
                    if (!fn.endsWith(ext)) {
                        return false;
                    }
                }
                for (final String name : names) {
                    if (name.startsWith("-") && fn.contains(name.substring(1))) {
                        return false;
                    } else if (!fn.contains(name)) {
                        return false;
                    }
                }
                return true;
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
