package de.zabuza.treeflood.exploration.localstorage;

import java.util.SortedSet;

import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Represents the complete knowledge before the given round from the view of a
 * given node. Used by {@link Robot}s to decide for their actions.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class Knowledge {
	/**
	 * The set of children ports that are advantaged, i.e. they have one more
	 * robot located in them than the other children.
	 */
	private final SortedSet<Integer> mAdvantagedChildrenPorts;
	/**
	 * The node this knowledge belongs to.
	 */
	private final ITreeNode mNode;
	/**
	 * The parent port of the node this knowledge belongs to.
	 */
	private final int mParentPort;
	/**
	 * The set of robots that also are at the node this knowledge belongs to.
	 */
	private final SortedSet<Integer> mRobotsAtLocation;
	/**
	 * The round this knowledge is of.
	 */
	private final int mRound;
	/**
	 * The set of children ports that are unfinished.
	 */
	private final SortedSet<Integer> mUnfinishedChildrenPorts;

	/**
	 * Creates a new knowledge for the given round and the given node.
	 * 
	 * @param round
	 *            The round this knowledge is of
	 * @param node
	 *            The node this knowledge belongs to
	 * @param parentPort
	 *            The parent port of the node this knowledge belongs to
	 * @param unfinishedChildrenPorts
	 *            The set of children ports that are unfinished
	 * @param advantagedChildrenPorts
	 *            The set of children ports that are advantaged, i.e. they have
	 *            one more robot located in them than the other children
	 * @param robotsAtLocation
	 *            The set of robots that also are at the node this knowledge
	 *            belongs to
	 */
	public Knowledge(final int round, final ITreeNode node, final int parentPort,
			final SortedSet<Integer> unfinishedChildrenPorts, final SortedSet<Integer> advantagedChildrenPorts,
			SortedSet<Integer> robotsAtLocation) {
		// TODO Evaluate which sets need to be sorted
		this.mRound = round;
		this.mNode = node;
		this.mParentPort = parentPort;
		this.mUnfinishedChildrenPorts = unfinishedChildrenPorts;
		this.mAdvantagedChildrenPorts = advantagedChildrenPorts;
		this.mRobotsAtLocation = robotsAtLocation;
	}

	/**
	 * Gets the set of children ports that are advantaged, i.e. they have one
	 * more robot located in them than the other children.
	 * 
	 * @return The set of children ports that are advantaged, i.e. they have one
	 *         more robot located in them than the other children
	 */
	public SortedSet<Integer> getAdvantagedChildrenPorts() {
		return this.mAdvantagedChildrenPorts;
	}

	/**
	 * Gets the node this knowledge belongs to.
	 * 
	 * @return The node this knowledge belongs to
	 */
	public ITreeNode getNode() {
		return this.mNode;
	}

	/**
	 * Gets the parent port of the node this knowledge belongs to.
	 * 
	 * @return The parent port of the node this knowledge belongs to
	 */
	public int getParentPort() {
		return this.mParentPort;
	}

	/**
	 * Gets the set of robots that also are at the node this knowledge belongs
	 * to.
	 * 
	 * @return The set of robots that also are at the node this knowledge
	 *         belongs to
	 */
	public SortedSet<Integer> getRobotsAtLocation() {
		return this.mRobotsAtLocation;
	}

	/**
	 * Gets the round this knowledge is of.
	 * 
	 * @return The round this knowledge is of
	 */
	public int getRound() {
		return this.mRound;
	}

	/**
	 * Gets the set of children ports that are unfinished.
	 * 
	 * @return The set of children ports that are unfinished
	 */
	public SortedSet<Integer> getUnfinishedChildrenPorts() {
		return this.mUnfinishedChildrenPorts;
	}
}
