package de.zabuza.treeflood.exploration.localstorage;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.zabuza.treeflood.tree.ITreeNode;

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
	private final Map<ITreeNode, List<String>> mNodeToStorage;

	/**
	 * Creates a new empty local storage.
	 */
	public LocalStorage() {
		this.mNodeToStorage = new HashMap<>();
	}

	/**
	 * Reads the storage from a given node. The content is unmodifiable.
	 * 
	 * @param node
	 *            The node to read from
	 * @return The storage of the given node as unmodifiable list
	 */
	public List<String> read(final ITreeNode node) {
		return Collections.unmodifiableList(getWithCreateOnInexistent(node));
	}

	/**
	 * Writes to the storage of the given node.
	 * 
	 * @param content
	 *            The content to write
	 * @param node
	 *            The node to write to
	 */
	public void write(final String content, final ITreeNode node) {
		List<String> storage = getWithCreateOnInexistent(node);
		storage.add(content);
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
	private List<String> getWithCreateOnInexistent(final ITreeNode node) {
		final List<String> storage;

		final List<String> currentStorage = this.mNodeToStorage.get(node);
		if (currentStorage == null) {
			final List<String> nextStorage = new LinkedList<>();
			this.mNodeToStorage.put(node, nextStorage);
			storage = nextStorage;
		} else {
			storage = currentStorage;
		}

		return storage;
	}
}
