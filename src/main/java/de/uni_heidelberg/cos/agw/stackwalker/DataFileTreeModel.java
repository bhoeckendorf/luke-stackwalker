package de.uni_heidelberg.cos.agw.stackwalker;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Map;

public class DataFileTreeModel extends DefaultTreeModel {

    private final DataFileTreeNode rootNode;

    public DataFileTreeModel(final DataFileTreeNode rootNode) {
        super(rootNode);
        this.rootNode = rootNode;
    }

    private void addRecursive(final File dirPath, final FileFilter filter) {
        final File[] files = dirPath.listFiles();
        if (files == null) {
            return;
        }
        for (final File file : files) {
            if (file.isDirectory()) {
                addRecursive(file, filter);
            } else {
                if (filter.accept(file)) {
                    final DataFile dataFile = DataFile.create(file);
                    if (dataFile != null)
                        add(dataFile);
                }
            }
        }
    }

    private void addNonRecursive(final File dirPath, final FileFilter filter) {
        for (final File file : dirPath.listFiles(filter)) {
            final DataFile dataFile = DataFile.create(file);
            if (dataFile != null) {
                add(dataFile);
            }
        }
    }

    /**
     * Add a {@link DataFile} to the model.
     *
     * @param dataFile the DataFile to be added
     */
    public void add(final DataFile dataFile) {
        DataFileTreeNode currentParent = rootNode;
        for (final DataType type : DataType.LIST) {
            final int value = dataFile.getValue(type);
            currentParent = currentParent.add(type, value);
        }
        currentParent.add(dataFile);
    }

    public DataFile get(final Map<DataType, Integer> values) {
        DataFileTreeNode current = rootNode;
        for (final DataType type : DataType.LIST) {
            final int value = values.get(type);
            current = current.getChildrenMap().get(value);
        }
        return current.getDataFile();
    }

    public int getFirst(final DataType type) {
        DataFileTreeNode current = rootNode;
        for (final DataType t : DataType.LIST) {
            current = current.getChildrenList().get(0);
            if (t == type) {
                return current.value;
            }
        }
        return -1;
    }

    public int getLast(final DataType type) {
        DataFileTreeNode current = rootNode;
        for (final DataType t : DataType.LIST) {
            current = current.getChildrenList().get(current.getChildrenList().size() - 1);
            if (t == type) {
                return current.value;
            }
        }
        return -1;
    }

    public DataFile getFirstDataFile() {
        DataFileTreeNode current = rootNode;
        for (final DataType t : DataType.LIST) {
            current = current.getChildrenList().get(0);
        }
        return current.getChildrenList().get(0).getDataFile();
    }

    public DataFile getLastDataFile() {
        DataFileTreeNode current = rootNode;
        for (final DataType t : DataType.LIST) {
            current = current.getChildrenList().get(current.getChildrenList().size() - 1);
        }
        return current.getChildrenList().get(current.getChildrenList().size() - 1).getDataFile();
    }

    /**
     * Updates the model according to the current settings in the UI.
     * Then updates the UI.
     */
    public void populate(final List<DataDir> dataDirs, final FileFilter globalFilter) {
        clear();
        for (final DataDir dir : dataDirs) {
            if (dir.isRecursive()) {
                addRecursive(new File(dir.getDirPath()), globalFilter);
            } else {
                addNonRecursive(new File(dir.getDirPath()), globalFilter);
            }
        }
        final DataFileTreeNode[] path = {rootNode};
        final int[] childIndices = new int[rootNode.getChildCount()];
        for (int i = 0; i < rootNode.getChildCount(); i++)
            childIndices[i] = i;
        fireTreeStructureChanged(this, new DataFileTreeNode[]{rootNode}, new int[]{0}, new DataFileTreeNode[]{rootNode});
        fireTreeStructureChanged(rootNode, path, childIndices, rootNode.getChildrenList().toArray());
    }

    /**
     * Clears the model.
     */
    private void clear() {
        rootNode.getChildrenMap().clear();
        rootNode.getChildrenList().clear();
        final DataFileTreeNode[] path = {rootNode};
        final int[] childIndices = new int[rootNode.getChildCount()];
        for (int i = 0; i < rootNode.getChildCount(); i++)
            childIndices[i] = i;
        fireTreeStructureChanged(rootNode, path, childIndices, rootNode.getChildrenList().toArray());
    }

    @Override
    public void addTreeModelListener(final TreeModelListener l) {
    }

    @Override
    public Object getChild(final Object parent, final int index) {
        return ((DataFileTreeNode) parent).getChildAt(index);
    }

    @Override
    public int getChildCount(final Object parent) {
        return ((DataFileTreeNode) parent).getChildCount();
    }

    @Override
    public int getIndexOfChild(final Object parent, final Object child) {
        return ((DataFileTreeNode) parent).getIndex((TreeNode) child);
    }

    @Override
    public Object getRoot() {
        return rootNode;
    }

    @Override
    public boolean isLeaf(final Object node) {
        return ((DataFileTreeNode) node).isLeaf();
    }

    @Override
    public void removeTreeModelListener(final TreeModelListener l) {
    }

    @Override
    public void valueForPathChanged(final TreePath path, final Object newValue) {
    }

    /**
     * Returns a String representation of the tree model.
     *
     * @return a String representation of the tree model
     */
    @Override
    public String toString() {
        String indent = "  ";
        String debug = "";
        List<DataFileTreeNode> dataSetNodes = rootNode.getChildrenList();
        for (DataFileTreeNode currentNode : dataSetNodes) {
            debug += (currentNode + "\n");
            String currentIndent = indent;
            while (!currentNode.getChildAt(0).isLeaf()) {
                for (DataFileTreeNode nextNode : currentNode.getChildrenList()) {
                    debug += String.format("%s%s\n", currentIndent, nextNode);
                    currentIndent += indent;
                    currentNode = nextNode;
                }
            }
            for (DataFileTreeNode leafNode : currentNode.getChildrenList()) {
                debug += String.format("%s%s\n", currentIndent, leafNode);
            }
        }
        return debug;
    }
}
