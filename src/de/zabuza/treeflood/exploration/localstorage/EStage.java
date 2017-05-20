package de.zabuza.treeflood.exploration.localstorage;

/**
 * All different stages of a step from a robot.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public enum EStage {
	/**
	 * Stage in which robots are allowed to move one node.
	 */
	MOVE,
	/**
	 * Stage in which robots are allowed to read from the local storage of the
	 * node they are currently located at.
	 */
	READ,
	/**
	 * Stage in which robots are allowed to write to the local storage of the
	 * node they are currently located at.
	 */
	WRITE
}
