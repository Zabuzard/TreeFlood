package de.zabuza.treeflood.exploration.localstorage.storage;

import java.util.HashMap;
import java.util.Map;

import de.zabuza.treeflood.exploration.localstorage.Information;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.util.NestedMap2;

/**
 * Provides a local storage for tree nodes. The storage is not thread safe.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class NodeStorageManager implements ILocalStorage {
	/**
	 * Data-structure that maps nodes to their local storage.
	 */
	private final Map<ITreeNode, NestedMap2<Integer, Integer, Information>> mNodeToStorage;

	/**
	 * Creates a new empty local storage.
	 */
	public NodeStorageManager() {
		this.mNodeToStorage = new HashMap<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.storage.ILocalStorage#read(
	 * de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public NestedMap2<Integer, Integer, Information> read(final ITreeNode node) {
		return getWithCreateOnInexistent(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.storage.ILocalStorage#write(
	 * de.zabuza.treeflood.exploration.localstorage.Information,
	 * de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
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
