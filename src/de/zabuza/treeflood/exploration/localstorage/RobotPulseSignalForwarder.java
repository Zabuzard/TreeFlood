package de.zabuza.treeflood.exploration.localstorage;

/**
 * A forwarder that forwards pulse commands to a given robot. Use
 * {@link #pulse()} to demand a pulse and {@link #hasFinishedLastPulse()} to
 * check the state.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RobotPulseSignalForwarder extends Thread {
	/**
	 * The time to wait for the forwarder in his life cycle in milliseconds.
	 */
	private static final long WAIT_TIME = 0L;
	/**
	 * Whether the pulse forwarder should forward a pulse to its robot.
	 */
	private boolean mDoPulse;
	/**
	 * The robot to forward pulses to
	 */
	private final Robot mRobot;
	/**
	 * Whether the pulse forwarder should stop.
	 */
	private boolean mShouldStop;

	/**
	 * Creates a new robot pulse signal forwarder that forwards pulse commands
	 * to the given robot. Use {@link #pulse()} to demand a pulse and
	 * {@link #hasFinishedLastPulse()} to check the state.
	 * 
	 * @param robot
	 *            The robot to forward pulses to
	 */
	public RobotPulseSignalForwarder(final Robot robot) {
		this.mRobot = robot;
		this.mShouldStop = false;
		this.mDoPulse = false;
	}

	/**
	 * Whether the robot has finished the last commanded pulse.
	 * 
	 * @return <tt>True</tt> if the robot has finished the last commanded pulse,
	 *         <tt>false</tt> otherwise
	 */
	public boolean hasFinishedLastPulse() {
		return !this.mDoPulse;
	}

	/**
	 * Whether the pulse forwarder has terminated, i.e. the robot has finished
	 * its algorithm.
	 * 
	 * @return <tt>True</tt> if the pulse forwarder has terminated,
	 *         <tt>false</tt> otherwise
	 */
	public boolean hasTerminated() {
		return this.mShouldStop;
	}

	/**
	 * Commands the robot to do a pulse. Use {@link #hasFinishedLastPulse()} to
	 * check the state.
	 */
	public void pulse() {
		// Do not accept commands if already stopped
		if (this.mShouldStop) {
			return;
		}
		this.mDoPulse = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (!this.mShouldStop) {
			if (this.mDoPulse) {
				final boolean isFinished = this.mRobot.pulse();
				if (isFinished) {
					this.mShouldStop = true;
				}
				this.mDoPulse = false;
			}

			try {
				Thread.sleep(WAIT_TIME);
			} catch (final InterruptedException e) {
				// Simply ignore the interrupt and continue
			}
		}
	}

}
