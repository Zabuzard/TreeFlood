package de.zabuza.treeflood.exploration.localstorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.zabuza.treeflood.tree.ITree;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.tree.RandomTreeGenerator;
import de.zabuza.treeflood.tree.util.HierarchicalTreeStringifier;
import de.zabuza.treeflood.tree.util.ITreeStringifier;
import de.zabuza.treeflood.util.MapUtil;

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
	 * Creates a new instance of a local storage exploration algorithm ready to
	 * explore the tree starting at the given root.
	 * 
	 * @param root
	 *            The root of the tree to explore
	 * @param amountOfRobots
	 *            The amount of robots to use for the distributed exploration
	 */
	public LocalStorageExploration(final ITreeNode root, final int amountOfRobots) {
		this(root, amountOfRobots, Collections.emptyList(), Collections.emptyList());
	}

	/**
	 * Creates a new instance of a local storage exploration algorithm ready to
	 * explore the tree starting at the given root.
	 * 
	 * @param root
	 *            The root of the tree to explore
	 * @param amountOfRobots
	 *            The amount of robots to use for the distributed exploration
	 * @param exploreEdgeListeners
	 *            A list of objects that want to receive events each time a new
	 *            edge was explored by a robot
	 * @param robotMovedListeners
	 *            A list of objects that want to receive events each time a
	 *            robot moves to another node
	 */
	public LocalStorageExploration(final ITreeNode root, final int amountOfRobots,
			final List<IExploreEdgeListener> exploreEdgeListeners,
			final List<IRobotMovedListener> robotMovedListeners) {
		this.mLocalStorage = new LocalStorage();
		this.mRobots = new ArrayList<>(amountOfRobots);

		// Create robots
		for (int i = 0; i < amountOfRobots; i++) {
			this.mRobots.add(new Robot(i, root, this.mLocalStorage, exploreEdgeListeners, robotMovedListeners));
		}
	}

	/**
	 * Executes the algorithm and explores the tree.
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
	 * Gets an unmodifiable list of the robots used by this algorithm.
	 * 
	 * @return An unmodifiable list of the robots used by this algorithm
	 */
	public List<Robot> getRobots() {
		return Collections.unmodifiableList(this.mRobots);
	}
}
