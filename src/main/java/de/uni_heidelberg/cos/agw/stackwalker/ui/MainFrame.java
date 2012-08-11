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

package de.uni_heidelberg.cos.agw.stackwalker.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;
import de.uni_heidelberg.cos.agw.stackwalker.DataSetTreeModel;


public class MainFrame extends JFrame {

	private DataSetTreeModel dataSetTreeModel;
	

	public MainFrame(DataSetTreeModel dataSetTreeModel) {
		this.dataSetTreeModel = dataSetTreeModel;
		setTitle("Luke Stackwalker");
		initUi();
	}

	
	private final void initUi() {
		//getContentPane().setLayout();
		JTabbedPane tabbedPane = new JTabbedPane();		
		add(tabbedPane);
		
		JPanel dataFilePanel = new JPanel();
		dataFilePanel.setLayout(new MigLayout("", "[][grow]", "[][][][grow]"));		
		DataDirPanel dataDirsPanel = new DataDirPanel();
		dataFilePanel.add(dataDirsPanel, "cell 0 0,grow");
		//DataTypeFieldPanel dataFileNameTagsPanel = new DataTypeFieldPanel();
		//dataFilePanel.add(dataFileNameTagsPanel, "cell 0 1,grow");
		DataTypePanel dataTypeTablePanel = new DataTypePanel();
		dataFilePanel.add(dataTypeTablePanel, "cell 0 1,grow");
		ActionPanel actionPanel = new ActionPanel(dataSetTreeModel);
		dataFilePanel.add(actionPanel, "cell 0 2,grow");
		DataFileHierarchyPanel dataFileHierarchyPanel = new DataFileHierarchyPanel(dataSetTreeModel);
		dataFilePanel.add(dataFileHierarchyPanel, "cell 1 0 1 4,grow");
		tabbedPane.add(dataFilePanel, "Data folders and files");
		
		JPanel metadataPanel = new JPanel();
		tabbedPane.add(metadataPanel, "Meta data");
		
		LogPanel logPanel = new LogPanel();
		tabbedPane.add(logPanel, "Log");
	}

}
