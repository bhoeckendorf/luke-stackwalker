package de.uni_heidelberg.cos.agw.stackwalker.ui;

import de.uni_heidelberg.cos.agw.stackwalker.DataFileTreeModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class DataFileHierarchyPanel extends JPanel {

    private final JLabel description;
    private DataFileTreeModel dataFileTreeModel;
    private JTree tree;

    public DataFileHierarchyPanel(DataFileTreeModel dataFileTreeModel) {
        this.dataFileTreeModel = dataFileTreeModel;
        tree = new JTree();
        tree.setRootVisible(true);
        tree.setModel(this.dataFileTreeModel);

        description = new JLabel();

        setLayout(new MigLayout("", "[grow]", "[][grow]"));
        add(description, "cell 0 0");
        add(new JScrollPane(tree), "cell 0 1,grow");
    }
}
