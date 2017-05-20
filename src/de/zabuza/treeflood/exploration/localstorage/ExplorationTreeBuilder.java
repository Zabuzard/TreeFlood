package de.zabuza.treeflood.exploration.localstorage;

import java.util.HashMap;
import java.util.Map;

import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.tree.Tree;

/**
 * Builds an exploration tree on demand. The tree is not backed to the original
 * nodes of the tree that is being explored.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ExplorationTreeBuilder implements IExploreEdgeReceiver {
	/**
	 * The exploration tree. It is not backed to the original nodes of the tree
	 * that is being explored.
	 */
	private final Tree mExploredTree;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.IExploreEdgeReceiver#addEdge
	 * (de.zabuza.treeflood.tree.ITreeNode, de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public void addEdge(final ITreeNode parent, final ITreeNode child) {
		final ITreeNode exploredParent = this.mOriginalToExploredNode.get(parent);
		final ITreeNode exploredChild = this.mExploredTree.addNode(exploredParent);
		this.mOriginalToExploredNode.put(child, exploredChild);
	}

	/**
	 * Gets the exploration tree. It is not backed to the original nodes of the
	 * tree that is being explored.
	 * 
	 * @return The exploration tree
	 */
	public Tree getExploredTree() {
		return this.mExploredTree;
	}
}
