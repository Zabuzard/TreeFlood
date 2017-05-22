package de.zabuza.treeflood.tree;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generator which produces uniformly distributed random tree graphs.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RandomTreeGenerator {
	/**
	 * The seed of the last generation or <tt>null</tt> if there was no.
	 */
	private Long mLastSeed;

	/**
	 * The size of the trees to produce.
	 */
	private final int mSize;

	/**
	 * Creates a new generator which produces uniformly distributed random trees
	 * of the given size.
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
	 * Generates a new uniformly distributed random tree. The seed used by the
	 * method can be obtained by {@link #getSeedOfLastGeneration()}.
	 * 
	 * @return The generated random tree
	 */
	public ITree generateRandomTree() {
		return generateRandomTree(System.currentTimeMillis());
	}

	/**
	 * Generates a new uniformly distributed random tree with the given seed.
	 * 
	 * @param seed
	 *            The seed to use for generation
	 * @return The generated random tree
	 */
	public ITree generateRandomTree(final long seed) {
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
