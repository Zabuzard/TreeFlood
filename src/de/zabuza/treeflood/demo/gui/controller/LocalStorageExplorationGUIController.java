package de.zabuza.treeflood.demo.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.zabuza.treeflood.demo.gui.model.CoordinateTree;
import de.zabuza.treeflood.demo.gui.model.DrawableNodeData;
import de.zabuza.treeflood.demo.gui.model.Edge;
import de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener;
import de.zabuza.treeflood.demo.gui.view.MainFrame;
import de.zabuza.treeflood.demo.gui.view.OptionPanel;
import de.zabuza.treeflood.demo.gui.view.properties.EStyle;
import de.zabuza.treeflood.exploration.localstorage.EStep;
import de.zabuza.treeflood.exploration.localstorage.Information;
import de.zabuza.treeflood.exploration.localstorage.LocalStorageExploration;
import de.zabuza.treeflood.exploration.localstorage.OneThreadPerRobotPulseManager;
import de.zabuza.treeflood.exploration.localstorage.Robot;
import de.zabuza.treeflood.exploration.localstorage.listener.ExplorationTreeBuilder;
import de.zabuza.treeflood.exploration.localstorage.listener.IRobotMovedListener;
import de.zabuza.treeflood.exploration.localstorage.listener.RobotNodeStringifier;
import de.zabuza.treeflood.exploration.localstorage.storage.NodeStorageManager;
import de.zabuza.treeflood.tree.ITreeNode;
import de.zabuza.treeflood.tree.RandomTreeGenerator;
import de.zabuza.treeflood.util.MapUtil;
import de.zabuza.treeflood.util.NestedMap2;

