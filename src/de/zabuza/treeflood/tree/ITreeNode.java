package de.zabuza.treeflood.tree;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface for nodes in a tree graph.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface ITreeNode {
	/**
	 * Gets the amount of children this node has.
	 * 
	 * @return The amount of children this node has
	 */
	public int getAmountOfChildren();

	/**
	 * Gets the child of this node specified by the given port number.
	 * 
	 * @param port
	 *            The port number that specifies the child. Children are
	 *            numbered from 1 to the amount of children. Thus port 1
	 *            specifies the first children, port 2 the second and so on.
	 * @return The child node specified by the given port
	 * @throws IllegalArgumentException
	 *             If the given port is not between 1 and the amount of children
	 *             (both inclusive).
	 */
	public ITreeNode getChild(int port) throws IllegalArgumentException;

	/**
	 * Gets an unmodifiable collection containing all children of this node.
	 * 
	 * @return An unmodifiable collection containing all children of this node
	 */
	public Collection<ITreeNode> getChildren();

	/**
	 * Gets the parent node if the current node is not the root.
	 * 
	 * @return The parent node if not the root
	 */
	public Optional<ITreeNode> getParent();

	/**
	 * Whether this node is a leaf node, i.e. whether it has no children.
	 * 
	 * @return <tt>True</tt> if this node is a leaf node, i.e. has no children,
	 *         <tt>false</tt> otherwise
	 */
	public boolean isLeaf();

	/**
	 * Whether this node is the root node, i.e. whether it has no parent node.
	 * 
	 * @return <tt>True</tt> if this node is the root node, i.e. has no parent
	 *         node, <tt>false</tt> otherwise
	 */
	public boolean isRoot();
}
