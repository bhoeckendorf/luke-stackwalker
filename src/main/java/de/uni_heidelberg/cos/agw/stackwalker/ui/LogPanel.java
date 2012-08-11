package de.uni_heidelberg.cos.agw.stackwalker.ui;

import java.awt.BorderLayout;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.JTextArea;


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
