package de.zabuza.treeflood.exploration.localstorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.tree.Tree;

/**
 * Algorithm that distributedly explores a given tree. The distributed processes
 * are called robots, they move along the nodes of the tree and are only allowed
 * to read and write data to the node they are currently located at. Thus they
 * are limited to a local storages and can not remotely exchange data with each
 * other.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class LocalStorageExploration {
	/**
	 * The object that provides the local storage for nodes.
	 */
	private final LocalStorage mLocalStorage;
	/**
	 * The list of robots.
	 */
	private final ArrayList<Robot> mRobots;
	/**
	 * Builder that is used by the robots to build the exploration tree.
	 */
	private final ExplorationTreeBuilder mTreeBuilder;

	/**
	 * Creates a new instance of a local storage exploration algorithm ready to
	 * explore the tree starting at the given root.
	 * 
	 * @param root
	 *            The root of the tree to explore
	 * @param amountOfRobots
	 *            The amount of robots to use for the distributed exploration
	 */
	public LocalStorageExploration(final ITreeNode root, final int amountOfRobots) {
		this.mLocalStorage = new LocalStorage();
		this.mTreeBuilder = new ExplorationTreeBuilder(root);
		this.mRobots = new ArrayList<>(amountOfRobots);

		// Create robots
		for (int i = 0; i < amountOfRobots; i++) {
			this.mRobots.add(new Robot(i, root, this.mLocalStorage, this.mTreeBuilder));
		}
	}

	/**
	 * Executes the algorithm and explores the tree. The resulting exploration
	 * tree can be accessed with {@link #getExplorationTree()}.
	 */
	public void explore() {
		boolean finished = false;
		while (!finished) {
			finished = exploreOneStep();
		}
	}

	/**
	 * Executes the algorithm only one step and then returns.
	 * 
	 * @return <tt>True</tt> if the algorithm is finished and should not be
	 *         continued anymore, <tt>false</tt> otherwise
	 */
	public boolean exploreOneStep() {
		return true;
	}

	/**
	 * Gets the current exploration tree.
	 * 
	 * @return The current exploration tree
	 */
	public Tree getExplorationTree() {
		return this.mTreeBuilder.getExploredTree();
	}

	/**
	 * Gets an unmodifiable list of the robots used by this algorithm.
	 * 
	 * @return An unmodifiable list of the robots used by this algorithm
	 */
	public List<Robot> getRobots() {
		return Collections.unmodifiableList(this.mRobots);
	}
}
