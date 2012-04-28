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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import net.miginfocom.swing.MigLayout;
import de.uni_heidelberg.cos.lukestackwalker.DataSetTreeModel;


public class DataFileHierarchyPanel extends JPanel {

	private DataSetTreeModel dataSetTreeModel;
	private JTree tree;
	private DefaultMutableTreeNode rootNode;
	
	
	public DataFileHierarchyPanel(DataSetTreeModel dataSetTreeModel) {
		this.dataSetTreeModel = dataSetTreeModel;
		initUi();
		tree.setModel(this.dataSetTreeModel);
	}
	
	
	private final void initUi() {
		setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel description = new JLabel();
		rootNode = new DefaultMutableTreeNode();
		tree = new JTree(rootNode);
		tree.setRootVisible(true);
		JScrollPane treeScrollPane = new JScrollPane(tree);
		
		add(description, "cell 0 0");
		add(treeScrollPane, "cell 0 1,grow");
	}
	
	
	public void update() {
		dataSetTreeModel.update();
	}

}
