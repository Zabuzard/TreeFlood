package de.zabuza.treeflood.tree;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.zabuza.treeflood.tree.util.ITreeNodeStringifier;
import de.zabuza.treeflood.tree.util.SimpleTreeNodeStringifier;

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
	 * Stringifier to use for tree nodes.
	 */
	private ITreeNodeStringifier mNodeStringifier;
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

		this.mNodeStringifier = new SimpleTreeNodeStringifier();
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

	/**
	 * Sets the stringifier to use for nodes.
	 * 
	 * @param stringifier
	 *            The stringifier to set
	 */
	public void setNodeStringifier(final ITreeNodeStringifier stringifier) {
		this.mNodeStringifier = stringifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		buildSubTreeString(result, getRoot(), true, "", System.lineSeparator());
		return result.toString();
	}

	/**
	 * Recursively builds the subtree rooted at the given node in a human
	 * readable text representation.
	 * 
	 * @param builder
	 *            The builder to append the text to
	 * @param node
	 *            The node where the subtree to build is rooted at
	 * @param isTail
	 *            If the subtree is the tail of another tree
	 * @param prefix
	 *            The prefix line for this subtree to create an indentation
	 * @param lineSeparator
	 *            Symbol used to separate lines
	 */
	private void buildSubTreeString(final StringBuilder builder, final ITreeNode node, final boolean isTail,
			final String prefix, final String lineSeparator) {
		builder.append(prefix);
		builder.append("|__");
		builder.append(this.mNodeStringifier.nodeToString(node));
		builder.append(lineSeparator);

		// Recursively iterate all children
		for (int port = 1; port <= node.getAmountOfChildren(); port++) {
			final ITreeNode child = node.getChild(port);
			String prefixForChild = prefix;

			if (isTail) {
				prefixForChild += "    ";
			} else {
				prefixForChild += "|   ";
			}

			// Child is tail if it is the last children to iterate
			final boolean isChildTail = port == node.getAmountOfChildren();
			buildSubTreeString(builder, child, isChildTail, prefixForChild, lineSeparator);
		}
	}
}
