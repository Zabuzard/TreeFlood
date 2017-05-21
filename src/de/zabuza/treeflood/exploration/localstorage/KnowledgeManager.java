package de.zabuza.treeflood.exploration.localstorage;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.util.NestedMap2;

/**
 * Manages the knowledge of nodes for a given {@link Robot}. It offers method to
 * construct knowledge for a given round out of all information available at the
 * {@link LocalStorage} of a node. It uses caching to speed up the process when
 * visiting a node again.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class KnowledgeManager {
	/**
	 * Constructs the initial knowledge available at the given node. Which
	 * corresponds to the round the node was first discovered by a robot.
	 * 
	 * @param round
	 *            The current round which bounds the number of rounds to search
	 *            for an initial knowledge
	 * @param node
	 *            The node to build the initial knowledge of
	 * @param localStorage
	 *            The information available at the {@link LocalStorage} of the
	 *            given node
	 * @return The initial knowledge available at the given node. Which
	 *         corresponds to the round the node was first discovered by a
	 *         robot.
	 */
	private static Knowledge constructInitialKnowledge(final int round, final ITreeNode node,
			final NestedMap2<Integer, Integer, Information> localStorage) {
		final Map<Integer, Information> initialRoundData;
		final int initialRound;

		// Retrieve the initial data to use
		final Map<Integer, Information> initialRoundDataCandidate = localStorage.get(Integer.valueOf(1));
		if (initialRoundDataCandidate != null) {
			// The node is a starting node for some robots which is handled as
			// special case
			initialRoundData = initialRoundDataCandidate;
			initialRound = 1;
		} else {
			// Search for the first entries in the storage of the node
			int startingRound = -1;
			Map<Integer, Information> startingRoundData = null;
			for (int pastRound = 1; pastRound < round; pastRound++) {
				final int pastStep = 3 * pastRound;
				startingRoundData = localStorage.get(Integer.valueOf(pastStep));
				// Found some initial data
				if (startingRoundData != null) {
					startingRound = pastRound;
					break;
				}
			}

			// There must be some entry
			if (startingRoundData == null || startingRound == -1) {
				throw new AssertionError();
			}

			initialRoundData = startingRoundData;
			initialRound = startingRound;
		}

		// Build the initial knowledge out of the initial round data
		// All entries must have the same port, namely the port they used to
		// discover the node in the first place which is also the parent port
		final int parentPort = initialRoundData.values().iterator().next().getPort();
		final SortedSet<Integer> unfinishedChildrenPorts = new TreeSet<>();
		for (int i = 1; i <= node.getAmountOfChildren(); i++) {
			// Initially all children are unfinished
			// XXX Note that we discard the parent pointer here as it is not
			// part of the children
			unfinishedChildrenPorts.add(Integer.valueOf(i));
		}
		// Initially there are no advantaged children as all robots must be
		// above the children at its first discovery
		final SortedSet<Integer> advantagedChildrenPorts = new TreeSet<>();

		final SortedSet<Integer> robotsAtLocation = new TreeSet<>();
		for (final Integer robotId : initialRoundData.keySet()) {
			// All robots that have written to the node in step 1 are in it
			robotsAtLocation.add(robotId);
		}

		final Knowledge initialKnowledge = new Knowledge(initialRound, node, parentPort, unfinishedChildrenPorts,
				advantagedChildrenPorts, robotsAtLocation);
		return initialKnowledge;
	}

	/**
	 * Data-structure that caches past computed knowledge of nodes.
	 */
	private final Map<ITreeNode, Knowledge> mNodeToKnowledgeCache;

	/**
	 * The unique id of the robot to manage knowledge for.
	 */
	private final int mRobotId;

	/**
	 * Creates a new knowledge manager that manages knowledge of nodes for the
	 * given robot.
	 * 
	 * @param robotId
	 *            The unique id of the robot to manage knowledge for
	 */
	public KnowledgeManager(final int robotId) {
		this.mRobotId = robotId;
		this.mNodeToKnowledgeCache = new HashMap<>();
	}

	/**
	 * Constructs the knowledge of the given node for the given round by using
	 * the given information available at the {@link LocalStorage} of the node.
	 * Uses caching to speed up the process if a node was already visited
	 * before.
	 * 
	 * @param round
	 *            The round to build the knowledge for
	 * @param node
	 *            The node to build the knowledge of
	 * @param localStorage
	 *            The information available at the {@link LocalStorage} of the
	 *            given node
	 * @return The knowledge of the given node for the given round
	 */
	public Knowledge constructKnowledge(final int round, final ITreeNode node,
			final NestedMap2<Integer, Integer, Information> localStorage) {
		// We build knowledge iteratively beginning from either a cached version
		// or the round it was first discovered
		final Knowledge startingKnowledge;
		if (this.mNodeToKnowledgeCache.containsKey(node)) {
			startingKnowledge = this.mNodeToKnowledgeCache.get(node);
		} else {
			startingKnowledge = constructInitialKnowledge(round, node, localStorage);
		}

		// Iteratively construct the knowledge up to now
		final int startingRound = startingKnowledge.getRound();
		Knowledge pastKnowledge = startingKnowledge;
		for (int pastRound = startingRound; pastRound < round; pastRound++) {
			// We build the knowledge for round 'pastRound + 1' by using the
			// knowledge of round 'pastRound'. In the end we receive the
			// knowledge for round 'round'.
			final int pastStep = 3 * pastRound;
			final int pastUpdateStep = pastStep + 1;
			Map<Integer, Information> pastRegularEntries = localStorage.get(Integer.valueOf(pastStep));
			Map<Integer, Information> pastUpdateEntries = localStorage.get(Integer.valueOf(pastUpdateStep));
			if (pastRegularEntries == null) {
				pastRegularEntries = Collections.emptyMap();
			}
			if (pastUpdateEntries == null) {
				pastUpdateEntries = Collections.emptyMap();
			}

			// The parent port remains unchanged
			final int parentPort = pastKnowledge.getParentPort();

			// We discard all children where a robot entered in the UPDATE step
			// to confirm that his subtree has finished
			final SortedSet<Integer> unfinishedChildrenPorts = pastKnowledge.getUnfinishedChildrenPorts();
			for (final Information info : pastUpdateEntries.values()) {
				unfinishedChildrenPorts.remove(Integer.valueOf(info.getPort()));
			}

			// We compute the complete past distribution of robots to children
			// in order to know which children are advantaged
			// TODO Implement this
			final SortedSet<Integer> advantagedChildrenPorts = pastKnowledge.getAdvantagedChildrenPorts();

			// We add robots that entered the node and delete those that left in
			// this round
			final SortedSet<Integer> robotsAtLocation = pastKnowledge.getRobotsAtLocation();
			// Determine which robots entered the node
			final Set<Integer> robotsEntered = pastRegularEntries.keySet();
			// Determine which robots left in this round
			// TODO Implement this
			final Set<Integer> robotsLeft = new HashSet<>();
			robotsAtLocation.removeAll(robotsLeft);
			robotsAtLocation.addAll(robotsEntered);

			// Create the knowledge for the next round
			pastKnowledge = new Knowledge(pastRound + 1, node, parentPort, unfinishedChildrenPorts,
					advantagedChildrenPorts, robotsAtLocation);
		}

		// Take the last knowledge built, it is valid for the round 'round'
		final Knowledge currentKnowledge = pastKnowledge;
		if (currentKnowledge.getRound() != round) {
			throw new AssertionError();
		}
		return currentKnowledge;
	}

	/**
	 * Forgets cached knowledge for the given node, if there is one. This method
	 * can be used to optimize the space consumption. Note that once the node is
	 * visited again the construction of knowledge is more time expensive as the
	 * cache was forgotten.
	 * 
	 * @param node
	 *            The node to forget knowledge for
	 */
	public void forgetKnowledgeForNode(final ITreeNode node) {
		this.mNodeToKnowledgeCache.remove(node);
	}
}
