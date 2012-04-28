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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import de.uni_heidelberg.cos.lukestackwalker.DataSetTreeModel;


public class ActionPanel extends JPanel {
	
	private DataSetTreeModel dataSetTreeModel;
	private JButton
		updateDataFileHierarchyButton,
		checkDataSetConsistencyButton,
		startButton;
	private JComboBox<String> actionsComboBox;
	private final String[] actions = {"Move to folder structure", "Convert to multi-page"};
	private JCheckBox
		compressCheckBox,
		updateMetadataCheckBox;
	
	
	public ActionPanel(DataSetTreeModel dataSetTreeModel) {
		this.dataSetTreeModel = dataSetTreeModel;
		initUi();
		ButtonActionListener buttonActionListener = new ButtonActionListener();
		updateDataFileHierarchyButton.addActionListener(buttonActionListener);
		DefaultComboBoxModel<String> actionsComboBoxModel = new DefaultComboBoxModel<String>();
		for (String action : actions)
			actionsComboBoxModel.addElement(action);
		actionsComboBox.setModel(actionsComboBoxModel);
	}
	
	
	private void initUi() {
		setLayout(new MigLayout("", "[grow]", "[][][][][]"));
		
		updateDataFileHierarchyButton = new JButton("Update data file hierarchy");
		add(updateDataFileHierarchyButton, "cell 0 0,grow");
		
		checkDataSetConsistencyButton = new JButton("Check data file hierarchy consistency");
		add(checkDataSetConsistencyButton, "cell 0 1,grow");
		
		actionsComboBox = new JComboBox<String>();
		add(actionsComboBox, "cell 0 2,grow");
		
		compressCheckBox = new JCheckBox("Compress");
		updateMetadataCheckBox = new JCheckBox("Update metadata");
		add(compressCheckBox, "cell 0 3,grow");
		add(updateMetadataCheckBox, "cell 0 3,grow");
		
		startButton = new JButton("Start");
		add(startButton, "cell 0 4,grow");
	}
	
	
	class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if (source == updateDataFileHierarchyButton)
				dataSetTreeModel.update();
		}
	}

}
