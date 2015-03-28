package de.uni_heidelberg.cos.agw.stackwalker.ui;

import de.uni_heidelberg.cos.agw.stackwalker.DataSetTreeModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MainFrame extends JFrame {

    private final DataSetTreeModel dataSetTreeModel;

    public MainFrame(final DataSetTreeModel dataSetTreeModel) {
        this.dataSetTreeModel = dataSetTreeModel;
        setTitle("Luke Stackwalker");
        initUi();
    }

    private void initUi() {
        //getContentPane().setLayout();
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane);

        JPanel dataFilePanel = new JPanel();
        dataFilePanel.setLayout(new MigLayout("", "[][grow]", "[][][][grow]"));
        DataDirPanel dataDirsPanel = new DataDirPanel();
        dataFilePanel.add(dataDirsPanel, "cell 0 0,grow");
        //DataTypeFieldPanel dataFileNameTagsPanel = new DataTypeFieldPanel();
        //dataFilePanel.add(dataFileNameTagsPanel, "cell 0 1,grow");
        DataTypePanel dataTypeTablePanel = new DataTypePanel();
        dataFilePanel.add(dataTypeTablePanel, "cell 0 1,grow");
        ActionPanel actionPanel = new ActionPanel(dataSetTreeModel);
        dataFilePanel.add(actionPanel, "cell 0 2,grow");
        DataFileHierarchyPanel dataFileHierarchyPanel = new DataFileHierarchyPanel(dataSetTreeModel);
        dataFilePanel.add(dataFileHierarchyPanel, "cell 1 0 1 4,grow");
        tabbedPane.add(dataFilePanel, "Data folders and files");

        JPanel metadataPanel = new JPanel();
        tabbedPane.add(metadataPanel, "Meta data");

        LogPanel logPanel = new LogPanel();
        tabbedPane.add(logPanel, "Log");
    }
}
