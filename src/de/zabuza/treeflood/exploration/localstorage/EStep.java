package de.zabuza.treeflood.exploration.localstorage;

/**
 * All different steps of a robot from the {@link LocalStorageExploration}
 * algorithm.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public enum EStep {
	/**
	 * The initial step used to distinguish the root.
	 */
	INITIAL,
	/**
	 * A no operation fake step introduced in order that the regular step cycle
	 * is divisible by 3.
	 */
	NOP,
	/**
	 * The regular step in which robots move accordingly to the algorithm where
	 * complete communication between robots is available.
	 */
	REGULAR,
	/**
	 * Return step in which robots undo their temporarily move of the
	 * {@link #UPDATE} step and return to their original node.
	 */
	RETURN,
	/**
	 * Update step where robots temporarily move back to update the parent about
	 * their progress.
	 */
	UPDATE
}
