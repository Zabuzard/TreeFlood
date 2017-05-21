package de.zabuza.treeflood.exploration.localstorage;

import java.util.HashMap;
import java.util.Map;

import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.util.NestedMap2;

/**
 * Provides a local storage for tree nodes. The storage is not thread safe.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class LocalStorage {

	/**
	 * Data-structure that maps nodes to their local storage.
	 */
	private final Map<ITreeNode, NestedMap2<Integer, Integer, Information>> mNodeToStorage;

	/**
	 * Creates a new empty local storage.
	 */
	public LocalStorage() {
		this.mNodeToStorage = new HashMap<>();
	}

	/**
	 * Reads the storage from a given node. The content is unmodifiable. The
	 * first key is the step of entries, the second key the robot id.
	 * 
	 * @param node
	 *            The node to read from
	 * @return The storage of the given node as unmodifiable map
	 */
	public NestedMap2<Integer, Integer, Information> read(final ITreeNode node) {
		return getWithCreateOnInexistent(node);
	}

	/**
	 * Writes to the storage of the given node.
	 * 
	 * @param information
	 *            The information to write
	 * @param node
	 *            The node to write to
	 */
	public void write(final Information information, final ITreeNode node) {
		NestedMap2<Integer, Integer, Information> storage = getWithCreateOnInexistent(node);
		storage.put(Integer.valueOf(information.getStep()), Integer.valueOf(information.getRobotId()), information);
	}

	/**
	 * Gets the storage of the given node. If the node has no storage it will
	 * create an empty storage. The returned object is backed with the storage.
	 * 
	 * @param node
	 *            The node to get the storage from
	 * @return The storage of the given node. The returned object is backed with
	 *         the storage.
	 */
	private NestedMap2<Integer, Integer, Information> getWithCreateOnInexistent(final ITreeNode node) {
		final NestedMap2<Integer, Integer, Information> storage;

		final NestedMap2<Integer, Integer, Information> currentStorage = this.mNodeToStorage.get(node);
		if (currentStorage == null) {
			final NestedMap2<Integer, Integer, Information> nextStorage = new NestedMap2<>();
			this.mNodeToStorage.put(node, nextStorage);
			storage = nextStorage;
		} else {
			storage = currentStorage;
		}

		return storage;
	}
}
