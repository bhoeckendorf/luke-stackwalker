package de.uni_heidelberg.cos.agw.stackwalker;

import de.uni_heidelberg.cos.agw.stackwalker.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class LukeStackwalker {

    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //DataSetModel dataSetModel = new DataSetModel();
                    DataSetTreeModel dataSetTreeModel = new DataSetTreeModel(new DataSetTreeModelNode());
                    MainFrame frame = new MainFrame(dataSetTreeModel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}