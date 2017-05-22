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
	 * robot located in them than the other children. The set must be a subset
	 * of the unfinishedChildrenPorts, that means that we only count unfinished
	 * children here. Finished but inhabited children do not count for this set.
	 */
	private final SortedSet<Integer> mAdvantagedChildrenPorts;
	/**
	 * The set of children ports that are finished and not inhabited.
	 */
	private final SortedSet<Integer> mFinishedAndNotInhabitedChildrenPorts;
	/**
	 * The set of children ports that are finished but inhabited.
	 */
	private final SortedSet<Integer> mFinishedButInhabitedChildrenPorts;
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
	 *            one more robot located in them than the other children. The
	 *            set must be a subset of the unfinishedChildrenPorts, that
	 *            means that we only count unfinished children here. Finished
	 *            but inhabited children do not count for this set.
	 * @param finishedButInhabitedChildrenPorts
	 *            The set of children ports that are finished but inhabited.
	 * @param finishedAndNotInhabitedChildrenPorts
	 *            The set of children ports that are finished and not inhabited.
	 * @param robotsAtLocation
	 *            The set of robots that also are at the node this knowledge
	 *            belongs to
	 */
	public Knowledge(final int round, final ITreeNode node, final int parentPort,
			final SortedSet<Integer> unfinishedChildrenPorts, final SortedSet<Integer> advantagedChildrenPorts,
			final SortedSet<Integer> finishedButInhabitedChildrenPorts,
			final SortedSet<Integer> finishedAndNotInhabitedChildrenPorts, SortedSet<Integer> robotsAtLocation) {
		this.mRound = round;
		this.mNode = node;
		this.mParentPort = parentPort;
		this.mUnfinishedChildrenPorts = unfinishedChildrenPorts;
		this.mAdvantagedChildrenPorts = advantagedChildrenPorts;
		this.mFinishedButInhabitedChildrenPorts = finishedButInhabitedChildrenPorts;
		this.mFinishedAndNotInhabitedChildrenPorts = finishedAndNotInhabitedChildrenPorts;
		this.mRobotsAtLocation = robotsAtLocation;
	}

	/**
	 * Gets the set of children ports that are advantaged, i.e. they have one
	 * more robot located in them than the other children. The set must be a
	 * subset of the unfinishedChildrenPorts, that means that we only count
	 * unfinished children here. Finished but inhabited children do not count
	 * for this set.
	 * 
	 * @return The set of children ports that are advantaged
	 */
	public SortedSet<Integer> getAdvantagedChildrenPorts() {
		return this.mAdvantagedChildrenPorts;
	}

	/**
	 * Gets the set of children ports that are finished and not inhabited.
	 * 
	 * @return The set of children ports that are finished and not inhabited
	 */
	public SortedSet<Integer> getFinishedAndNotInhabitedChildrenPorts() {
		return this.mFinishedAndNotInhabitedChildrenPorts;
	}

	/**
	 * Gets the set of children ports that are finished but inhabited.
	 * 
	 * @return The set of children ports that are finished but inhabited
	 */
	public SortedSet<Integer> getFinishedButInhabitedChildrenPorts() {
		return this.mFinishedButInhabitedChildrenPorts;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Knowledge [round=" + this.mRound + ", node=" + this.mNode + ", parentPort=" + this.mParentPort
				+ ", mRobotsAtLocation=" + this.mRobotsAtLocation + ", unfinishedChildrenPorts="
				+ this.mUnfinishedChildrenPorts + ", advantagedChildrenPorts=" + this.mAdvantagedChildrenPorts
				+ ", finishedButInhabitedChildrenPorts=" + this.mFinishedButInhabitedChildrenPorts
				+ ", finishedAndNotInhabitedChildrenPorts=" + this.mFinishedAndNotInhabitedChildrenPorts + "]";
	}
}
