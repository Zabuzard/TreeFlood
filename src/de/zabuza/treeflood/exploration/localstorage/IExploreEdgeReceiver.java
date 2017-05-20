package de.zabuza.treeflood.exploration.localstorage;

import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Interface for objects that want to receive events of type edge explored.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IExploreEdgeReceiver {

	/**
	 * Explores the given edge by adding it to the exploration tree.
	 * 
	 * @param parent
	 *            The parent node of the edge which must be contained in the
	 *            tree already
	 * @param child
	 *            The child node of the edge which must not be contained in the
	 *            tree already
	 */
	public void addEdge(ITreeNode parent, ITreeNode child);
}