/**
 * A controller which handles the communication between {@link MainFrame} and
 * {@link LocalStorageExploration}.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class LocalStorageExplorationGUIController implements WindowListener, ActionListener, IRobotMovedListener,
		INodeHoverListener, ChangeListener, ItemListener {

	/**
	 * Gets the step type by the current step.
	 * 
	 * @param step
	 *            The current step of the algorithm.
	 * @return The corresponding step type.
	 */
	private static EStep getStepTypeByStep(final int step) {
		if (step == 1) {
			return EStep.INITIAL;
		} else if (step == 2) {
			return EStep.NOP;
		} else if (step % 3 == 0) {
			return EStep.REGULAR;
		} else if (step % 3 == 1) {
			return EStep.UPDATE;
		} else if (step % 3 == 2) {
			return EStep.RETURN;
		}
		// There was no step type associated with the current step count.
		throw new AssertionError();

	}

	/**
	 * Sets the given edge to visited. Used for painting related logic.
	 * 
	 * @param edgeToPaint
	 *            The edge which should be set to visited.
	 */
	private static void setEdgeVisited(final Edge edgeToPaint) {
		// Happens when robots try to walk edges backwards, since there is no
		// destination -> source mapping for the edges. Handling needs to be
		// implemented for animations.
		if (edgeToPaint == null) {
			return;
		}
		edgeToPaint.setVisited();
	}

	/**
	 * The current {@link LocalStorageExploration} in progress.
	 */
	private LocalStorageExploration mAlgorithm;

	/**
	 * The current edgeMapping for the tree in usage.
	 */
	private NestedMap2<ITreeNode, ITreeNode, Edge> mEdgeMapping;

	/**
	 * The {@link ExplorationTreeBuilder} which holds the explored tree at the
	 * current step.
	 */
	private ExplorationTreeBuilder mExplorationTreeBuilder;

	/**
	 * A boolean determining whether the algorithm is finished or not (for the
	 * current tree).
	 */
	private boolean mIsFinished;

	/**
	 * All the threads currently getting callbacks from the trees hover event.
	 */
	private final List<INodeHoverListener> mListeners;

	/**
	 * The current information for every node.
	 */
	private final Map<ITreeNode, List<Information>> mNodeInformationMapping;

	/**
	 * The current nodeMapping for the tree in usage.
	 */
	private Map<ITreeNode, DrawableNodeData> mNodeMapping;

	/**
	 * The current node storage manager used for this algorithm;
	 */
	private NodeStorageManager mNodeStorageManager;

	/**
	 * The current proportions of the tree.
	 */
	private int mProportions;

	/**
	 * The {@link RobotNodeStringifier} used.
	 */
	private RobotNodeStringifier mRobotNodeStringifier;

	/**
	 * The current exploration-step for
	 * {@link LocalStorageExplorationGUIController#mStep}.
	 */
	private int mStep;

	/**
	 * The current {@link CoordinateTree} in usage.
	 */
	private CoordinateTree mTree;

	/**
	 * The {@link MainFrame} from which to fetch commands and data.
	 */
	private final MainFrame mView;

	/**
	 * Constructs a new controller which handles the communication between the
	 * given {@link MainFrame} and {@link LocalStorageExploration}.
	 * 
	 * @param view
	 *            The view on which to operate.
	 */
	public LocalStorageExplorationGUIController(final MainFrame view) {
		this.mView = view;
		this.mListeners = new LinkedList<>();
		this.mListeners.add(this);

		this.mNodeInformationMapping = new HashMap<>();
		this.mProportions = DrawableNodeData.DEFAULT_RADIUS;

		view.addWindowListener(this);
		view.addUseSeedButtonListener(this);
		view.addWithoutSeedButtonListener(this);
		view.addStepButtonListener(this);
		view.addFullyButtonListener(this);
		view.addRoundButtonListener(this);
		view.addSizeSliderListener(this);
		view.addStyleItemListener(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		final Object source = event.getSource();

		if (source instanceof JButton) {
			final JButton sourceAsButton = (JButton) source;

			if (sourceAsButton.getText().equals(OptionPanel.USE_SEED_BUTTON_TEXT)) {
				// Make sure to fully finish the algorithm in a "correct" way to
				// stop it. This happens if a new tree is generated when the
				// algorithm wasn't finished on the old tree.
				if (this.mAlgorithm != null) {
					this.mAlgorithm.explore();
				}

				final RandomTreeGenerator generator = new RandomTreeGenerator(this.mView.getTreeSize());
				this.mTree = new CoordinateTree(generator.generateRandomTree(this.mView.getSeed()), this.mListeners,
						this.mProportions);
				this.resetData(generator);

			} else if (sourceAsButton.getText().equals(OptionPanel.WITHOUT_SEED_BUTTON_TEXT)) {
				// Make sure to fully finish the algorithm in a "correct" way to
				// stop it. This happens if a new tree is generated when the
				// algorithm wasn't finished on the old tree.
				if (this.mAlgorithm != null) {
					this.mAlgorithm.explore();
				}

				final RandomTreeGenerator generator = new RandomTreeGenerator(this.mView.getTreeSize());
				this.mTree = new CoordinateTree(generator.generateRandomTree(), this.mListeners, this.mProportions);
				this.resetData(generator);

			} else if (sourceAsButton.getText().equals(OptionPanel.STEP_BUTTON_TEXT)) {
				// Make sure to not do anything if the user wants to further
				// execute the algorithm if its already finished.
				if (this.mIsFinished) {
					return;
				}

				// If the algorithm wasn't started yet we start it and don't
				// execute the command.
				if (this.mAlgorithm == null) {
					this.startAlgorithm();
					return;
				}

				if (!this.mIsFinished) {
					this.mIsFinished = this.mAlgorithm.exploreOneStep();
					this.mStep++;
				}
				this.endAlgorithmCycle();

			} else if (sourceAsButton.getText().equals(OptionPanel.FULLY_BUTTON_TEXT)) {
				// Make sure to not do anything if the user wants to further
				// execute the algorithm if its already finished.
				if (this.mIsFinished) {
					return;
				}

				// If the algorithm wasn't started yet we start it and don't
				// execute the command.
				if (this.mAlgorithm == null) {
					this.startAlgorithm();
				}

				while (!this.mIsFinished) {
					this.mIsFinished = this.mAlgorithm.exploreOneStep();
					this.mStep++;
				}
				this.endAlgorithmCycle();

			} else if (sourceAsButton.getText().equals(OptionPanel.ROUND_BUTTON_TEXT)) {
				// Make sure to not do anything if the user wants to further
				// execute the algorithm if its already finished.
				if (this.mIsFinished) {
					return;
				}

				// If the algorithm wasn't started yet we start it and don't
				// execute the command.
				if (this.mAlgorithm == null) {
					this.startAlgorithm();
					return;
				}

				final int stepToGo = this.mStep + 3;

				while (this.mStep < stepToGo && !this.mIsFinished) {
					this.mIsFinished = this.mAlgorithm.exploreOneStep();
					this.mStep++;
				}
				this.endAlgorithmCycle();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(final ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			this.mView.setStyle((EStyle) e.getItem());
			this.mView.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.exploration.localstorage.listener.IRobotMovedListener
	 * #movedTo(de.zabuza.treeflood.exploration.localstorage.Robot,
	 * de.zabuza.treeflood.tree.ITreeNode, de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public synchronized void movedTo(final Robot robot, final ITreeNode source, final ITreeNode destination) {
		final DrawableNodeData sourceData = this.mNodeMapping.get(source);
		final DrawableNodeData destinationData = this.mNodeMapping.get(destination);

		sourceData.setDescription(Integer.parseInt(sourceData.getDescription()) - 1 + "");
		destinationData.setDescription(Integer.parseInt(destinationData.getDescription()) + 1 + "");

		this.paintNode(destination);
		LocalStorageExplorationGUIController.setEdgeVisited(this.mEdgeMapping.get(source, destination));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener#
	 * startHover(de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public void startHover(final ITreeNode node) {
		if (this.mNodeInformationMapping.get(node) != null) {

			final DrawableNodeData data = this.mNodeMapping.get(node);
			data.setInformation(this.mNodeInformationMapping.get(node));
			data.showTooltip();

			this.mView.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.
	 * ChangeEvent)
	 */
	@Override
	public void stateChanged(final ChangeEvent e) {
		if (!(e.getSource() instanceof JSlider)) {
			return;
		}
		final JSlider sizeSlider = (JSlider) e.getSource();
		this.changeTreeProportions(sizeSlider.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener#
	 * stopHover(de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public void stopHover(final ITreeNode node) {
		this.mNodeMapping.get(node).hideTooltip();
		this.mView.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(final WindowEvent event) {
		// No implementation needed.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(final WindowEvent event) {
		// No implementation needed.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(final WindowEvent event) {
		if (this.mAlgorithm == null) {
			return;
		}
		// Make sure to fully finish the algorithm before closing the window.
		this.mAlgorithm.explore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.
	 * WindowEvent)
	 */
	@Override
	public void windowDeactivated(final WindowEvent event) {
		// No implementation needed.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.
	 * WindowEvent)
	 */
	@Override
	public void windowDeiconified(final WindowEvent event) {
		// No implementation needed.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(final WindowEvent event) {
		// No implementation needed.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(final WindowEvent event) {
		// No implementation needed.
	}

	/**
	 * Changes the proportions of the tree by the given value.
	 * 
	 * @param value
	 *            The value with which to re-scale the tree.
	 */
	private void changeTreeProportions(final int value) {
		for (final ITreeNode node : this.mTree.getNodes()) {
			this.mNodeMapping.get(node).setRadius(value);
		}
		this.mProportions = value;
		this.mView.repaint();
	}

	/**
	 * Ends the current algorithm cycle. Updates the steps on the view and
	 * repaints the view, since there may have been robots moving, thus we need
	 * to update the graphics. Also sets the algorithm to <tt>null</tt> if it
	 * was finished.
	 */
	private void endAlgorithmCycle() {
		this.updateKnowledge();

		this.mView.setCurrentStep(this.mStep + "");
		this.mView.setStepType(getStepTypeByStep(this.mStep));
		this.mView.repaint();

		// Fiddle the aliases
		final Map<ITreeNode, ITreeNode> originalToExploredNodes = this.mExplorationTreeBuilder.getNodeAlias();
		final Map<ITreeNode, ITreeNode> exploredToOriginalNodes = MapUtil.reverseMap(originalToExploredNodes);
		this.mRobotNodeStringifier.setNodeAlias(exploredToOriginalNodes);

		if (this.mIsFinished) {
			this.mAlgorithm = null;
			return;
		}

	}

	/**
	 * Sets the given node to visited. Used for painting related logic.
	 * 
	 * @param nodeToPaint
	 *            The node which should be set to visited.
	 */
	private void paintNode(final ITreeNode nodeToPaint) {
		this.mNodeMapping.get(nodeToPaint).setVisited();
	}

	/**
	 * Resets all the values, so the algorithm can start on default values.
	 * (should be called when generating a new tree).
	 * 
	 * @param generator
	 *            The generator used to generate the tree.
	 */
	private void resetData(final RandomTreeGenerator generator) {
		this.mView.setTree(this.mTree);
		this.mView.setSeed(generator.getSeedOfLastGeneration().longValue());
		this.mView.setCurrentStep("");
		this.mView.setStepType(null);
		this.mNodeMapping = this.mTree.getNodeMapping();
		this.mEdgeMapping = this.mTree.getEdgeMapping();
		this.mAlgorithm = null;
		this.mIsFinished = false;
	}

	/**
	 * Starts the algorithm. Should only be called if the algorithm is not
	 * running, otherwise this method may not work as expected.
	 */
	private void startAlgorithm() {
		final List<IRobotMovedListener> robotMovedListener = new LinkedList<>();
		this.mExplorationTreeBuilder = new ExplorationTreeBuilder(this.mTree.getRoot());
		robotMovedListener.add(this.mExplorationTreeBuilder);

		this.mRobotNodeStringifier = new RobotNodeStringifier();
		robotMovedListener.add(this.mRobotNodeStringifier);
		robotMovedListener.add(this);

		this.mNodeStorageManager = new NodeStorageManager();

		this.mAlgorithm = new LocalStorageExploration(this.mTree.getRoot(), this.mView.getAmountOfRobots(),
				this.mNodeStorageManager, new OneThreadPerRobotPulseManager(), robotMovedListener);

		this.paintNode(this.mTree.getRoot());

		this.mRobotNodeStringifier.setInitialLocation(this.mAlgorithm.getRobots());

		this.mNodeMapping.get(this.mTree.getRoot()).setDescription(this.mAlgorithm.getRobots().size() + "");

		this.mStep = 0;

		this.mView.repaint();
	}

	/**
	 * Updates an intern map view, mapping nodes to their knowledges. Does so by
	 * traversing the whole tree on which is being operated and retrieving its
	 * knowledge data. The method is implemented thread-safe.
	 */
	private synchronized void updateKnowledge() {
		for (final ITreeNode node : this.mTree.getNodes()) {
			final NestedMap2<Integer, Integer, Information> map = this.mNodeStorageManager.read(node);

			for (int stepsToCount = 0; stepsToCount <= this.mStep; stepsToCount++) {
				for (int i = 0; i < this.mAlgorithm.getRobots().size(); i++) {
					final Information currentNodeInformation = map.get(Integer.valueOf(stepsToCount),
							Integer.valueOf(i));

					if (currentNodeInformation != null) {
						if (this.mNodeInformationMapping.get(node) == null) {
							this.mNodeInformationMapping.put(node, new ArrayList<>());
						}
						if (this.mNodeInformationMapping.get(node).contains(currentNodeInformation)) {
							continue;
						}
						this.mNodeInformationMapping.get(node).add(currentNodeInformation);
					}
				}
			}
		}
	}
}
