package de.zabuza.treeflood.exploration.localstorage;

import java.util.Set;

import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Represents the complete knowledge before the given round from the view of a
 * given node. Used by {@link Robot}s to decide for their actions.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Knowledge {
	/**
	 * The set of children ports that are advantaged, i.e. they have one more
	 * robot located in them than the other children. The set must be a subset
	 * of the unfinishedChildrenPorts, that means that we only count unfinished
	 * children here. Finished but inhabited children do not count for this set.
	 * It is important that this set is maintained in a sorted order.
	 */
	private final Set<Integer> mAdvantagedChildrenPorts;
	/**
	 * The set of children ports that are finished and not inhabited. It is
	 * important that this set is maintained in a sorted order.
	 */
	private final Set<Integer> mFinishedAndNotInhabitedChildrenPorts;
	/**
	 * The set of children ports that are finished but inhabited. It is
	 * important that this set is maintained in a sorted order.
	 */
	private final Set<Integer> mFinishedButInhabitedChildrenPorts;
	/**
	 * The node this knowledge belongs to.
	 */
	private final ITreeNode mNode;
	/**
	 * The parent port of the node this knowledge belongs to.
	 */
	private final int mParentPort;
	/**
	 * The set of robots that also are at the node this knowledge belongs to. It
	 * is important that this set is maintained in a sorted order.
	 */
	private final Set<Integer> mRobotsAtLocation;
	/**
	 * The round this knowledge is of.
	 */
	private final int mRound;
	/**
	 * The set of children ports that are unfinished. It is important that this
	 * set is maintained in a sorted order.
	 */
	private final Set<Integer> mUnfinishedChildrenPorts;

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
	 *            The set of children ports that are unfinished. It is important
	 *            that this set is in a sorted order.
	 * @param advantagedChildrenPorts
	 *            The set of children ports that are advantaged, i.e. they have
	 *            one more robot located in them than the other children. The
	 *            set must be a subset of the unfinishedChildrenPorts, that
	 *            means that we only count unfinished children here. Finished
	 *            but inhabited children do not count for this set. It is
	 *            important that this set is in a sorted order.
	 * @param finishedButInhabitedChildrenPorts
	 *            The set of children ports that are finished but inhabited. It
	 *            is important that this set is in a sorted order.
	 * @param finishedAndNotInhabitedChildrenPorts
	 *            The set of children ports that are finished and not inhabited.
	 *            It is important that this set is in a sorted order.
	 * @param robotsAtLocation
	 *            The set of robots that also are at the node this knowledge
	 *            belongs to. It is important that this set is in a sorted
	 *            order.
	 */
	public Knowledge(final int round, final ITreeNode node, final int parentPort,
			final Set<Integer> unfinishedChildrenPorts, final Set<Integer> advantagedChildrenPorts,
			final Set<Integer> finishedButInhabitedChildrenPorts,
			final Set<Integer> finishedAndNotInhabitedChildrenPorts, final Set<Integer> robotsAtLocation) {
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
	 * for this set. It is important that this set is maintained in a sorted
	 * order.
	 * 
	 * @return The set of children ports that are advantaged
	 */
	public Set<Integer> getAdvantagedChildrenPorts() {
		return this.mAdvantagedChildrenPorts;
	}

	/**
	 * Gets the set of children ports that are finished and not inhabited. It is
	 * important that this set is maintained in a sorted order.
	 * 
	 * @return The set of children ports that are finished and not inhabited
	 */
	public Set<Integer> getFinishedAndNotInhabitedChildrenPorts() {
		return this.mFinishedAndNotInhabitedChildrenPorts;
	}

	/**
	 * Gets the set of children ports that are finished but inhabited. It is
	 * important that this set is maintained in a sorted order.
	 * 
	 * @return The set of children ports that are finished but inhabited
	 */
	public Set<Integer> getFinishedButInhabitedChildrenPorts() {
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
	 * to. It is important that this set is maintained in a sorted order.
	 * 
	 * @return The set of robots that also are at the node this knowledge
	 *         belongs to
	 */
	public Set<Integer> getRobotsAtLocation() {
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
	 * Gets the set of children ports that are unfinished. It is important that
	 * this set is maintained in a sorted order.
	 * 
	 * @return The set of children ports that are unfinished
	 */
	public Set<Integer> getUnfinishedChildrenPorts() {
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
