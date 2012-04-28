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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;


public class DataSetTreeModelNode extends DefaultMutableTreeNode {
	
	public static final int ROOT = 0;
	public static final int DATASET = 1;
	public static final int DATATYPE = 2;
	public static final int DATAFILE = 3;
	
	private DataSetTreeModelNode parent;
	public int dataTypeLevel;
	private DataType dataType;
	public int instanceIndex;
	public final int type;
	private DataFile dataFile;
	private String dataSetName;
	
	private Map<String, DataSetTreeModelNode> children = new HashMap<String, DataSetTreeModelNode>();
	
	
	public DataSetTreeModelNode() {
		this.parent = null;
		type = ROOT;
	}
	
	
	public DataSetTreeModelNode(DataSetTreeModelNode parent, String dataSetName) {
		this.parent = parent;
		type = DATASET;
		this.dataSetName = dataSetName;
	}
	
	
	public DataSetTreeModelNode(DataSetTreeModelNode parent, int dataTypeLevel, int instanceIndex) {
		this.parent = parent;
		type = DATATYPE;
		this.dataTypeLevel = dataTypeLevel;
		dataType = DataTypeTableModel.getDataTypeOfLevel(true, dataTypeLevel);
		this.instanceIndex = instanceIndex;
	}
	
	
	public DataSetTreeModelNode(DataSetTreeModelNode parent, DataFile dataFile) {
		this.parent = parent;
		type = DATAFILE;
		this.dataFile = dataFile;
	}
	
	
	@Override
	public String toString() {
		switch (type) {
		case DATASET:
			return dataSetName;
		case DATATYPE:
			return dataType.getName() + " " + instanceIndex;
		case DATAFILE:
			return dataFile.getFilePath();
		default:
			return new String();
		}
	}

	
	@Override
	public Enumeration<DataSetTreeModelNode> children() {
		List<DataSetTreeModelNode> nodes = getChildrenList();
		return Collections.enumeration(nodes);
	}
	
	
	public List<DataSetTreeModelNode> getChildrenList() {
		List<String> keys = new ArrayList<String>(children.keySet());
		Collections.sort(keys);
		List<DataSetTreeModelNode> nodes = new ArrayList<DataSetTreeModelNode>();
		for (String key : keys)
			nodes.add(children.get(key));
		return nodes;
	}
	
	
	public Map<String, DataSetTreeModelNode> getChildrenMap() {
		return children;
	}

	
	@Override
	public boolean getAllowsChildren() {
		if (type == DATAFILE)
			return false;
		return true;
	}

	
	@Override
	public DataSetTreeModelNode getChildAt(int childIndex) {
		if (childIndex >= children.size())
			return null;
		return getChildrenList().get(childIndex);
	}

	
	@Override
	public int getChildCount() {
		return children.size();
	}

	
	@Override
	public int getIndex(TreeNode node) {
		return getChildrenList().indexOf(node.toString());
	}

	
	@Override
	public DataSetTreeModelNode getParent() {
		return parent;
	}

	
	@Override
	public boolean isLeaf() {
		if (getChildCount() > 0)
			return false;
		return true;
	}


	@Override
	public void insert(MutableTreeNode node, int index) {
		add((DataSetTreeModelNode)node);
	}


	public DataSetTreeModelNode add(DataSetTreeModelNode newNode) {
		if (newNode.type < type) {
			System.out.println("Warning: newNode.type < type");
			return null;			
		}
		
		String newNodeString = newNode.toString();

		if (newNode.type == DATASET) {
			if (children.containsKey(newNodeString))
				return children.get(newNodeString);
			children.put(newNodeString, newNode);
			return newNode;
		}
		
		else if (newNode.type == DATATYPE) {
			if (type == DATASET && newNode.dataTypeLevel != 0) {
				System.out.println("Warning: type == DATASET && newNode.dataTypeLevel != 0");
				return null;
			}
			if (newNode.dataTypeLevel != 0 && newNode.dataTypeLevel != dataTypeLevel + 1) {
				System.out.println("Warning: newNode.dataTypeLevel != dataTypeLevel + 1");
				return null;
			}
			if (children.containsKey(newNodeString))
				return children.get(newNodeString);
			children.put(newNodeString, newNode);
			return newNode;
		}

		else if (newNode.type == DATAFILE) {
			if (dataTypeLevel != DataTypeTableModel.getDataTypeCount(true) -1) {
				System.out.println("Warning: dataTypeLevel != DataTypeModel.getDataTypes().size() -1");
				return null;
			}
			if (children.containsKey(newNodeString)) {
				System.out.println("Warning: DataFile already in model");
				return children.get(newNodeString);
			}
			children.put(newNodeString, newNode);
			return newNode;
		}
		
		else
			return null;
	}
	
	
	@Override
	public void remove(int index) {
		if (index == -1)
			return;
		children.remove(index);
	}


	@Override
	public void remove(MutableTreeNode node) {
		int index = getIndex(node);
		remove(index);
	}


	@Override
	public void removeFromParent() {
		int index = parent.getIndex(this);
		parent.remove(index);
	}


	@Override
	public void setParent(MutableTreeNode newParent) {
		if (parent != null)
			removeFromParent();
		newParent.insert(this, 0);
	}


	@Override
	public void setUserObject(Object object) {
	}

}
