package de.zabuza.treeflood.tree.util;

import de.zabuza.treeflood.tree.ITree;

/**
 * Interface for tree stringifier.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface ITreeStringifier {
	/**
	 * Returns a human readable text representation of the given tree.
	 * 
	 * @param tree
	 *            The tree to stringify
	 * @return A human readable text representation of the given tree
	 */
	public String treeToString(final ITree tree);
}
