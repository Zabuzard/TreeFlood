package de.zabuza.treeflood.exploration.localstorage;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementation of a robot pulse manager that uses throw-away threads for each
 * execution.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ThrowAwayThreadRobotPulseManager implements IRobotPulseManager {

	/**
	 * The robots to manage.
	 */
	private Collection<Robot> mRobots;

	/**
	 * Creates a new throw-away robot pulse manager with initially no robots.
	 * Use {@link #setRobots(Collection)} to set the robots to manage.
	 */
	public ThrowAwayThreadRobotPulseManager() {
		this.mRobots = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.IRobotPulseManager#pulse()
	 */
	@Override
	public boolean pulse() {
		final ExecutorService executor = Executors.newFixedThreadPool(this.mRobots.size());
		final LinkedList<RobotPulse> pulses = new LinkedList<>();

		for (final Robot robot : this.mRobots) {
			final RobotPulse pulse = new RobotPulse(robot);
			pulses.add(pulse);
			executor.execute(pulse);
		}

		executor.shutdown();
		// Wait until all threads have finished
		while (!executor.isTerminated()) {
			try {
				Thread.sleep(5);
			} catch (final InterruptedException e) {
				// Just ignore the interrupt and continue
			}
		}

		// Fetch the result of the pulse
		for (final RobotPulse pulse : pulses) {
			if (!pulse.hasRobotStopped()) {
				// Found a robot that has not stopped yet
				return false;
			}
		}
		// All robots have stopped
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.IRobotPulseManager#setRobots
	 * (java.util.Collection)
	 */
	@Override
	public void setRobots(final Collection<Robot> robots) {
		if (this.mRobots == null) {
			this.mRobots = robots;
		}
	}

}
