package de.zabuza.treeflood.tree.util;

import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Interface for tree node stringifier.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface ITreeNodeStringifier {
	/**
	 * Returns a human readable text representation of the given node.
	 * 
	 * @param node
	 *            The node to stringify
	 * @return A human readable text representation of the given node
	 */
	public String nodeToString(final ITreeNode node);
}
