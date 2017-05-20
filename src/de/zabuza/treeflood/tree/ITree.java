package de.zabuza.treeflood.tree;

import java.util.Set;

/**
 * Interface for tree graphs.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface ITree {
	/**
	 * Adds a node to the tree which is rooted at the given parent node.
	 * 
	 * @param parent
	 *            The parent of the node to add
	 * @return The added node
	 * @throws IllegalArgumentException
	 *             If the given parent node is not contained in the tree
	 */
	public ITreeNode addNode(final ITreeNode parent) throws IllegalArgumentException;

	/**
	 * Whether the tree contains the given node.
	 * 
	 * @param node
	 *            The node in question
	 * @return <tt>True</tt> if the tree contains the given node, <tt>false</tt>
	 *         if not
	 */
	public boolean containsNode(final ITreeNode node);

	/**
	 * Gets an unmodifiable set of all nodes contained in this tree.
	 * 
	 * @return An unmodifiable set of all nodes contained in this tree
	 */
	public Set<ITreeNode> getNodes();

	/**
	 * Gets the root node of the tree.
	 * 
	 * @return The root node of the tree
	 */
	public ITreeNode getRoot();

	/**
	 * Gets the size of the tree which is the amount of nodes.
	 * 
	 * @return The size, i.e. the amount of nodes, of the graph
	 */
	public int getSize();
}
