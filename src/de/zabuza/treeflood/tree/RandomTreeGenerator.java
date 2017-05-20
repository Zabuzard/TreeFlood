package de.zabuza.treeflood.tree;

import java.util.ArrayList;
import java.util.Random;

import de.zabuza.treeflood.tree.util.UniqueIdTreeNodeStringifier;

/**
 * Generator which produces random tree graphs.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RandomTreeGenerator {
	/**
	 * Demonstrates the usage of the random tree generator.
	 * 
	 * @param args
	 *            Not supported
	 */
	public static void main(final String[] args) {
		final RandomTreeGenerator generator = new RandomTreeGenerator(100);
		final Tree tree = generator.generateRandomTree();
		final long seed = generator.getSeedOfLastGeneration().longValue();

		tree.setNodeStringifier(new UniqueIdTreeNodeStringifier());

		System.out.println("Seed is: " + seed);
		System.out.println("Tree is: ");
		System.out.println(tree);
	}

	/**
	 * The seed of the last generation or <tt>null</tt> if there was no.
	 */
	private Long mLastSeed;

	/**
	 * The size of the trees to produce.
	 */
	private final int mSize;

	/**
	 * Creates a new generator which produces random trees of the given size.
	 * 
	 * @param size
	 *            The size of the trees to produces
	 * @throws IllegalArgumentException
	 *             If the given size is not greater than 0
	 */
	public RandomTreeGenerator(final int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException();
		}
		this.mSize = size;
		this.mLastSeed = null;
	}

	/**
	 * Generates a new random tree. The seed used by the method can be obtained
	 * by {@link #getSeedOfLastGeneration()}.
	 * 
	 * @return The generated random tree
	 */
	public Tree generateRandomTree() {
		return generateRandomTree(System.currentTimeMillis());
	}

	/**
	 * Generates a new random tree with the given seed.
	 * 
	 * @param seed
	 *            The seed to use for generation
	 * @return The generated random tree
	 */
	public Tree generateRandomTree(final long seed) {
		final Random random = new Random(seed);
		this.mLastSeed = Long.valueOf(seed);

		final Tree tree = new Tree();

		final ArrayList<ITreeNode> nodes = new ArrayList<>();
		nodes.add(tree.getRoot());

		// Iteratively add n - 1 new nodes
		for (int i = 0; i < this.mSize - 1; i++) {
			// Select a random node of the tree to become
			// the parent for the new node
			final int parentIndex = random.nextInt(nodes.size());
			final ITreeNode parent = nodes.get(parentIndex);

			final ITreeNode child = tree.addNode(parent);
			nodes.add(child);
		}

		return tree;
	}

	/**
	 * Gets the seed used by the last generation or <tt>null</tt> if there was
	 * no.
	 * 
	 * @return The seed used by the last generation or <tt>null</tt> if there
	 *         was no
	 */
	public Long getSeedOfLastGeneration() {
		return this.mLastSeed;
	}
}
