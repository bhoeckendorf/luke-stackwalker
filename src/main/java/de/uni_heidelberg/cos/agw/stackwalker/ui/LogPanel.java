package de.uni_heidelberg.cos.agw.stackwalker.ui;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {

    private final JTextArea textArea = new JTextArea();

    public LogPanel() {
        textArea.setEditable(false);

        setLayout(new BorderLayout());
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void log(final String message) {
        textArea.append(message);
    }
}
