package de.uni_heidelberg.cos.agw.stackwalker;

import javax.swing.tree.TreeNode;
import java.util.*;

public class DataFileTreeNode implements TreeNode, Comparable<DataFileTreeNode> {

    public final Type nodeType;
    public final int value;
    private final DataType dataType;
    private final DataFile dataFile;
    private final DataFileTreeNode parent;
    private final Map<Integer, DataFileTreeNode> childrenMap = new HashMap<Integer, DataFileTreeNode>();
    private List<DataFileTreeNode> childrenList = new ArrayList<DataFileTreeNode>();

    public DataFileTreeNode() {
        this.parent = null;
        nodeType = Type.ROOT;
        dataType = null;
        value = -1;
        dataFile = null;
    }

    public DataFileTreeNode(final DataFileTreeNode parent, final DataType type, final int value) {
        this.parent = null;
        nodeType = Type.DATATYPE;
        dataType = type;
        this.value = value;
        dataFile = null;
    }

    public DataFileTreeNode(final DataFileTreeNode parent, final DataFile dataFile) {
        this.parent = null;
        nodeType = Type.DATAFILE;
        dataType = null;
        value = -1;
        this.dataFile = dataFile;
    }

    public DataFile getDataFile() {
        if (nodeType != Type.DATAFILE)
            return null;
        return dataFile;
    }

    public List<DataFileTreeNode> getChildrenList() {
        return childrenList;
    }

    public Map<Integer, DataFileTreeNode> getChildrenMap() {
        return childrenMap;
    }

    public DataFileTreeNode add(final DataType type, final int value) {
        if (childrenMap.containsKey(value)) {
            return childrenMap.get(value);
        } else {
            final DataFileTreeNode node = new DataFileTreeNode(this, type, value);
            childrenMap.put(value, node);
            childrenList.add(node);
            Collections.sort(childrenList);
            return node;
        }
    }

    public void add(final DataFile dataFile) {
        childrenList.add(new DataFileTreeNode(this, dataFile));
    }

    @Override
    public String toString() {
        if (nodeType == Type.DATATYPE)
            return String.format("%s %d", dataType.getName(), value);
        else if (nodeType == Type.ROOT)
            return "root";
        else
            return dataFile.getFilePath();
    }

    @Override
    public Enumeration<DataFileTreeNode> children() {
        return Collections.enumeration(childrenList);
    }

    @Override
    public boolean getAllowsChildren() {
        return nodeType != Type.DATAFILE;
    }

    @Override
    public DataFileTreeNode getChildAt(final int index) {
        return childrenList.get(index);
    }

    @Override
    public int getChildCount() {
        return childrenList.size();
    }

    @Override
    public int getIndex(final TreeNode node) {
        return childrenList.indexOf(this);
    }

    @Override
    public DataFileTreeNode getParent() {
        return parent;
    }

    @Override
    public boolean isLeaf() {
        return childrenList.isEmpty();
    }

    @Override
    public int compareTo(final DataFileTreeNode other) {
        if (this.nodeType.ordinal() > other.nodeType.ordinal()) {
            return 1;
        } else if (this.nodeType.ordinal() < other.nodeType.ordinal()) {
            return -1;
        } else {
            if (this.nodeType == Type.DATATYPE) {
                if (this.value > other.value) {
                    return 1;
                } else if (this.value < other.value) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                return this.dataFile.getFilePath().compareTo(other.dataFile.getFilePath());
            }
        }
    }

    public enum Type {ROOT, DATATYPE, DATAFILE}
}
