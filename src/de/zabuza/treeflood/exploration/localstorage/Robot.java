package de.zabuza.treeflood.exploration.localstorage;

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
public final class Robot {
	/**
	 * The current node the robot is in.
	 */
	private ITreeNode mCurrentNode;
	/**
	 * The object that wants to receive events each time a new edge was explored
	 * by this robot.
	 */
	private final IExploreEdgeReceiver mExploreEdgeReceiver;
	/**
	 * The unique id of this robot.
	 */
	private final int mId;
	/**
	 * The object that provides the local storage of nodes.
	 */
	private final LocalStorage mLocalStorage;

	/**
	 * Creates a new robot with a unique id starting at the given node.
	 * 
	 * @param id
	 *            The unique id of the robot
	 * @param startingNode
	 *            The node the robot starts in
	 * @param localStorage
	 *            The object that provides the local storage of nodes
	 * @param exploreEdgeReceiver
	 *            The object that wants to receive events each time a new edge
	 *            was explored by this robot
	 */
	public Robot(final int id, final ITreeNode startingNode, final LocalStorage localStorage,
			IExploreEdgeReceiver exploreEdgeReceiver) {
		this.mId = id;
		this.mCurrentNode = startingNode;
		this.mLocalStorage = localStorage;
		this.mExploreEdgeReceiver = exploreEdgeReceiver;
	}
}
