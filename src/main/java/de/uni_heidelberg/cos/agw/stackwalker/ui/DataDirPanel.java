package de.uni_heidelberg.cos.agw.stackwalker.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataDirPanel extends JPanel implements ActionListener {

    protected final DataDirTableModel dataDirTableModel = new DataDirTableModel();
    private final JTable dataDirTable = new JTable();
    private final JButton addDataDirButton = new JButton("+");
    private final JButton removeDataDirButton = new JButton("-");

    public DataDirPanel() {
        dataDirTable.setModel(dataDirTableModel);
        dataDirTable.doLayout();
        addDataDirButton.addActionListener(this);
        removeDataDirButton.addActionListener(this);

        setLayout(new MigLayout("", "[][grow][][]", "[][grow]"));
        add(new JLabel("Data folders"), "cell 0 0");
        add(addDataDirButton, "cell 2 0");
        add(removeDataDirButton, "cell 3 0");
        add(new JScrollPane(dataDirTable), "cell 0 1 4 1,grow");
    }

    private void addDataDir() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            dataDirTableModel.insertRow(chooser.getSelectedFile().getAbsolutePath(), false);
        }
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
