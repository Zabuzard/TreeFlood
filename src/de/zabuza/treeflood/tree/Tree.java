package de.zabuza.treeflood.tree;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of a tree graph.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Tree implements ITree {
	/**
	 * Set containing all nodes of this tree.
	 */
	private final Set<TreeNode> mNodes;
	/**
	 * The root node of the tree.
	 */
	private final TreeNode mRoot;

	/**
	 * Creates a new tree of size 1 with a root node that is accessible by
	 * {@link #getRoot()}.
	 */
	public Tree() {
		this.mNodes = new HashSet<>();
		this.mRoot = new TreeNode();
		this.mNodes.add(this.mRoot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITree#addNode(de.zabuza.treeflood.tree.
	 * ITreeNode)
	 */
	@Override
	public ITreeNode addNode(final ITreeNode parent) throws IllegalArgumentException {
		if (!containsNode(parent)) {
			throw new IllegalArgumentException();
		}
		final TreeNode child = new TreeNode(parent);
		this.mNodes.add(child);

		// Cast is possible since it is contained in the tree
		final TreeNode parentAsTreeNode = (TreeNode) parent;
		parentAsTreeNode.addChild(child);

		return child;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.tree.ITree#containsNode(de.zabuza.treeflood.tree.
	 * ITreeNode)
	 */
	@Override
	public boolean containsNode(final ITreeNode node) {
		return this.mNodes.contains(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITree#getNodes()
	 */
	@Override
	public Set<ITreeNode> getNodes() {
		return Collections.unmodifiableSet(this.mNodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITree#getRoot()
	 */
	@Override
	public ITreeNode getRoot() {
		return this.mRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITree#getSize()
	 */
	@Override
	public int getSize() {
		return this.mNodes.size();
	}
}
