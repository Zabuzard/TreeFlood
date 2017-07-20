package de.zabuza.treeflood.demo.gui.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener;
import de.zabuza.treeflood.demo.gui.view.util.Window;
import de.zabuza.treeflood.tree.ITree;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.util.NestedMap2;

/**
 * Provides a wrapper for any {@link ITree}. Adds extra information to the nodes
 * of the given tree (needed for drawing, e.g.), while not changing the given
 * tree, and is able to {@link CoordinateTree#alignComponents(Window)}, to make
 * the nodes of the given tree have accurate coordinates. Make sure that the
 * given tree is "finished", in a sense that no more elements get added to the
 * tree, otherwise this class may not work as expected.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class CoordinateTree implements ITree {

	/**
	 * A mapping which maps to every depth value the nodes which are present in
	 * that depth. (depth == 0 only contains the root).
	 */
	private final HashMap<Integer, Set<ITreeNode>> mDepthToNodes;

	/**
	 * A list containing every edge of the tree. An edge is explicitly from
	 * source -> destination and not the other way around.
	 */
	private final List<Edge> mEdges;

	/**
	 * A list containing every leaf in the tree.
	 */
	private final List<ITreeNode> mLeafs;

	/**
	 * All the listeners receiving the callbacks
	 * {@link INodeHoverListener#startHover(ITreeNode)} and
	 * {@link INodeHoverListener#stopHover(ITreeNode)}.
	 */
	private final List<INodeHoverListener> mListeners;

	/**
	 * The depth of this tree. (the depth of the root is 0).
	 */
	private int mMaxDepth;

	/**
	 * The mapping for nodes to their extra information, e.g. coordinates,
	 * color, ...
	 */
	private final HashMap<ITreeNode, DrawableNodeData> mNodeMapping;

	/**
	 * Maps every {@link ITreeNode} -> {@link Integer}. The ID gets incremented
	 * for every node we traverse (starting from 0). So the highest ID is
	 * treeSize - 1.
	 */
	private final HashMap<ITreeNode, Integer> mNodeToID;

	/**
	 * The proportions of this tree;
	 */
	private final int mProportions;

	/**
	 * Maps two nodes to their respective edge. The first key is the source of
	 * the edge and the second key is the destination of the edge. Note that
	 * switching first and second key will result in <tt>null</tt> since there
	 * are no "reversed" edges.
	 */
	private final NestedMap2<ITreeNode, ITreeNode, Edge> mSourceDestinationToEdge;

	/**
	 * The given tree, on which this wrapper is based on.
	 */
	private final ITree mTree;

	/**
	 * Constructs a new CoordinateTree, providing a wrapper for the given
	 * {@link ITree}. Adds extra information to the nodes of the given tree
	 * (needed for drawing, e.g.), while not changing the given tree, and is
	 * able to {@link CoordinateTree#alignComponents(Window)}, to make the nodes
	 * of the given tree have accurate coordinates. Make sure that the given
	 * tree is "finished", in a sense that no more elements get added to the
	 * tree, otherwise this class may not work as expected. Also note that
	 * {@link CoordinateTree#initializeData(Window)} should be called before
	 * operating on this tree.
	 * 
	 * @param tree
	 *            The tree on which this coordinate tree should be based.
	 * 
	 * @param listeners
	 *            The hover listeners receiving callbacks from the
	 *            {@link INodeHoverListener} interface.
	 */
	public CoordinateTree(final ITree tree, final List<INodeHoverListener> listeners) {
		this(tree, listeners, DrawableNodeData.DEFAULT_RADIUS);

	}

	/**
	 * Constructs a new CoordinateTree, providing a wrapper for the given
	 * {@link ITree}. Adds extra information to the nodes of the given tree
	 * (needed for drawing, e.g.), while not changing the given tree, and is
	 * able to {@link CoordinateTree#alignComponents(Window)}, to make the nodes
	 * of the given tree have accurate coordinates. Make sure that the given
	 * tree is "finished", in a sense that no more elements get added to the
	 * tree, otherwise this class may not work as expected. Also note that
	 * {@link CoordinateTree#initializeData(Window)} should be called before
	 * operating on this tree.
	 * 
	 * @param tree
	 *            The tree on which this coordinate tree should be based.
	 * 
	 * @param listeners
	 *            The hover listeners receiving callbacks from the
	 *            {@link INodeHoverListener} interface.
	 * 
	 * @param proportions
	 *            the proportions of this tree (size of nodes, edges, e.t.c.)
	 */
	public CoordinateTree(final ITree tree, final List<INodeHoverListener> listeners, final int proportions) {
		this.mTree = tree;
		this.mNodeMapping = new HashMap<>();
		this.mDepthToNodes = new HashMap<>();
		this.mLeafs = new ArrayList<>();
		this.mEdges = new ArrayList<>();
		this.mNodeToID = new HashMap<>();
		this.mSourceDestinationToEdge = new NestedMap2<>();
		this.mListeners = listeners;
		this.mProportions = proportions;
		this.mMaxDepth = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITree#addNode(de.zabuza.treeflood.tree.
	 * ITreeNode)
	 * 
	 */
	@Override
	public ITreeNode addNode(final ITreeNode parent) throws IllegalArgumentException {
		return this.mTree.addNode(parent);
	}

	/**
	 * Aligns every node and edge of this tree for a given {@link Window}. Makes
	 * the spacing between the nodes "pretty", thus sets the coordinates of
	 * every node and edge.
	 * 
	 * @param window
	 *            The window which holds placement data for the part where this
	 *            tree is going to be drawn.
	 */
	public void alignComponents(final Window window) {
		this.initializeData(window);

		final int width = window.getTreePanelSize().width;
		final int height = window.getTreePanelSize().height;

		final int ratio = width / this.mLeafs.size();
		final int spacingForNodes = ratio / 2;

		// first, allocate space for the leafs, and also set their x position,
		// since we don't yet know their y position yet.
		for (int i = 0; i < this.mLeafs.size(); i++) {
			final DrawableNodeData leafData = this.mNodeMapping.get(this.mLeafs.get(i));
			leafData.setX(i * ratio + spacingForNodes);
		}

		// now we start setting the coordinates for the missing nodes. We start
		// from the bottom (the largest depth value for nodes) and work our way
		// to the root.
		for (int i = this.mMaxDepth; i >= 0; i--) {
			final Set<ITreeNode> nodes = this.mDepthToNodes.get(Integer.valueOf(i));

			for (final ITreeNode node : nodes) {
				final DrawableNodeData nodeData = this.mNodeMapping.get(node);
				nodeData.setRadius(this.mProportions);

				nodeData.setY(i * (int) (15f / 100f * height) + nodeData.getRadius() * 2);

				if (!this.mLeafs.contains(node)) {
					nodeData.setX(nodeData.getRelativeXLocation());
				}

				if (node.getParent().isPresent()) {
					final DrawableNodeData parentData = this.mNodeMapping.get(node.getParent().get());
					parentData.setChildX(nodeData.getX());
				}
				if (node.isLeaf()) {
					continue;
				}

				for (int port = 1; port <= node.getAmountOfChildren(); port++) {
					final DrawableNodeData childData = this.mNodeMapping.get(node.getChild(port));

					final Edge edgeToAdd = new Edge(nodeData, childData);
					edgeToAdd.setDescription("" + port);

					this.mEdges.add(edgeToAdd);
					this.mSourceDestinationToEdge.put(node, node.getChild(port), edgeToAdd);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.tree.ITree#containsNode(de.zabuza.treeflood.tree.
	 * ITreeNode)
	 */
	@Override
	public boolean containsNode(final ITreeNode node) {
		return this.mTree.containsNode(node);
	}

	/**
	 * Returns the ({@link ITreeNode}, {@link ITreeNode}) -&gt; {@link Edge}
	 * mapping, which maps two nodes to their respective edge. The first key is
	 * the source of the edge and the second key is the destination of the edge.
	 * Note that switching first and second key will result in <tt>null</tt>
	 * since there are no "reversed" edges.
	 * 
	 * @return The mapping mentioned.
	 */
	public NestedMap2<ITreeNode, ITreeNode, Edge> getEdgeMapping() {
		return this.mSourceDestinationToEdge;

	}

	/**
	 * Gets all the {@link Edge}s of this tree.
	 * 
	 * @return An unmodifiable list view of the edges mentioned..
	 */
	public List<Edge> getEdges() {
		return Collections.unmodifiableList(this.mEdges);

	}

	/**
	 * Returns the {@link ITreeNode} -&gt; {@link DrawableNodeData} mapping,
	 * where {@link DrawableNodeData} holds extra information, e.g. coordinates,
	 * color, ... for the nodes.
	 * 
	 * @return An unmodifiable map view of the mapping mentioned.
	 */
	public Map<ITreeNode, DrawableNodeData> getNodeMapping() {
		return Collections.unmodifiableMap(this.mNodeMapping);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITree#getNodes()
	 */
	@Override
	public Set<ITreeNode> getNodes() {
		return this.mTree.getNodes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITree#getRoot()
	 */
	@Override
	public ITreeNode getRoot() {
		return this.mTree.getRoot();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.tree.ITree#getSize()
	 */
	@Override
	public int getSize() {
		return this.mTree.getSize();

	}

	/**
	 * Calls {@link DrawableNodeData#checkHover(Point, List)} for every node
	 * contained in this tree.
	 * 
	 * @param point
	 *            The position of the mouse.
	 */
	public void setMousePosition(final Point point) {
		for (final ITreeNode node : this.mTree.getNodes()) {
			final DrawableNodeData data = this.mNodeMapping.get(node);
			data.checkHover(point, this.mListeners);
		}
	}

	/**
	 * Initializes the data for this tree. Traverses the whole tree once and
	 * fetches all the data needed to operate appropriately on the given tree.
	 * 
	 * @param window
	 *            the window object used for position related values.
	 */
	private void initializeData(final Window window) {
		final Queue<ITreeNode> queue = new LinkedList<>();
		queue.add(this.mTree.getRoot());

		int nodeID = 0;
		while (!queue.isEmpty()) {
			final ITreeNode node = queue.poll();

			this.mNodeToID.put(node, Integer.valueOf(nodeID));
			nodeID++;

			if (node.isLeaf()) {
				this.mLeafs.add(node);
			}

			for (final ITreeNode child : node.getChildren()) {
				queue.add(child);
			}

			int depth = 0;
			if (!node.isRoot()) {
				// It is a given that the parent of a node is included in the
				// tree, otherwise the tree couldn't have been generated, so
				// this is safe.
				depth = this.mNodeMapping.get(node.getParent().get()).getDepth() + 1;
			}
			this.mNodeMapping.put(node, new DrawableNodeData(depth, node, window));

			if (this.mDepthToNodes.get(Integer.valueOf(depth)) == null) {
				this.mDepthToNodes.put(Integer.valueOf(depth), new HashSet<>());
			}
			this.mDepthToNodes.get(Integer.valueOf(depth)).add(node);

			if (this.mMaxDepth < depth) {
				this.mMaxDepth = depth;
			}
		}
	}
}
