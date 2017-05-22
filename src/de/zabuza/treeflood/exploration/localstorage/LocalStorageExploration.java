package de.zabuza.treeflood.exploration.localstorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.zabuza.treeflood.exploration.localstorage.listener.IRobotEncounteredExceptionListener;
import de.zabuza.treeflood.exploration.localstorage.listener.IRobotMovedListener;
import de.zabuza.treeflood.exploration.localstorage.storage.ILocalStorage;
import de.zabuza.treeflood.exploration.localstorage.storage.NodeStorageManager;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Algorithm that distributedly explores a given tree. The distributed processes
 * are called robots, they move along the nodes of the tree and are only allowed
 * to read and write data to the node they are currently located at. Thus they
 * are limited to a local storages and can not remotely exchange data with each
 * other. <br>
 * <br>
 * The algorithm was developed by <tt>Fraigniaud Pierre et al.</tt> in their
 * research <a href="http://dx.doi.org/10.1007/978-3-540-24698-5_18">Collective
 * Tree Exploration</a>.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class LocalStorageExploration implements IRobotEncounteredExceptionListener {
	/**
	 * If not <tt>null</tt> it stores the exception a robot encountered.
	 */
	private Throwable mExceptionEncounteredByRobot;

	/**
	 * The object that provides the local storage for nodes.
	 */
	private final ILocalStorage mLocalStorage;

	/**
	 * If not <tt>null</tt> it stores the robot that encountered an exception.
	 */
	private Robot mRobotIdThatEncounteredException;

	/**
	 * The list of robots.
	 */
	private final ArrayList<Robot> mRobots;

	/**
	 * Creates a new instance of a local storage exploration algorithm ready to
	 * explore the tree starting at the given root. The algorithm uses a
	 * centralized manager for the local storage of nodes.
	 * 
	 * @param root
	 *            The root of the tree to explore
	 * @param amountOfRobots
	 *            The amount of robots to use for the distributed exploration
	 */
	public LocalStorageExploration(final ITreeNode root, final int amountOfRobots) {
		this(root, amountOfRobots, new NodeStorageManager(), Collections.emptyList());
	}

	/**
	 * Creates a new instance of a local storage exploration algorithm ready to
	 * explore the tree starting at the given root.
	 * 
	 * @param root
	 *            The root of the tree to explore
	 * @param localStorage
	 *            Object that provides a local storage for nodes
	 * @param amountOfRobots
	 *            The amount of robots to use for the distributed exploration
	 */
	public LocalStorageExploration(final ITreeNode root, final int amountOfRobots, final ILocalStorage localStorage) {
		this(root, amountOfRobots, localStorage, Collections.emptyList());
	}

	/**
	 * Creates a new instance of a local storage exploration algorithm ready to
	 * explore the tree starting at the given root.
	 * 
	 * @param root
	 *            The root of the tree to explore
	 * @param amountOfRobots
	 *            The amount of robots to use for the distributed exploration
	 * @param localStorage
	 *            Object that provides a local storage for nodes
	 * @param robotMovedListeners
	 *            A list of objects that want to receive events each time a
	 *            robot moves to another node
	 */
	public LocalStorageExploration(final ITreeNode root, final int amountOfRobots, final ILocalStorage localStorage,
			final List<IRobotMovedListener> robotMovedListeners) {
		this.mExceptionEncounteredByRobot = null;
		this.mRobotIdThatEncounteredException = null;
		this.mLocalStorage = localStorage;
		this.mRobots = new ArrayList<>(amountOfRobots);

		// Create robots
		for (int i = 0; i < amountOfRobots; i++) {
			this.mRobots
					.add(new Robot(i, root, this.mLocalStorage, robotMovedListeners, Collections.singletonList(this)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.exploration.localstorage.listener.
	 * IRobotEncounteredExceptionListener#encounteredException(de.zabuza.
	 * treeflood.exploration.localstorage.Robot, java.lang.Throwable)
	 */
	@Override
	public synchronized void encounteredException(final Robot robot, final Throwable e) {
		// Only set it once
		if (this.mExceptionEncounteredByRobot == null) {
			this.mExceptionEncounteredByRobot = e;
			this.mRobotIdThatEncounteredException = robot;
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
		// One step always consists of three pulses that execute the tree stages
		// MOVE, WRITE, READ
		for (int i = 0; i < 3; i++) {
			// Stop if all robots have finished after a pulse
			if (pulse()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets an unmodifiable list of the robots used by this algorithm.
	 * 
	 * @return An unmodifiable list of the robots used by this algorithm
	 */
	public List<Robot> getRobots() {
		return Collections.unmodifiableList(this.mRobots);
	}

	/**
	 * Pulses all robots distributedly with groups of {@link Thread}s. This
	 * demands the robots to execute one single step.
	 * 
	 * @return <tt>True</tt> if all robots have stopped because they finished
	 *         the algorithm, <tt>false<tt> otherwise
	 */
	private boolean pulse() {
		final ExecutorService executor = Executors.newFixedThreadPool(this.mRobots.size());
		LinkedList<RobotPulse> pulses = new LinkedList<>();

		for (final Robot robot : this.mRobots) {
			final RobotPulse pulse = new RobotPulse(robot);
			pulses.add(pulse);
			executor.execute(pulse);
		}

		executor.shutdown();
		// Wait until all threads have finished
		while (!executor.isTerminated()) {
			try {
				Thread.sleep(5);
			} catch (final InterruptedException e) {
				// Just ignore the interrupt and continue
			}
		}
		// Fail if a robot encounter an exception
		if (this.mExceptionEncounteredByRobot != null) {
			throw new RobotFailedException(this.mRobotIdThatEncounteredException, this.mExceptionEncounteredByRobot);
		}

		// Fetch the result of the pulse
		for (final RobotPulse pulse : pulses) {
			if (!pulse.hasRobotStopped()) {
				// Found a robot that has not stopped yet
				return false;
			}
		}
		// All robots have stopped
		return true;
	}
}
