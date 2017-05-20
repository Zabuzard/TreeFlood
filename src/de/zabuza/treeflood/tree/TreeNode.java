package de.zabuza.treeflood.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Implementation of tree graph nodes.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class TreeNode implements ITreeNode {

	/**
	 * A list with fast random access containing all children of this node.
	 */
	private final ArrayList<ITreeNode> mChildren;
	/**
	 * The parent of this node or <tt>null</tt> if it is the root.
	 */
	private final ITreeNode mParent;

	/**
	 * Creates a new root node with no children initially.
	 */
	public TreeNode() {
		this(null);
	}

	/**
	 * Creates a new tree node with a given parent but no children initially.
	 * 
	 * @param parent
	 *            The parent of this node, <tt>null</tt> if the node is the root
	 *            node
	 */
	public TreeNode(final ITreeNode parent) {
		this.mParent = parent;
		this.mChildren = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITreeNode#getAmountOfChildren()
	 */
	@Override
	public int getAmountOfChildren() {
		return this.mChildren.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITreeNode#getChild(int)
	 */
	@Override
	public ITreeNode getChild(final int port) throws IllegalArgumentException {
		if (port < 1 || port > getAmountOfChildren()) {
			throw new IllegalArgumentException();
		}
		return this.mChildren.get(port - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITreeNode#getChildren()
	 */
	@Override
	public Collection<ITreeNode> getChildren() {
		return Collections.unmodifiableCollection(this.mChildren);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITreeNode#getParent()
	 */
	@Override
	public Optional<ITreeNode> getParent() {
		if (isRoot()) {
			return Optional.empty();
		}
		return Optional.of(this.mParent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITreeNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		return this.mChildren.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITreeNode#isRoot()
	 */
	@Override
	public boolean isRoot() {
		return this.mParent == null;
	}

	/**
	 * Adds the given node as child to this node. The method is not backed with
	 * the underlying tree structure. It is used as utility method for such a
	 * tree to build the graph on demand.
	 * 
	 * @param child
	 *            The node to add as child of this node
	 */
	void addChild(final ITreeNode child) {
		this.mChildren.add(child);
	}

}
