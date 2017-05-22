package de.zabuza.treeflood.demo;

import de.zabuza.treeflood.tree.ITree;
import de.zabuza.treeflood.tree.RandomTreeGenerator;
import de.zabuza.treeflood.tree.util.HierarchicalTreeStringifier;
import de.zabuza.treeflood.tree.util.ITreeStringifier;
import de.zabuza.treeflood.tree.util.UniqueIdTreeNodeStringifier;

/**
 * Tool that demonstrates the usage of the {@link RandomTreeDemo} for generation
 * of random trees.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RandomTreeDemo {
	/**
	 * Tool that demonstrates the usage of the {@link RandomTreeDemo} for
	 * generation of random trees.
	 * 
	 * @param args
	 *            Not supported
	 */
	public static void main(final String[] args) {
		final RandomTreeGenerator generator = new RandomTreeGenerator(10);
		final ITree tree = generator.generateRandomTree();
		final long seed = generator.getSeedOfLastGeneration().longValue();

		final ITreeStringifier treeStringifier = new HierarchicalTreeStringifier(new UniqueIdTreeNodeStringifier());

		System.out.println("Seed is: " + seed);
		System.out.println("Tree is: ");
		System.out.println(treeStringifier.treeToString(tree));
	}

	/**
	 * Utility class. No implementation.
	 */
	private RandomTreeDemo() {

	}
}
