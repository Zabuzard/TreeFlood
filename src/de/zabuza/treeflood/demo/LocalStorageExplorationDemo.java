package de.zabuza.treeflood.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.zabuza.treeflood.exploration.localstorage.LocalStorageExploration;
import de.zabuza.treeflood.exploration.localstorage.listener.ExplorationTreeBuilder;
import de.zabuza.treeflood.exploration.localstorage.listener.IRobotMovedListener;
import de.zabuza.treeflood.exploration.localstorage.listener.RobotNodeStringifier;
import de.zabuza.treeflood.exploration.localstorage.storage.NodeStorageManager;
import de.zabuza.treeflood.tree.ITree;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.tree.RandomTreeGenerator;
import de.zabuza.treeflood.tree.util.HierarchicalTreeStringifier;
import de.zabuza.treeflood.tree.util.ITreeStringifier;
import de.zabuza.treeflood.util.MapUtil;

/**
 * Command line tool that demonstrates the execution of the algorithm
 * {@link LocalStorageExploration} step by step.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class LocalStorageExplorationDemo {
	/**
	 * Command line tool that demonstrates the execution of the algorithm
	 * {@link LocalStorageExploration} step by step.
	 * 
	 * @param args
	 *            Not supported
	 */
	public static void main(final String[] args) {
		try (final Scanner scanner = new Scanner(System.in)) {
			// Generate a tree to explore
			System.out.println("Enter a size for a random tree:");
			final int size = Integer.parseInt(scanner.nextLine());
			final RandomTreeGenerator generator = new RandomTreeGenerator(size);

			System.out.println("Enter a seed for the random tree or leave blank:");
			final String seedCandidate = scanner.nextLine();
			final ITree treeToExplore;
			if (seedCandidate.trim().isEmpty()) {
				treeToExplore = generator.generateRandomTree();
				System.out.println("Using seed: " + generator.getSeedOfLastGeneration());
			} else {
				final long seed = Long.parseLong(seedCandidate);
				treeToExplore = generator.generateRandomTree(seed);
			}

			// Create event listeners
			final List<IRobotMovedListener> robotMovedListener = new LinkedList<>();
			final ExplorationTreeBuilder explorationTreeBuilder = new ExplorationTreeBuilder(treeToExplore.getRoot());
			robotMovedListener.add(explorationTreeBuilder);

			final RobotNodeStringifier robotNodeStringifier = new RobotNodeStringifier();
			robotMovedListener.add(robotNodeStringifier);

			// Create an instance of the algorithm
			System.out.println("Enter a number for the amount of robots:");
			final int robots = Integer.parseInt(scanner.nextLine());
			final LocalStorageExploration algorithm = new LocalStorageExploration(treeToExplore.getRoot(), robots,
					new NodeStorageManager(), robotMovedListener);

			// Initialize objects
			robotNodeStringifier.setInitialLocation(algorithm.getRobots());
			final ITreeStringifier treeStringifier = new HierarchicalTreeStringifier(robotNodeStringifier);

			// Start the service
			boolean shouldStop = false;
			int step = 0;
			while (!shouldStop) {
				// Fetch the command
				System.out.println("Enter command: ");
				final String command = scanner.nextLine();

				final int stepToGo;
				if (command.matches("\\d+")) {
					final int stepToGoCandidate = Integer.parseInt(command);
					if (stepToGoCandidate <= step) {
						System.out.println("<int> must be greater than the current step");
						stepToGo = step;
					} else {
						stepToGo = stepToGoCandidate;
					}
				} else if (command.equalsIgnoreCase("n")) {
					stepToGo = step + 1;
				} else if (command.equalsIgnoreCase("r")) {
					stepToGo = step + 3;
				} else if (command.equalsIgnoreCase("q")) {
					shouldStop = true;
					stepToGo = step;
				} else if (command.equalsIgnoreCase("f")) {
					stepToGo = Integer.MAX_VALUE;
				} else {
					System.out.println("Unknown command, available are:");
					System.out.println("\tn - Explore one step");
					System.out.println("\tr - Explore one round, i.e. three steps");
					System.out.println("\t<int> - Explore to step number <int>");
					System.out.println("\tf - Fully explore until the algorithm is finished");
					System.out.println("\tq - Quit");
					stepToGo = step;
				}

				// Execute steps until stepToGo
				boolean isFinished = false;
				final int previousStep = step;
				while (step < stepToGo && !isFinished) {
					isFinished = algorithm.exploreOneStep();
					step++;
				}

				// Print the result
				if (previousStep < step) {
					System.out.println();
					System.out.println("After step " + step + ":");
					System.out.flush();
					final ITree explorationTree = explorationTreeBuilder.getExploredTree();

					// Fiddle the aliases
					final Map<ITreeNode, ITreeNode> originalToExploredNodes = explorationTreeBuilder.getNodeAlias();
					final Map<ITreeNode, ITreeNode> exploredToOriginalNodes = MapUtil
							.reverseMap(originalToExploredNodes);
					robotNodeStringifier.setNodeAlias(exploredToOriginalNodes);

					System.out.println(treeStringifier.treeToString(explorationTree));
				}

				// Stop if algorithm has finished
				if (isFinished) {
					System.out.println("Algorithm has finished, quitting.");
					shouldStop = true;
				}
			}
		}

		System.out.println("Terminated.");
	}

	/**
	 * Utility class. No implementation.
	 */
	private LocalStorageExplorationDemo() {

	}
}
