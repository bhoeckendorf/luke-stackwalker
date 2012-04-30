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

package de.uni_heidelberg.cos.lukestackwalker;

import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/**
 * Tree model of the data file hierarchy.
 */
public class DataSetTreeModel extends DefaultTreeModel {

	private final DataSetTreeModelNode rootNode = new DataSetTreeModelNode();
	

	public DataSetTreeModel(TreeNode name) {
		super(name);
		// TODO stub
	}

	
	/**
	 * Add a {@link DataFile} to the model.
	 * @param dataFile the DataFile to be added
	 */
	public void add(final DataFile dataFile) {
		DataSetTreeModelNode currentParent = rootNode.add(new DataSetTreeModelNode(rootNode, dataFile.getDataSetName()));
		for (int dataTypeLevel = 0; dataTypeLevel < DataTypeTableModel.getDataTypeCount(true); dataTypeLevel++) {
			int instanceIndex = dataFile.getDataTypeValue(dataTypeLevel);
			currentParent = currentParent.add(new DataSetTreeModelNode(currentParent, dataTypeLevel, instanceIndex));
		}
		currentParent = currentParent.add(new DataSetTreeModelNode(currentParent, dataFile));
	}
	
	
	/**
	 * Updates the model according to the current settings in the UI.
	 * Then updates the UI.
	 */
	public void update() {
		clear();
		populate();
//		List<DataSetTreeModelNode> cs = rootNode.getChildrenList();
//		for (DataSetTreeModelNode c : cs) {
//			System.out.println(c.toString());
//		}
		System.out.println(toString());
		DataSetTreeModelNode[] path = {rootNode};
		int[] childIndices = new int[rootNode.getChildCount()];
		for (int i = 0; i < rootNode.getChildCount(); i++)
			childIndices[i] = i;
		fireTreeStructureChanged(rootNode, path, childIndices, rootNode.getChildrenList().toArray());
	}
	

	/**
	 * Populates the model.
	 */
	private void populate() {
		for (DataDir dataDir : DataDirTableModel.getDataDirs())
			dataDir.insertDataFilesIntoModel(this);
	}


	/**
	 * Clears the model.
	 */
	private void clear() {
		rootNode.getChildrenMap().clear();
		DataSetTreeModelNode[] path = {rootNode};
		int[] childIndices = new int[rootNode.getChildCount()];
		for (int i = 0; i < rootNode.getChildCount(); i++)
			childIndices[i] = i;
		fireTreeStructureChanged(rootNode, path, childIndices, rootNode.getChildrenList().toArray());
	}
	
	
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO stub
	}

	
	@Override
	public Object getChild(Object parent, int index) {
		return ((DataSetTreeModelNode)parent).getChildAt(index);
	}

	
	@Override
	public int getChildCount(Object parent) {
		return ((DataSetTreeModelNode)parent).getChildCount();
	}

	
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return ((DataSetTreeModelNode)parent).getIndex((TreeNode)child);
	}

	
	@Override
	public Object getRoot() {
		return rootNode;
	}
	

	@Override
	public boolean isLeaf(Object node) {
		return ((DataSetTreeModelNode)node).isLeaf();
	}

	
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO stub		
	}

	
	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO stub	
	}


	/**
	 * Returns a String representation of the tree model.
	 * @return a String representation of the tree model
	 */
	@Override
	public String toString() {
		String indent = "  ";
		String debug = new String();
		List<DataSetTreeModelNode> dataSetNodes = rootNode.getChildrenList();
		for (DataSetTreeModelNode currentNode : dataSetNodes) {
			debug += (currentNode + "\n");
			String currentIndent = indent;
			while (!currentNode.getChildAt(0).isLeaf()) {
				for (DataSetTreeModelNode nextNode : currentNode.getChildrenList()) {
					debug += String.format("%s%s\n", currentIndent, nextNode);
					currentIndent += indent;
					currentNode = nextNode;
				}
			}
			for (DataSetTreeModelNode leafNode : currentNode.getChildrenList()) {
				debug += String.format("%s%s\n", currentIndent, leafNode);
			}
		}
		return debug;
	}

}
