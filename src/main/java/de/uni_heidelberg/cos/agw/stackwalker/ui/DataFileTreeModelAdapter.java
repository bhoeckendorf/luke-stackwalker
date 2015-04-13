package de.uni_heidelberg.cos.agw.stackwalker.ui;

import de.uni_heidelberg.cos.agw.stackwalker.DataFileTreeModel;
import de.uni_heidelberg.cos.agw.stackwalker.DataFileTreeNode;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class DataFileTreeModelAdapter implements TreeModel {

    private final DataFileTreeModel model;

    public DataFileTreeModelAdapter(final DataFileTreeModel model) {
        this.model = model;
    }

    @Override
    public Object getRoot() {
        return model.getRoot();
    }

    @Override
    public Object getChild(final Object o, final int i) {
        return ((DataFileTreeNode) o).getChildrenList().get(i);
    }

    @Override
    public int getChildCount(final Object o) {
        return ((DataFileTreeNode) o).getChildCount();
    }

    @Override
    public boolean isLeaf(final Object o) {
        return ((DataFileTreeNode) o).isLeaf();
    }

    @Override
    public void valueForPathChanged(final TreePath treePath, final Object o) {

    }

    @Override
    public int getIndexOfChild(final Object o, final Object o1) {
        return ((DataFileTreeNode) o).getIndex((DataFileTreeNode) o1);
    }

    @Override
    public void addTreeModelListener(final TreeModelListener treeModelListener) {

    }

    @Override
    public void removeTreeModelListener(final TreeModelListener treeModelListener) {

    }
}
