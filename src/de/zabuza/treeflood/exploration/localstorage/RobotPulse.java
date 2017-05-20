package de.zabuza.treeflood.exploration.localstorage;

/**
 * Runnable which pulses a given robot if started.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RobotPulse implements Runnable {

	/**
	 * Whether the robot has stopped its execution because it has finished the
	 * algorithm.
	 */
	private boolean mHasRobotStopped;
	/**
	 * The robot to pulse on execution.
	 */
	private final Robot mRobot;

	/**
	 * Creates a new robot pulse which pulses the given robot when started.
	 * 
	 * @param robot
	 *            The robot to pulse
	 */
	public RobotPulse(final Robot robot) {
		this.mRobot = robot;
	}

	/**
	 * Whether the robot has stopped its execution because it has finished the
	 * algorithm.
	 * 
	 * @return <tt>True</tt> if the robot has stopped, <tt>false</tt> otherwise
	 */
	public boolean hasRobotStopped() {
		return this.mHasRobotStopped;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.mHasRobotStopped = this.mRobot.pulse();
	}

}
