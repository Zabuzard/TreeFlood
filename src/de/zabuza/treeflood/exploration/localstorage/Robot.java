package de.zabuza.treeflood.exploration.localstorage;

import java.util.List;

import de.zabuza.treeflood.exploration.localstorage.listener.IExploreEdgeListener;
import de.zabuza.treeflood.exploration.localstorage.listener.IRobotMovedListener;
import de.zabuza.treeflood.exploration.localstorage.storage.ILocalStorage;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * A robot that can explores a tree. All calls to the robot should be done via
 * threads in order to let robots run distributedly. Robots can only communicate
 * over the local storage nodes. Thus they do not have a remove communication
 * channel.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Robot implements Comparable<Robot> {
	/**
	 * The current node the robot is in.
	 */
	private ITreeNode mCurrentNode;
	/**
	 * The current stage the robot is in.
	 */
	private EStage mCurrentStage;
	/**
	 * The current step the robot is in.
	 */
	private EStep mCurrentStep;
	/**
	 * A list of objects that want to receive events each time a new edge was
	 * explored by this robot.
	 */
	private final List<IExploreEdgeListener> mExploreEdgeListeners;
	/**
	 * Whether the robot has stopped, i.e. finished the algorithm.
	 */
	private boolean mHasStopped;
	/**
	 * The unique id of this robot.
	 */
	private final int mId;
	/**
	 * The object that provides the local storage of nodes.
	 */
	private final ILocalStorage mLocalStorage;
	/**
	 * The port used in the last move stage.
	 */
	private int mPortUsedLastMoveStage;
	/**
	 * A list of objects that want to receive events each time this robot moves
	 * to another node.
	 */
	private final List<IRobotMovedListener> mRobotMovedListeners;
	/**
	 * The number of the round that is to be executed next.
	 */
	private int mRoundCounter;

	/**
	 * The number of the step that is to be executed next.
	 */
	private int mStepCounter;

	/**
	 * Creates a new robot with a unique id starting at the given node.
	 * 
	 * @param id
	 *            The unique id of the robot
	 * @param startingNode
	 *            The node the robot starts in
	 * @param localStorage
	 *            The object that provides the local storage of nodes
	 * @param exploreEdgeListeners
	 *            A list of objects that want to receive events each time a new
	 *            edge was explored by this robot
	 * @param robotMovedListeners
	 *            A list of objects that want to receive events each time this
	 *            robot moves to another node
	 */
	public Robot(final int id, final ITreeNode startingNode, final ILocalStorage localStorage,
			final List<IExploreEdgeListener> exploreEdgeListeners,
			final List<IRobotMovedListener> robotMovedListeners) {
		this.mId = id;
		this.mCurrentNode = startingNode;
		this.mLocalStorage = localStorage;
		this.mExploreEdgeListeners = exploreEdgeListeners;
		this.mRobotMovedListeners = robotMovedListeners;

		this.mCurrentStep = EStep.INITIAL;
		this.mCurrentStage = EStage.MOVE;
		this.mHasStopped = false;
		this.mStepCounter = 1;
		this.mRoundCounter = 0;
		this.mPortUsedLastMoveStage = Information.STAYED_PORT;
	}

	/**
	 * Compares robots by their unique id, ascending.
	 */
	@Override
	public int compareTo(final Robot other) {
		return Integer.compare(this.mId, other.getId());
	}

	/**
	 * Gets the unique id of this robot.
	 * 
	 * @return The unique id of this robot
	 */
	public int getId() {
		return this.mId;
	}

	/**
	 * Gets the tree node the robot is currently located at.
	 * 
	 * @return The tree node the robot is currently located at
	 */
	public ITreeNode getLocation() {
		return this.mCurrentNode;
	}

	/**
	 * Whether the robot has stopped, i.e. finished the algorithm.
	 * 
	 * @return <tt>True</tt> if the robot has stopped, <tt>false</tt> otherwise
	 */
	public boolean hasStopped() {
		return this.mHasStopped;
	}

	/**
	 * Pulses the robot which demands him to execute one step of the algorithm.
	 * 
	 * @return <tt>True</tt> if the robot has stopped because he finished the
	 *         algorithm, <tt>false<tt> otherwise
	 */
	public boolean pulse() {
		// Directly return if already stopped
		if (hasStopped()) {
			return true;
		}

		// Execute the stage of the current step
		executeStage(this.mCurrentStep, this.mCurrentStage);

		// Select the next step and stage and finish the pulse
		selectNextStepAndStage();
		return hasStopped();
	}

	/**
	 * Executes the stage of the given step.
	 * 
	 * @param step
	 *            The step to execute for
	 * @param stage
	 *            The stage to execute
	 */
	private void executeStage(final EStep step, final EStage stage) {
		// NOP step
		if (step == EStep.NOP) {
			// Do nothing in this step
			return;
		}

		// The actions for stages WRITE and READ are equal in all steps
		if (stage == EStage.WRITE) {
			writeAction();
			return;
		}
		if (stage == EStage.READ) {
			readAction();
			return;
		}

		// Move stage
		if (stage == EStage.MOVE) {
			// Initial step
			if (step == EStep.INITIAL) {
				// Only set the star port which indicates the initial movement
				// to the root
				this.mPortUsedLastMoveStage = Information.STAR_PORT;
				return;
			}

			// Regular step
			if (step == EStep.REGULAR) {
				// TODO Implement
				return;
			}

			// Update step
			if (step == EStep.UPDATE) {
				// TODO Implement
				return;
			}

			// Return step
			if (step == EStep.RETURN) {
				// TODO Implement
				return;
			}
		}

		throw new AssertionError();
	}

	/**
	 * Reads all information from the current node and updates the personal
	 * knownledge.
	 */
	private void readAction() {
		// TODO Implement
	}

	/**
	 * Selects the step and stage to be used for the next pulse depending on the
	 * current step and stage.
	 */
	private void selectNextStepAndStage() {
		boolean doUpdateStep = false;

		// Update the stage
		if (this.mCurrentStage == EStage.MOVE) {
			this.mCurrentStage = EStage.WRITE;
		} else if (this.mCurrentStage == EStage.WRITE) {
			this.mCurrentStage = EStage.READ;
		} else if (this.mCurrentStage == EStage.READ) {
			doUpdateStep = true;
			this.mCurrentStage = EStage.MOVE;
		} else {
			throw new AssertionError();
		}

		// Update the step
		if (!doUpdateStep) {
			return;
		}
		this.mStepCounter++;
		if (this.mCurrentStep == EStep.INITIAL) {
			this.mCurrentStep = EStep.NOP;
		} else if (this.mCurrentStep == EStep.NOP) {
			this.mRoundCounter++;
			this.mCurrentStep = EStep.REGULAR;
		} else if (this.mCurrentStep == EStep.REGULAR) {
			this.mCurrentStep = EStep.UPDATE;
		} else if (this.mCurrentStep == EStep.UPDATE) {
			this.mCurrentStep = EStep.RETURN;
		} else if (this.mCurrentStep == EStep.RETURN) {
			this.mRoundCounter++;
			this.mCurrentStep = EStep.REGULAR;
		} else {
			throw new AssertionError();
		}
	}

	/**
	 * Sets the robot to stopped. This means it has finished the algorithm and
	 * will refuse further {@link #pulse()} calls.
	 */
	private void setStopped() {
		this.mHasStopped = true;
	}

	/**
	 * Writes the current information to the current node.
	 */
	private void writeAction() {
		// Write information only if robot moved in the last stage
		if (this.mPortUsedLastMoveStage == Information.STAYED_PORT) {
			return;
		}

		// Write (step, id, port) to current node. This means that the robot
		// specified by the given id moved in the given step to the current node
		// by using the given port.
		final Information info = new Information(this.mStepCounter, this.mId, this.mPortUsedLastMoveStage);
		this.mLocalStorage.write(info, this.mCurrentNode);
	}
}
