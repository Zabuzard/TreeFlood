package de.zabuza.treeflood.exploration.localstorage.listener;

import de.zabuza.treeflood.exploration.localstorage.Robot;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Interface for objects that want to listen to events of type robot moved.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IRobotMovedListener {
	/**
	 * Event listener callback for robot movement events. An implementation
	 * should note that this method will be called from multiple threads at the
	 * same time. It may be necessary to implement the method thread safe.
	 * 
	 * @param robot
	 *            The robot that moved
	 * @param source
	 *            The source the robot moved from
	 * @param destination
	 *            The destination the robot moved to
	 */
	public void movedTo(Robot robot, ITreeNode source, ITreeNode destination);
}
