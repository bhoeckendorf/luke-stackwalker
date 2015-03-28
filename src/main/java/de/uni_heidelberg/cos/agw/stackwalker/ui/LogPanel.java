package de.uni_heidelberg.cos.agw.stackwalker.ui;

import javax.swing.*;
import java.awt.*;


public class LogPanel extends JPanel {

    private static JTextArea textArea = new JTextArea();


    public LogPanel() {
        textArea.setEditable(false);
        initUi();
    }


    public static void log(final String message) {
        textArea.append(message);
    }


    private void initUi() {
        setLayout(new BorderLayout());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

}
