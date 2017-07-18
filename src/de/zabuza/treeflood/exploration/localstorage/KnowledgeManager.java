package de.zabuza.treeflood.exploration.localstorage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.zabuza.treeflood.exploration.localstorage.storage.ILocalStorage;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.util.NestedMap2;

/**
 * Manages the knowledge of nodes for a given {@link Robot}. It offers method to
 * construct knowledge for a given round out of all information available at the
 * {@link ILocalStorage} of a node. It uses caching to speed up the process when
 * visiting a node again.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class KnowledgeManager {
	/**
	 * Determines the action the given robot will perform based on the
	 * underlying knowledge at the given node.
	 * 
	 * @param robotId
	 *            The id of the robot to compute its action
	 * @param knowledge
	 *            The knowledge available at the current node based on which the
	 *            action should be determined
	 * @param node
	 *            The node the given knowledge corresponds to
	 * @return An integer representing the port the given robot will use to
	 *         leave the node. The value {@link Information#STAY_PORT} indicates
	 *         that the robot will not leave the node whereas
	 *         {@link Information#PARENT_PORT} indicates that the robot moves to
	 *         the parent of the given node and {@link Information#STAR_PORT}
	 *         means that the robot stops as it has finished the algorithm.
	 */
	public static int robotAction(final int robotId, final Knowledge knowledge, final ITreeNode node) {
		// Case 1: The node is finished, i.e. all children have finished and are
		// not inhabited
		if (knowledge.getUnfinishedChildrenPorts().isEmpty()
				&& knowledge.getFinishedButInhabitedChildrenPorts().isEmpty()) {
			// Stop if at root, else move to parent
			if (node.isRoot()) {
				return Information.STAR_PORT;
			}
			return Information.PARENT_PORT;
		}

		// Case 2: At least one child is unfinished
		if (!knowledge.getUnfinishedChildrenPorts().isEmpty()) {
			// We distribute all robots to the unfinished children by first
			// assigning as much robots as possible such that each child gets
			// the same amount of robots. After that we assign robots from left
			// to right to the disadvantaged children. If there are some robots
			// left we assign them from left to right starting at the leftmost
			// unfinished child.
			// We now simulate this procedure until the given robot was assigned
			final Set<Integer> robotsAtLocation = knowledge.getRobotsAtLocation();
			final Set<Integer> unfinishedChildren = knowledge.getUnfinishedChildrenPorts();
			final Set<Integer> advantagedChildren = knowledge.getAdvantagedChildrenPorts();
			final Iterator<Integer> robotsToAssign = robotsAtLocation.iterator();

			// Assign robots equally to all unfinished children
			final int amountOfRobotsForEachChild = (int) Math
					.floor((robotsAtLocation.size() + 0.0) / unfinishedChildren.size());
			for (final Integer unfinishedChild : unfinishedChildren) {
				// Simulate the assignment of robots for this child
				for (int i = 0; i < amountOfRobotsForEachChild; i++) {
					final int robotToAssign = robotsToAssign.next().intValue();
					if (robotToAssign == robotId) {
						// The given robot is assigned to this child
						return unfinishedChild.intValue();
					}
				}
			}

			// Now assign robots from left to right starting at the first
			// disadvantaged child
			if (advantagedChildren.size() != unfinishedChildren.size()) {
				// Skip all advantaged children
				final Iterator<Integer> unfinishedChildrenIter = unfinishedChildren.iterator();
				for (int i = 0; i < advantagedChildren.size(); i++) {
					unfinishedChildrenIter.next();
				}
				// Now assign robots to each disadvantaged child
				while (unfinishedChildrenIter.hasNext()) {
					final int child = unfinishedChildrenIter.next().intValue();
					final int robotToAssign = robotsToAssign.next().intValue();
					if (robotToAssign == robotId) {
						// The given robot is assigned to this child
						return child;
					}
				}
			}

			// Start from the leftmost unfinished child and assign the remaining
			// robots
			final Iterator<Integer> unfinishedChildrenIter = unfinishedChildren.iterator();
			while (unfinishedChildrenIter.hasNext()) {
				final int child = unfinishedChildrenIter.next().intValue();
				final int robotToAssign = robotsToAssign.next().intValue();
				if (robotToAssign == robotId) {
					// The given robot is assigned to this child
					return child;
				}
			}

		}

		// Case 3: The children are all finished but at least one is inhabited
		if (knowledge.getUnfinishedChildrenPorts().isEmpty()
				&& !knowledge.getFinishedButInhabitedChildrenPorts().isEmpty()) {
			// Stay at the node and wait for other robots to come
			return Information.STAY_PORT;
		}

		throw new AssertionError();
	}

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
	 *            The information available at the {@link ILocalStorage} of the
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
			for (int pastRound = 1; pastRound <= round; pastRound++) {
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
		// The algorithm can maintain this set ordered without a sorted set
		// structure
		final LinkedHashSet<Integer> unfinishedChildrenPorts = new LinkedHashSet<>();
		for (int i = 1; i <= node.getAmountOfChildren(); i++) {
			// Initially all children are unfinished
			unfinishedChildrenPorts.add(Integer.valueOf(i));
		}
		// Initially there are no advantaged children as all robots must be
		// above the children at its first discovery
		// The algorithm creates this set newly every step so we do not need a
		// sorted set structure now
		final Set<Integer> advantagedChildrenPorts = Collections.emptySet();
		// Initially there are no finished children at all as they have not been
		// visited yet
		// The algorithm can not guarantee a sorted order on this sets so we
		// need a sorted set structure
		final SortedSet<Integer> finishedButInhabitedChildrenPorts = new TreeSet<>();
		final SortedSet<Integer> finishedAndNotInhabitedChildrenPorts = new TreeSet<>();
		final SortedSet<Integer> robotsAtLocation = new TreeSet<>();

		for (final Integer robotId : initialRoundData.keySet()) {
			// All robots that have written to the node in step 1 are in it
			robotsAtLocation.add(robotId);
		}

		final Knowledge initialKnowledge = new Knowledge(initialRound, node, parentPort, unfinishedChildrenPorts,
				advantagedChildrenPorts, finishedButInhabitedChildrenPorts, finishedAndNotInhabitedChildrenPorts,
				robotsAtLocation);
		return initialKnowledge;
	}

	/**
	 * Data-structure that caches past computed knowledge of nodes.
	 */
	private final Map<ITreeNode, Knowledge> mNodeToKnowledgeCache;

	/**
	 * Creates a new knowledge manager that manages knowledge of nodes for a
	 * robot.
	 */
	public KnowledgeManager() {
		this.mNodeToKnowledgeCache = new HashMap<>();
	}

	/**
	 * Constructs the knowledge of the given node for the given round by using
	 * the given information available at the {@link ILocalStorage} of the node.
	 * Uses caching to speed up the process if a node was already visited
	 * before.
	 * 
	 * @param round
	 *            The round to build the knowledge for
	 * @param node
	 *            The node to build the knowledge of
	 * @param localStorage
	 *            The information available at the {@link ILocalStorage} of the
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

			// We add robots that entered the node and delete those that left in
			// this round
			final SortedSet<Integer> robotsAtLocation = new TreeSet<>(pastKnowledge.getRobotsAtLocation());
			// Determine which robots left in this round
			final LinkedList<Integer> robotsLeft = new LinkedList<>();
			for (final int robotId : robotsAtLocation) {
				final int port = robotAction(robotId, pastKnowledge, node);
				if (port != Information.STAY_PORT) {
					// The robot leaves the node
					robotsLeft.add(Integer.valueOf(robotId));
				}
			}
			robotsAtLocation.removeAll(robotsLeft);
			// Determine which robots entered the node
			final Set<Integer> robotsEntered = pastRegularEntries.keySet();
			robotsAtLocation.addAll(robotsEntered);

			// All children from where a robot entered in the UPDATE step are
			// confirmed to have finished.
			// We first assume that a finished child is inhabited.
			// A finished but inhabited child stays inhabited if there did not
			// enter any robot from that child in this round. If there entered
			// any we call the child finished and not inhabited as robots will
			// leave such nodes all together.
			final Set<Integer> unfinishedChildrenPorts = pastKnowledge.getUnfinishedChildrenPorts();
			final Set<Integer> finishedButInhabitedChildrenPorts = pastKnowledge.getFinishedButInhabitedChildrenPorts();
			final Set<Integer> finishedAndNotInhabitedChildrenPorts = pastKnowledge
					.getFinishedAndNotInhabitedChildrenPorts();
			final Set<Integer> advantagedChildrenPortsStart = pastKnowledge.getAdvantagedChildrenPorts();
			for (final Information info : pastUpdateEntries.values()) {
				// The child of that port is now finished
				final Integer port = Integer.valueOf(info.getPort());
				unfinishedChildrenPorts.remove(port);
				advantagedChildrenPortsStart.remove(port);

				// First assume that it also is inhabited
				finishedButInhabitedChildrenPorts.add(port);
			}
			// Check which finished but inhabited children are now not inhabited
			// anymore
			// This is the case if a robot entered from that child in this round
			for (final Integer robotEntered : robotsEntered) {
				final Information info = pastRegularEntries.get(robotEntered);
				if (info.wasEnteredFromParent()) {
					// The robot did not enter from the child
					continue;
				}
				final Integer portOfChild = Integer.valueOf(info.getPort());
				if (finishedButInhabitedChildrenPorts.contains(portOfChild)) {
					// The robot entered from a inhabited child, it is now not
					// inhabited anymore
					finishedButInhabitedChildrenPorts.remove(portOfChild);
					finishedAndNotInhabitedChildrenPorts.add(portOfChild);
				}
			}

			// We compute the complete past distribution of robots to children
			// in order to know which children are advantaged
			final LinkedHashSet<Integer> advantagedChildrenPorts = new LinkedHashSet<>();
			// If all children are finished then obviously there can not be any
			// unfinished advantaged children anymore
			if (!unfinishedChildrenPorts.isEmpty()) {
				// If we have more robots than unfinished children we first
				// distribute that many robots to each child such that each
				// receives the same amount. The remaining amount of robots is
				// given by the modulo.
				final int amountOfRemainingRobots = robotsAtLocation.size() % unfinishedChildrenPorts.size();
				// We begin to distribute robots to the disadvantaged children,
				// from left to right. After that we begin at the leftmost child
				// and assign robots from left to right. We now determine the
				// child position where all robots where assigned. All children
				// left to this position (inclusive) are advantaged now, all to
				// the right are disadvantaged.
				final int amountOfAdvantagedChildren;
				if (amountOfRemainingRobots <= unfinishedChildrenPorts.size() - advantagedChildrenPortsStart.size()) {
					// We have not enough robots to even assign them to all
					// disadvantaged children in the first place
					amountOfAdvantagedChildren = advantagedChildrenPortsStart.size() + amountOfRemainingRobots;
				} else {
					amountOfAdvantagedChildren = amountOfRemainingRobots - unfinishedChildrenPorts.size()
							+ advantagedChildrenPortsStart.size();
				}
				// Iterate unfinished children and determine all advantaged
				// children
				final Iterator<Integer> unfinishedChildrenIter = unfinishedChildrenPorts.iterator();
				for (int i = 1; i <= amountOfAdvantagedChildren; i++) {
					// The child specified by this port is advantaged
					final Integer port = unfinishedChildrenIter.next();
					advantagedChildrenPorts.add(port);
				}
			}

			// Create the knowledge for the next round
			pastKnowledge = new Knowledge(pastRound + 1, node, parentPort, unfinishedChildrenPorts,
					advantagedChildrenPorts, finishedButInhabitedChildrenPorts, finishedAndNotInhabitedChildrenPorts,
					robotsAtLocation);
		}

		// Take the last knowledge built, it is valid for the round 'round'
		final Knowledge currentKnowledge = pastKnowledge;
		if (currentKnowledge.getRound() != round) {
			throw new AssertionError();
		}

		// Put the knowledge into the cache
		this.mNodeToKnowledgeCache.put(node, currentKnowledge);

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
