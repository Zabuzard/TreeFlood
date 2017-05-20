package de.zabuza.treeflood.tree.util;

import java.util.HashMap;
import java.util.Map;

import de.zabuza.treeflood.tree.ITreeNode;

/**
 * An implementation of a tree node stringifier that returns an unique id for a
 * given node.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class UniqueIdTreeNodeStringifier implements ITreeNodeStringifier {

	/**
	 * A data-structure that maps nodes to their unique id.
	 */
	private final Map<ITreeNode, Integer> mNodeToId;

	/**
	 * Creates a new unique id tree node stringifier.
	 */
	public UniqueIdTreeNodeStringifier() {
		this.mNodeToId = new HashMap<>();
	}

	/**
	 * Returns a unique id for the given node.
	 */
	@Override
	public String nodeToString(final ITreeNode node) {
		final Integer value = this.mNodeToId.get(node);

		final Integer id;
		if (value == null) {
			id = Integer.valueOf(this.mNodeToId.size());
			this.mNodeToId.put(node, id);
		} else {
			id = value;
		}

		return id.toString();
	}

}
