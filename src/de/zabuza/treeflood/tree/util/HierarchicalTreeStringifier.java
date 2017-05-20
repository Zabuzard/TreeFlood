package de.zabuza.treeflood.tree.util;

import de.zabuza.treeflood.tree.ITree;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * An implementation of a tree stringifier that returns builds a hierarchical
 * text representation of a given tree.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class HierarchicalTreeStringifier implements ITreeStringifier {

	/**
	 * Stringifier to use for tree nodes.
	 */
	private final ITreeNodeStringifier mNodeStringifier;

	/**
	 * Creates a new hierarchical tree stringifier with a default node
	 * stringifier.
	 */
	public HierarchicalTreeStringifier() {
		this(new SimpleTreeNodeStringifier());
	}

	/**
	 * Creates a new hierarchical tree stringifier with the given node
	 * stringifier.
	 * 
	 * @param nodeStringifier
	 *            The stringifier to use for tree nodes
	 */
	public HierarchicalTreeStringifier(final ITreeNodeStringifier nodeStringifier) {
		this.mNodeStringifier = nodeStringifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.tree.util.ITreeStringifier#treeToString(de.zabuza.
	 * treeflood.tree.ITree)
	 */
	@Override
	public String treeToString(final ITree tree) {
		final StringBuilder result = new StringBuilder();
		buildSubTreeString(result, tree.getRoot(), true, "", System.lineSeparator());
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
