package de.zabuza.treeflood.exploration.localstorage;

import java.util.List;

import de.zabuza.treeflood.tree.ITreeNode;

/**
 * A robot that can explores a tree. All calls to the robot should be done via
 * threads in order to let robots run distributedly. Robots can only communicate
 * over the local storage nodes. Thus they do not have a remove communication
 * channel.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Robot implements Comparable<Robot> {
	/**
	 * The current node the robot is in.
	 */
	private ITreeNode mCurrentNode;
	/**
	 * A list of objects that want to receive events each time a new edge was
	 * explored by this robot.
	 */
	private final List<IExploreEdgeListener> mExploreEdgeListeners;
	/**
	 * The unique id of this robot.
	 */
	private final int mId;
	/**
	 * The object that provides the local storage of nodes.
	 */
	private final LocalStorage mLocalStorage;
	/**
	 * A list of objects that want to receive events each time this robot moves
	 * to another node.
	 */
	private final List<IRobotMovedListener> mRobotMovedListeners;

	/**
	 * Creates a new robot with a unique id starting at the given node.
	 * 
	 * @param id
	 *            The unique id of the robot
	 * @param startingNode
	 *            The node the robot starts in
	 * @param localStorage
	 *            The object that provides the local storage of nodes
	 * @param exploreEdgeListeners
	 *            A list of objects that want to receive events each time a new
	 *            edge was explored by this robot
	 * @param robotMovedListeners
	 *            A list of objects that want to receive events each time this
	 *            robot moves to another node
	 */
	public Robot(final int id, final ITreeNode startingNode, final LocalStorage localStorage,
			final List<IExploreEdgeListener> exploreEdgeListeners,
			final List<IRobotMovedListener> robotMovedListeners) {
		this.mId = id;
		this.mCurrentNode = startingNode;
		this.mLocalStorage = localStorage;
		this.mExploreEdgeListeners = exploreEdgeListeners;
		this.mRobotMovedListeners = robotMovedListeners;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final Robot other) {
		return Integer.compare(this.mId, other.getId());
	}

	/**
	 * Gets the unique id of this robot.
	 * 
	 * @return The unique id of this robot
	 */
	public int getId() {
		return this.mId;
	}

	/**
	 * Gets the tree node the robot is currently located at.
	 * 
	 * @return The tree node the robot is currently located at
	 */
	public ITreeNode getLocation() {
		return this.mCurrentNode;
	}
}
