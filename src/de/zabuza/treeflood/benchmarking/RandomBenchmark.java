package de.zabuza.treeflood.benchmarking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.zabuza.treeflood.exploration.localstorage.LocalStorageExploration;
import de.zabuza.treeflood.exploration.localstorage.OneThreadPerRobotPulseManager;
import de.zabuza.treeflood.exploration.localstorage.storage.NodeStorageManager;
import de.zabuza.treeflood.tree.ITree;
import de.zabuza.treeflood.tree.RandomTreeGenerator;

/**
 * Used to create benchmarks with random trees.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RandomBenchmark {
	/**
	 * Determines after how many finished trees the benchmark will print a
	 * logging information.
	 */
	private static final int LOG_EVERY = 50;
	/**
	 * Default path where the benchmark data gets saved to.
	 */
	public static final File DATA_PATH = new File(new File(System.getProperty("user.home"), "Desktop"),
			"treeFloodBenchmarkData");

	/**
	 * Creates and executes a benchmark with a fixed size of robots.
	 * 
	 * @throws IOException
	 *             If an I/O-Exception occurs
	 */
	public static void executeBenchmarkFixedRobots() throws IOException {
		// Create a file for the results
		final String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final String fileName = "fixedRobotsBenchmark_" + timeStamp;
		final String fileFormat = ".tsv";

		if (!DATA_PATH.exists()) {
			DATA_PATH.mkdirs();
		} else if (!DATA_PATH.isDirectory()) {
			throw new IllegalArgumentException("The provided path exists but is no directory: " + DATA_PATH);
		}

		final File dataFile = new File(DATA_PATH, fileName + fileFormat);
		try (final FileWriter fw = new FileWriter(dataFile)) {
			final String lineSeparator = System.lineSeparator();
			// Write header
			fw.write("TREE_SIZE\tROBOTS\tTIME\tSTEPS" + lineSeparator);

			final int benchmarkDensity = 500;
			final int amountOfRobots = 100;
			final int minTreeSize = 1;
			final int maxTreeSize = 1_000;
			final int stepWidth = 10;

			for (int treeSize = minTreeSize; treeSize <= maxTreeSize; treeSize += stepWidth) {
				final RandomBenchmark benchmark = new RandomBenchmark(benchmarkDensity, treeSize, amountOfRobots);
				benchmark.executeMeasuring();
				final long overalTime = benchmark.getAverageOverallTime();
				final int amountOfSteps = benchmark.getAverageAmountOfSteps();

				// Log the data in a file
				fw.write(treeSize + "\t" + amountOfRobots + "\t" + overalTime + "\t" + amountOfSteps + lineSeparator);

				System.out.println("From size " + minTreeSize + " to " + maxTreeSize + ", at " + treeSize);
			}
		}
	}

	/**
	 * Creates and executes a benchmark with a scaling amount of robots.
	 * 
	 * @throws IOException
	 *             If an I/O-Exception occurs
	 */
	public static void executeBenchmarkScalingRobots() throws IOException {
		// Create a file for the results
		final String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final String fileName = "scalingRobotsBenchmark_" + timeStamp;
		final String fileFormat = ".tsv";

		if (!DATA_PATH.exists()) {
			DATA_PATH.mkdirs();
		} else if (!DATA_PATH.isDirectory()) {
			throw new IllegalArgumentException("The provided path exists but is no directory: " + DATA_PATH);
		}

		final File dataFile = new File(DATA_PATH, fileName + fileFormat);
		try (final FileWriter fw = new FileWriter(dataFile)) {
			final String lineSeparator = System.lineSeparator();
			// Write header
			fw.write("TREE_SIZE\tROBOTS\tTIME\tSTEPS" + lineSeparator);

			final int benchmarkDensity = 500;
			final float robotCoverage = 0.1f;
			final int minTreeSize = 1;
			final int maxTreeSize = 1_000;
			final int stepWidth = 10;

			for (int treeSize = minTreeSize; treeSize <= maxTreeSize; treeSize += stepWidth) {
				final int amountOfRobots = (int) Math.ceil(treeSize * robotCoverage);
				final RandomBenchmark benchmark = new RandomBenchmark(benchmarkDensity, treeSize, amountOfRobots);
				benchmark.executeMeasuring();
				final long overalTime = benchmark.getAverageOverallTime();
				final int amountOfSteps = benchmark.getAverageAmountOfSteps();

				// Log the data in a file
				fw.write(treeSize + "\t" + amountOfRobots + "\t" + overalTime + "\t" + amountOfSteps + lineSeparator);

				System.out.println("From size " + minTreeSize + " to " + maxTreeSize + ", at " + treeSize);
			}
		}
	}

	/**
	 * Executes benchmarks.
	 * 
	 * @param args
	 *            Not supported
	 * @throws IOException
	 *             If an I/O-Exception occurs
	 */
	public static void main(final String[] args) throws IOException {
		executeBenchmarkFixedRobots();
		// executeBenchmarkScalingRobots();
	}

	/**
	 * The amount of robots to use in this benchmark.
	 */
	private final int mAmountOfRobots;
	/**
	 * The amount of trees used in this benchmark.
	 */
	private final int mAmountOfTrees;
	/**
	 * The total sum of the overall running time.
	 */
	private long mSumOfOverallTime;
	/**
	 * The total sum of the steps used by the algorithm.
	 */
	private int mSumOfSteps;
	/**
	 * The size of the trees to use for this benchmark.
	 */
	private final int mTreeSize;

	/**
	 * Creates a new benchmark with given arguments that uses random trees.
	 * 
	 * @param amountOfTrees
	 *            The amount of trees to use
	 * @param treeSize
	 *            The size of the trees to use
	 * @param amountOfRobots
	 *            The amount of robots to use
	 */
	public RandomBenchmark(final int amountOfTrees, final int treeSize, final int amountOfRobots) {
		this.mAmountOfTrees = amountOfTrees;
		this.mTreeSize = treeSize;
		this.mAmountOfRobots = amountOfRobots;
		reset();
	}

	/**
	 * Executes the measuring of this benchmark.
	 */
	public void executeMeasuring() {
		reset();
		final RandomTreeGenerator generator = new RandomTreeGenerator(this.mTreeSize);

		for (int i = 1; i <= this.mAmountOfTrees; i++) {
			// Generate a random tree and prepare the task
			final ITree treeToExplore = generator.generateRandomTree();
			final LocalStorageExploration algorithm = new LocalStorageExploration(treeToExplore.getRoot(),
					this.mAmountOfRobots, new NodeStorageManager(), new OneThreadPerRobotPulseManager());

			// Start measurement
			final long startTime = System.currentTimeMillis();

			// Execute the task step by step
			boolean finished = false;
			while (!finished) {
				finished = algorithm.exploreOneStep();
				this.mSumOfSteps++;
			}

			// Stop measurement
			final long stopTime = System.currentTimeMillis();
			final long duration = stopTime - startTime;
			this.mSumOfOverallTime += duration;
			if (i % LOG_EVERY == 0) {
				System.out.println("\tFinished " + i + " of " + this.mAmountOfTrees);
			}
		}
	}

	/**
	 * Gets the average amount of steps that was needed in this benchmark.
	 * 
	 * @return The average amount of steps that was needed in this benchmark
	 */
	public int getAverageAmountOfSteps() {
		return this.mSumOfSteps / this.mAmountOfTrees;
	}

	/**
	 * Gets the average overall running time of this benchmark.
	 * 
	 * @return The average overall running time of this benchmark.
	 */
	public long getAverageOverallTime() {
		return this.mSumOfOverallTime / this.mAmountOfTrees;
	}

	/**
	 * Resets the results of the last executed measuring.
	 */
	private void reset() {
		this.mSumOfOverallTime = 0L;
		this.mSumOfSteps = 0;
	}
}
