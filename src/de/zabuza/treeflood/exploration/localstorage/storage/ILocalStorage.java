package de.zabuza.treeflood.exploration.localstorage.storage;

import de.zabuza.treeflood.exploration.localstorage.Information;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.util.NestedMap2;

/**
 * Interface for objects that provide a local storage for nodes.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface ILocalStorage {
	/**
	 * Reads the storage from a given node. The content is unmodifiable. The
	 * first key is the step of entries, the second key the robot id.
	 * 
	 * @param node
	 *            The node to read from
	 * @return The storage of the given node as unmodifiable map
	 */
	public NestedMap2<Integer, Integer, Information> read(final ITreeNode node);

	/**
	 * Writes to the storage of the given node.
	 * 
	 * @param information
	 *            The information to write
	 * @param node
	 *            The node to write to
	 */
	public void write(final Information information, final ITreeNode node);
}
