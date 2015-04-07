package de.uni_heidelberg.cos.agw.stackwalker.ui;

import de.uni_heidelberg.cos.agw.stackwalker.DataFileTreeModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    private final DataFileTreeModel dataFileTreeModel;
    private final ActionPanel actionPanel;
    private final DataDirPanel dataDirPanel;

    public MainFrame(final DataFileTreeModel dataFileTreeModel) {
        this.dataFileTreeModel = dataFileTreeModel;
        setTitle("Luke Stackwalker");

        //getContentPane().setLayout();
        final JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane);

        final JPanel dataFilePanel = new JPanel();
        dataFilePanel.setLayout(new MigLayout("", "[][grow]", "[][][][grow]"));
        dataDirPanel = new DataDirPanel();
        dataFilePanel.add(dataDirPanel, "cell 0 0,grow");
        //DataTypeFieldPanel dataFileNameTagsPanel = new DataTypeFieldPanel();
        //dataFilePanel.add(dataFileNameTagsPanel, "cell 0 1,grow");
        final DataTypePanel dataTypeTablePanel = new DataTypePanel();
        dataFilePanel.add(dataTypeTablePanel, "cell 0 1,grow");
        actionPanel = new ActionPanel(this);
        dataFilePanel.add(actionPanel, "cell 0 2,grow");
        final DataFileHierarchyPanel dataFileHierarchyPanel = new DataFileHierarchyPanel(dataFileTreeModel);
        dataFilePanel.add(dataFileHierarchyPanel, "cell 1 0 1 4,grow");
        tabbedPane.add(dataFilePanel, "Data folders and files");

//        final JPanel metadataPanel = new JPanel();
//        tabbedPane.add(metadataPanel, "Meta data");

//        final LogPanel logPanel = new LogPanel();
//        tabbedPane.add(logPanel, "Log");
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final JButton source = (JButton) event.getSource();
        if (source == actionPanel.updateDataFileHierarchyButton) {
            dataFileTreeModel.populate(dataDirPanel.dataDirTableModel.dataDirs, dataDirPanel.getFilter());
        }
    }
}
