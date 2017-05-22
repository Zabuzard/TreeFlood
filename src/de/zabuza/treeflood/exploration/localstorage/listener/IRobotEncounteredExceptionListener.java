package de.zabuza.treeflood.exploration.localstorage.listener;

import de.zabuza.treeflood.exploration.localstorage.Robot;

/**
 * Interface for objects that want to listen to events of type robot encountered
 * exception.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IRobotEncounteredExceptionListener {
	/**
	 * Event listener callback for robot encountered exception events. An
	 * implementation should note that this method will be called from multiple
	 * threads at the same time. It may be necessary to implement the method
	 * thread safe.
	 * 
	 * @param robot
	 *            The robot that encountered an exception
	 * @param e
	 *            The throwable that was encountered
	 */
	public void encounteredException(Robot robot, Throwable e);
}
