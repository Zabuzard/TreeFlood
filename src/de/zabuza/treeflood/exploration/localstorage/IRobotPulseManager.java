package de.zabuza.treeflood.exploration.localstorage;

import java.util.Collection;

/**
 * Interface for classes that manage pulse organization and distribution to
 * robots.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IRobotPulseManager {
	/**
	 * Pulses all robots distributedly. This demands the robots to execute one
	 * single step.
	 * 
	 * @return <tt>True</tt> if all robots have stopped because they finished
	 *         the algorithm, <tt>false<tt> otherwise
	 */
	public boolean pulse();

	/**
	 * Sets the robots to manage. This method can only be used once. Additional
	 * calls will simply return.
	 * 
	 * @param robots
	 *            The robots to manage
	 */
	public void setRobots(Collection<Robot> robots);
}
