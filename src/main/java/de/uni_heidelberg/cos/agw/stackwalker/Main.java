package de.uni_heidelberg.cos.agw.stackwalker;

import de.uni_heidelberg.cos.agw.stackwalker.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    final DataFileTreeModel dataFileTreeModel = new DataFileTreeModel(new DataFileTreeNode());
                    final MainFrame frame = new MainFrame(dataFileTreeModel);
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