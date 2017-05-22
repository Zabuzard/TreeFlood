package de.zabuza.treeflood.exploration.localstorage;

/**
 * Exception that indicates that a robot has failed.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RobotFailedException extends IllegalStateException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new exception that indicates that the given robot has failed.
	 * 
	 * @param robot
	 *            The robot that failed
	 * @param reason
	 *            The reason why the robot failed
	 */
	public RobotFailedException(final Robot robot, final Throwable reason) {
		super("The robot with id " + robot.getId() + " failed.", reason);
	}

}
