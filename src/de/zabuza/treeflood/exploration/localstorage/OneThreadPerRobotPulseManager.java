package de.zabuza.treeflood.exploration.localstorage;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of a robot pulse manager that uses one thread for each robot.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class OneThreadPerRobotPulseManager implements IRobotPulseManager {

	/**
	 * The time to wait for the manager in his pulse execution checks in
	 * milliseconds.
	 */
	private static final long WAIT_TIME = 0L;
	/**
	 * The list of robot pulse signal forwarders used by the manager.
	 */
	private List<RobotPulseSignalForwarder> mPulseForwarders;

	/**
	 * The robots to manage.
	 */
	private Collection<Robot> mRobots;

	/**
	 * Initializes the list of pulse forwarder.
	 */
	public void initializePulseForwarders() {
		this.mPulseForwarders = new LinkedList<>();
		for (final Robot robot : this.mRobots) {
			final RobotPulseSignalForwarder forwarder = new RobotPulseSignalForwarder(robot);
			this.mPulseForwarders.add(forwarder);
			forwarder.start();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.IRobotPulseManager#pulse()
	 */
	@Override
	public boolean pulse() {
		// Command a pulse to all forwarders
		for (final RobotPulseSignalForwarder forwarder : this.mPulseForwarders) {
			forwarder.pulse();
		}

		// Wait until all pulses have been executed
		while (true) {
			boolean haveAllPulsesBeenExecuted = true;
			boolean haveAllRobotsTerminated = true;

			// Check the states of all forwarders
			for (final RobotPulseSignalForwarder forwarder : this.mPulseForwarders) {
				// Only check this state if not already false
				if (haveAllRobotsTerminated && !forwarder.hasTerminated()) {
					haveAllRobotsTerminated = false;
				}
				// Only check this state if not already false
				if (haveAllPulsesBeenExecuted && !forwarder.hasFinishedLastPulse()) {
					haveAllPulsesBeenExecuted = false;
				}

				// Both states are known
				if (!haveAllRobotsTerminated && !haveAllPulsesBeenExecuted) {
					break;
				}
			}

			// All pulses have been executed
			if (haveAllPulsesBeenExecuted) {
				return haveAllRobotsTerminated;
			}

			try {
				Thread.sleep(WAIT_TIME);
			} catch (final InterruptedException e) {
				// Simply ignore the interrupt and continue
			}
		}
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
			initializePulseForwarders();
		}
	}

}
