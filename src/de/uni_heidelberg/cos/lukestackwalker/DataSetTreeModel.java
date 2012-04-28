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

package de.uni_heidelberg.cos.lukestackwalker;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


public class DataSetTreeModel extends DefaultTreeModel {

	private DataSetTreeModelNode rootNode = new DataSetTreeModelNode();
	
	
	public DataSetTreeModel(TreeNode name) {
		super(name);
		// TODO stub
	}

	
	public void add(DataFile dataFile) {
		if (!dataFile.isValid())
			return;
		DataSetTreeModelNode currentParent = rootNode.add(new DataSetTreeModelNode(rootNode, dataFile.getDataSetName()));
		for (int dataTypeLevel = 0; dataTypeLevel < DataTypeTableModel.getDataTypeCount(true); dataTypeLevel++) {
			int instanceIndex = dataFile.getDataTypeValue(dataTypeLevel);
			currentParent = currentParent.add(new DataSetTreeModelNode(currentParent, dataTypeLevel, instanceIndex));
		}
		currentParent = currentParent.add(new DataSetTreeModelNode(currentParent, dataFile));
	}
	
	
	public void update() {
		clear();
		getDataFiles();
		String[] foo = {
				"DS0SPC0TL1CHN0PLN0PH0.tif",
				"DS0SPC0TL0CHN0PLN0PH0.tif",
				"DS0SPC0TL0CHN0PLN1PH0.tif",
				"DS1SPC0TL0CHN0PLN0PH0.tif"
		};
		String dataDir = "/home/burkhard/tiffs/";
		for (String f : foo) {
			File file = new File(dataDir + f);
			boolean recursive = false;
			DataFile dataFile = new DataFile(dataDir, recursive, file);
			add(dataFile);
		}
//		List<DataSetTreeModelNode> cs = rootNode.getChildrenList();
//		for (DataSetTreeModelNode c : cs) {
//			System.out.println(c.toString());
//		}
		System.out.println(getDebugString());
		DataSetTreeModelNode[] path = {rootNode};
		int[] childIndices = new int[rootNode.getChildCount()];
		for (int i = 0; i < rootNode.getChildCount(); i++)
			childIndices[i] = i;
		fireTreeStructureChanged(rootNode, path, childIndices, rootNode.getChildrenList().toArray());
	}
	
	
	public void clear() {
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

	
	private void getDataFiles() {
		final List<DataDir> dataDirs = DataDirTableModel.getDataDirs();
		for (DataDir dataDir : dataDirs)
			getDataFiles(dataDir);
	}
	
	
	private void getDataFiles(DataDir dataDir) {
		if (dataDir.isRecursive()) {
			String mainDir = new String();
			try {
				mainDir = dataDir.getPath().getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}				
			getDataFilesRecursively(mainDir, dataDir.getPath());
		}
		getDataFilesNonrecursively(dataDir.getPath());
	}
	
	
	// List absolute paths to files in dir, nonrecursively.
	private void getDataFilesNonrecursively(File dir) {
		String dirPath = new String();
		try {
			dirPath = dir.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		for(File file: dir.listFiles()) {
			if(isTiffFile(file)) {
				DataFile dataFile = new DataFile(dirPath, false, file);
				add(dataFile);
			}
		}
	}

	
	// List absolute paths to files in dir, recursively.
	private void getDataFilesRecursively(String dataDir, File currentDir) {
		System.out.println("Looking for TIFF files in " + currentDir.toString());
		String[] subPaths = currentDir.list();
		for(String subPath: subPaths) {
			File file = new File(currentDir, subPath);
			if(file.isDirectory()) {
				getDataFilesRecursively(dataDir, file);
			} else {
				if(isTiffFile(file)) {
					DataFile dataFile = new DataFile(dataDir, true, file);
					add(dataFile);
				}
			}
		}
	}
	
	
	private boolean isTiffFile(File file) {
		String fileName = file.getName();
		return fileName.endsWith(".tif") || fileName.endsWith(".tiff") || fileName.endsWith(".TIF") || fileName.endsWith(".TIFF");
	}
	
	
	public String getDebugString() {
		String indent = "  ";
		String debug = new String();
		List<DataSetTreeModelNode> dataSetNodes = rootNode.getChildrenList();
		for (DataSetTreeModelNode currentNode : dataSetNodes) {
			debug += (currentNode.toString() + "\n");
			String currentIndent = indent;
			while (!currentNode.getChildAt(0).isLeaf()) {
				for (DataSetTreeModelNode nextNode : currentNode.getChildrenList()) {
					debug += String.format("%s%s\n", currentIndent, nextNode.toString());
					currentIndent += indent;
					currentNode = nextNode;
				}
			}
			for (DataSetTreeModelNode leafNode : currentNode.getChildrenList()) {
				debug += String.format("%s%s\n", currentIndent, leafNode.toString());
			}
		}
		return debug;
	}

}
