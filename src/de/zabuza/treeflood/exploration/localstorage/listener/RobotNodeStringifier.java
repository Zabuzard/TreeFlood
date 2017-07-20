package de.zabuza.treeflood.exploration.localstorage.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeSet;

import de.zabuza.treeflood.exploration.localstorage.Robot;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.tree.util.ITreeNodeStringifier;

/**
 * An implementation of a tree node stringifier that, for a node, returns a text
 * containing all robots currently located in that node.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RobotNodeStringifier implements ITreeNodeStringifier, IRobotMovedListener {
	/**
	 * Data-structure that maps nodes to the robots currently located in them.
	 */
	private final Map<ITreeNode, SortedSet<Robot>> mNodeToRobots;
	/**
	 * Data-structure that maps original nodes to their aliases.
	 */
	private Map<ITreeNode, ITreeNode> mOriginalToAlias;

	/**
	 * Creates a new robot node stringifier. For a node it returns a text
	 * containing all robots currently located in that node. Therefore robots
	 * need to call {@link #movedTo(Robot, ITreeNode, ITreeNode)} when they
	 * move. For their initial position use
	 * {@link #setInitialLocation(Iterable)}.
	 */
	public RobotNodeStringifier() {
		this.mNodeToRobots = new HashMap<>();
		this.mOriginalToAlias = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.IRobotMovedReceiver#movedTo(
	 * de.zabuza.treeflood.exploration.localstorage.Robot,
	 * de.zabuza.treeflood.tree.ITreeNode, de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public synchronized void movedTo(final Robot robot, final ITreeNode source, final ITreeNode destination) {
		// Remove the robot from the source
		if (source != null) {
			final SortedSet<Robot> robotsAtSource = this.mNodeToRobots.get(source);
			if (robotsAtSource != null) {
				robotsAtSource.remove(robot);
			}
		}

		// Add the robot to the destination
		SortedSet<Robot> robotsAtDestination = this.mNodeToRobots.get(destination);
		if (robotsAtDestination == null) {
			robotsAtDestination = new TreeSet<>();
			this.mNodeToRobots.put(destination, robotsAtDestination);
		}
		robotsAtDestination.add(robot);
	}

	/**
	 * Returns a text containing all robots currently located in the given node.
	 */
	@Override
	public String nodeToString(final ITreeNode node) {
		ITreeNode nodeToStringify = node;
		// Use an alias if set
		if (this.mOriginalToAlias != null) {
			final ITreeNode alias = this.mOriginalToAlias.get(node);
			if (alias != null) {
				nodeToStringify = alias;
			}
		}

		final StringJoiner joiner = new StringJoiner(", ", "{", "}");
		final SortedSet<Robot> robotsAtNode = this.mNodeToRobots.get(nodeToStringify);
		if (robotsAtNode != null) {
			for (final Robot robot : robotsAtNode) {
				joiner.add(Integer.toString(robot.getId()));
			}
		}
		return joiner.toString();
	}

	/**
	 * Sets the initial location of each of the given robots.
	 * 
	 * @param robots
	 *            The robots to set the initial location
	 */
	public void setInitialLocation(final Iterable<Robot> robots) {
		for (final Robot robot : robots) {
			movedTo(robot, null, robot.getLocation());
		}
	}

	/**
	 * Sets a node alias map. If used the stringifier will not stringify the
	 * original requested node but its alias.
	 * 
	 * @param originalToAlias
	 *            Data-structure that maps original nodes to their aliases.
	 */
	public void setNodeAlias(final Map<ITreeNode, ITreeNode> originalToAlias) {
		this.mOriginalToAlias = originalToAlias;
	}

}
