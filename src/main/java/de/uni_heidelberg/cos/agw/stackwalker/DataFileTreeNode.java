package de.uni_heidelberg.cos.agw.stackwalker;

import javax.swing.tree.TreeNode;
import java.util.*;

public class DataFileTreeNode implements TreeNode, Comparable<DataFileTreeNode> {

    public final Type nodeType;
    public final int value;
    private final DataType dataType;
    private final DataFile dataFile;
    private final DataFileTreeNode parent;
    private final Map<Integer, DataFileTreeNode> childrenMap;
    private List<DataFileTreeNode> lazyList;

    public DataFileTreeNode() {
        this.parent = null;
        nodeType = Type.ROOT;
        dataType = null;
        value = -1;
        dataFile = null;
        childrenMap = new HashMap<Integer, DataFileTreeNode>();
    }

    public DataFileTreeNode(final DataFileTreeNode parent, final DataType type, final int value) {
        this.parent = parent;
        nodeType = Type.DATATYPE;
        dataType = type;
        this.value = value;
        dataFile = null;
        childrenMap = new HashMap<Integer, DataFileTreeNode>();
    }

    public DataFileTreeNode(final DataFileTreeNode parent, final DataFile dataFile) {
        this.parent = parent;
        nodeType = Type.DATAFILE;
        dataType = null;
        value = -1;
        this.dataFile = dataFile;
        childrenMap = null;
    }

    public DataFile getDataFile() {
        if (nodeType != Type.DATAFILE)
            return null;
        return dataFile;
    }

    public List<DataFileTreeNode> getChildrenList() {
        if (lazyList == null) {
            lazyList = new ArrayList<DataFileTreeNode>(childrenMap.values());
            Collections.sort(lazyList);
        }
        return lazyList;
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
            lazyList = null;
            return node;
        }
    }

    public void add(final DataFile dataFile) {
        childrenMap.put(childrenMap.size(), new DataFileTreeNode(this, dataFile));
        lazyList = null;
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
        return Collections.enumeration(getChildrenList());
    }

    @Override
    public boolean getAllowsChildren() {
        return nodeType != Type.DATAFILE;
    }

    @Override
    public DataFileTreeNode getChildAt(final int index) {
        return getChildrenList().get(index);
    }

    @Override
    public int getChildCount() {
        return childrenMap.size();
    }

    @Override
    public int getIndex(final TreeNode node) {
        return getChildrenList().indexOf(this);
    }

    @Override
    public DataFileTreeNode getParent() {
        return parent;
    }

    @Override
    public boolean isLeaf() {
        return childrenMap == null || childrenMap.isEmpty();
    }

    @Override
    public int compareTo(final DataFileTreeNode other) {
        if (this.nodeType == other.nodeType) {
            if (this.nodeType == Type.DATATYPE) {
                if (this.value > other.value) {
                    return 1;
                } else if (this.value < other.value) {
                    return -1;
                } else {
                    return 0;
                }
            } else if (this.nodeType == Type.DATAFILE) {
                return this.dataFile.getFilePath().compareTo(other.dataFile.getFilePath());
            } else {
                return 0;
            }
        } else if (this.nodeType.ordinal() > other.nodeType.ordinal()) {
            return 1;
        } else if (this.nodeType.ordinal() < other.nodeType.ordinal()) {
            return -1;
        } else {
            return 0;
        }
    }

    public enum Type {ROOT, DATATYPE, DATAFILE}
}
