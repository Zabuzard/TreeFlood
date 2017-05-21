package de.zabuza.treeflood.exploration.localstorage.listener;

import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Interface for objects that want to listen to events of type edge explored.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IExploreEdgeListener {

	/**
	 * Event listen callback for edge exploration events.
	 * 
	 * @param parent
	 *            Parent node of the explored edge
	 * @param child
	 *            Child node of the explored edge
	 */
	public void exploredEdge(ITreeNode parent, ITreeNode child);
}
