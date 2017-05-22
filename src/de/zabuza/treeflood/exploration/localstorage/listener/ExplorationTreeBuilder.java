package de.zabuza.treeflood.exploration.localstorage.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.zabuza.treeflood.exploration.localstorage.Robot;
import de.zabuza.treeflood.tree.ITree;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.tree.Tree;
import de.zabuza.treeflood.util.Pair;

/**
 * Builds an exploration tree on demand. The tree is not backed to the original
 * nodes of the tree that is being explored.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ExplorationTreeBuilder implements IRobotMovedListener {
	/**
	 * The exploration tree. It is not backed to the original nodes of the tree
	 * that is being explored.
	 */
	private final ITree mExploredTree;
	/**
	 * A set containing all edges of the original tree that where already
	 * explored as pair of source to destination. This is used to prevent the
	 * exploration of an already explored edge.
	 */
	private final Set<Pair<ITreeNode, ITreeNode>> mOriginalEdgesExplored;

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
		this.mOriginalEdgesExplored = new HashSet<>();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.listener.IRobotMovedListener
	 * #movedTo(de.zabuza.treeflood.exploration.localstorage.Robot,
	 * de.zabuza.treeflood.tree.ITreeNode, de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public synchronized void movedTo(final Robot robot, final ITreeNode source, final ITreeNode destination) {
		// Reject the edge if robot moved from child to parent as the edge then
		// must already be explored seen from the other side
		if (source.getParent().isPresent() && source.getParent().get().equals(destination)) {
			return;
		}

		// Check if the edge was already added before
		final Pair<ITreeNode, ITreeNode> edge = new Pair<>(source, destination);
		if (this.mOriginalEdgesExplored.contains(edge)) {
			return;
		}

		// Explore the edge
		this.mOriginalEdgesExplored.add(edge);
		addEdge(source, destination);
	}
}
