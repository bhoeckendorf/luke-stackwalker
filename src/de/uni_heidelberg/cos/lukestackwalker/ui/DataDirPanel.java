/*
 * This file is part of Luke Stackwalker.
 * https://github.com/bhoeckendorf/luke-stackwalker
 * 
 * Copyright 2012 Burkhard Hoeckendorf <b.hoeckendorf at web dot de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.uni_heidelberg.cos.lukestackwalker.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import de.uni_heidelberg.cos.lukestackwalker.DataDirTableModel;


/**
 * UI element that allows choosing data folders and specifying whether or not subfolders should be included.
 * 
 * Uses a {@link DataDirTableModel} instance as data model of a {@link JTable}.
 */
public class DataDirPanel extends JPanel implements ActionListener {
	
	private DataDirTableModel dataDirTableModel;
	private JTable dataDirTable;
	private JButton
		addDataDirButton,
		removeDataDirButton;


	public DataDirPanel() {
		dataDirTableModel = new DataDirTableModel();
		initUi();
		dataDirTable.setModel(dataDirTableModel);
		dataDirTable.doLayout();
		addDataDirButton.addActionListener(this);
		removeDataDirButton.addActionListener(this);
	}

	
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton source = (JButton)event.getSource();
		if (source == addDataDirButton) {
			dataDirTableModel.addRow();
		}
		else if (source == removeDataDirButton) {
			int rowIdxs[] = dataDirTable.getSelectedRows();
			for (int rowIdx : rowIdxs)
				dataDirTableModel.removeRow(rowIdx);
		}
	}

	
	private void initUi() {
		setLayout(new MigLayout("", "[][grow][][]", "[][grow]"));

		JLabel dataDirsLabel = new JLabel("Data folders");
		add(dataDirsLabel, "cell 0 0");
		
		addDataDirButton = new JButton("+");
		add(addDataDirButton, "cell 2 0");
		
		removeDataDirButton = new JButton("-");
		add(removeDataDirButton, "cell 3 0");
		
		dataDirTable = new JTable();
		JScrollPane dataDirsTableScrollPane = new JScrollPane(dataDirTable);
		add(dataDirsTableScrollPane, "cell 0 1 4 1,grow");
	}

}