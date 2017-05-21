package de.zabuza.treeflood.exploration.localstorage.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.zabuza.treeflood.tree.ITree;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.tree.Tree;

/**
 * Builds an exploration tree on demand. The tree is not backed to the original
 * nodes of the tree that is being explored.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ExplorationTreeBuilder implements IExploreEdgeListener {
	/**
	 * The exploration tree. It is not backed to the original nodes of the tree
	 * that is being explored.
	 */
	private final ITree mExploredTree;
	/**
	 * Data-structure that maps the original nodes to the nodes of the
	 * exploration tree.
	 */
	private final Map<ITreeNode, ITreeNode> mOriginalToExploredNode;

	/**
	 * Creates a new exploration tree builder where only the root is explored
	 * initially.
	 * 
	 * @param root
	 *            The root of the tree
	 */
	public ExplorationTreeBuilder(final ITreeNode root) {
		this.mOriginalToExploredNode = new HashMap<>();
		this.mExploredTree = new Tree();

		this.mOriginalToExploredNode.put(root, this.mExploredTree.getRoot());
	}

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
	public void addEdge(final ITreeNode parent, final ITreeNode child) {
		final ITreeNode exploredParent = this.mOriginalToExploredNode.get(parent);
		final ITreeNode exploredChild = this.mExploredTree.addNode(exploredParent);
		this.mOriginalToExploredNode.put(child, exploredChild);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.exploration.localstorage.IExploreEdgeReceiver#
	 * exploredEdge(de.zabuza.treeflood.tree.ITreeNode,
	 * de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public void exploredEdge(ITreeNode parent, ITreeNode child) {
		addEdge(parent, child);
	}

	/**
	 * Gets the exploration tree. It is not backed to the original nodes of the
	 * tree that is being explored.
	 * 
	 * @return The exploration tree
	 */
	public ITree getExploredTree() {
		return this.mExploredTree;
	}

	/**
	 * Gets an unmodifiable map of the node aliases. It maps the original nodes
	 * to the alias nodes of the exploration tree.
	 * 
	 * @return An unmodifiable map of the node aliases
	 */
	public Map<ITreeNode, ITreeNode> getNodeAlias() {
		return Collections.unmodifiableMap(this.mOriginalToExploredNode);
	}
}
