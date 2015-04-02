package de.uni_heidelberg.cos.agw.stackwalker.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel {

    //    private final String[] actions = {};
    protected JButton updateDataFileHierarchyButton;
//            checkDataSetConsistencyButton,
//            startButton;
//    private JComboBox actionsComboBox;

    public ActionPanel(final ActionListener mainListener) {
        updateDataFileHierarchyButton = new JButton("Update data file hierarchy");
        updateDataFileHierarchyButton.addActionListener(mainListener);

//        checkDataSetConsistencyButton = new JButton("Check data file hierarchy consistency");
//        startButton = new JButton("Start");

//        actionsComboBox = new JComboBox();
//        DefaultComboBoxModel actionsComboBoxModel = new DefaultComboBoxModel();
//        for (String action : actions)
//            actionsComboBoxModel.addElement(action);
//        actionsComboBox.setModel(actionsComboBoxModel);

//        setLayout(new MigLayout("", "[grow]", "[][][][]"));
        setLayout(new MigLayout("", "[grow]", "[]"));
        add(updateDataFileHierarchyButton, "cell 0 0,grow");
//        add(checkDataSetConsistencyButton, "cell 0 1,grow");
//        add(actionsComboBox, "cell 0 2,grow");
//        add(startButton, "cell 0 3,grow");
    }
}
