/*
 * This file is part of Luke Stackwalker
 * https://github.com/bhoeckendorf/luke-stackwalker
 * 
 * Copyright 2012 Burkhard Höckendorf <b.hoeckendorf at web dot de>
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.miginfocom.swing.MigLayout;
import de.uni_heidelberg.cos.lukestackwalker.DataTypeTableModel;


public class DataTypePanel extends JPanel implements ActionListener, KeyListener, TableModelListener {
	
	public static DataTypeTableModel dataTypeTableModel = new DataTypeTableModel();
	private JButton
		addButton,
		removeButton,
		upButton,
		downButton;
	private JComboBox<String> presetComboBox;
	private JTable dataTypeTable;
	
	
	public DataTypePanel() {
		initUi();
		dataTypeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		dataTypeTable.doLayout();
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		upButton.addActionListener(this);
		downButton.addActionListener(this);
		presetComboBox.addActionListener(this);
		dataTypeTableModel.addTableModelListener(this);
	}
	

	private final void initUi() {
		setLayout(new MigLayout("", "[][][grow][][]", "[][][][grow]"));

		JLabel filterLabel = new JLabel("Filter");
		JTextField filterEdit = new JTextField(DataTypeTableModel.getFilter());
		filterEdit.addKeyListener(this);
		
		add(filterLabel, "cell 0 0");
		add(filterEdit, "cell 1 0 4 1,growx");

		JLabel presetLabel = new JLabel("Preset");
		presetComboBox = new JComboBox<String>(DataTypeTableModel.getPresetComboBoxModel());
		addButton = new JButton("+");
		removeButton = new JButton("-");
		upButton = new JButton("up");
		downButton = new JButton("down");
		add(presetLabel, "cell 0 1");
		add(presetComboBox, "cell 1 1 4 1,growx");
		add(addButton, "cell 0 2");
		add(removeButton, "cell 1 2");
		add(upButton, "cell 3 2");
		add(downButton, "cell 4 2");
		
		dataTypeTable = new JTable(dataTypeTableModel);
		JScrollPane dataTypeTableScrollPane = new JScrollPane(dataTypeTable);
		add(dataTypeTableScrollPane, "cell 0 3 5 1,grow");
	}


	public void keyTyped(KeyEvent event) {
		String value = ((JTextField)event.getSource()).getText();
		DataTypeTableModel.setFilter(value);
	}
	public void keyPressed(KeyEvent event) {}
	public void keyReleased(KeyEvent event) {}
	
	
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		int rowIdx = dataTypeTable.getSelectedRow();
		if (source == addButton)
			dataTypeTableModel.addRow(rowIdx);
		else if (source == removeButton)
			dataTypeTableModel.removeRow(rowIdx);
		else if (source == upButton) {
			if (dataTypeTableModel.moveUp(rowIdx))
				dataTypeTable.getSelectionModel().setSelectionInterval(rowIdx - 1, rowIdx - 1);
		}
		else if (source == downButton) {
			if (dataTypeTableModel.moveDown(rowIdx))
				dataTypeTable.getSelectionModel().setSelectionInterval(rowIdx + 1, rowIdx + 1);
		}
		else if (source == presetComboBox) {
			int idx = ((JComboBox<String>)source).getSelectedIndex();
			dataTypeTableModel.removeTableModelListener(this);
			dataTypeTableModel.setPreset(idx);
			dataTypeTableModel.addTableModelListener(this);
		}
	}


	public void tableChanged(TableModelEvent event) {
		presetComboBox.setSelectedIndex(0);
	}
	
}
