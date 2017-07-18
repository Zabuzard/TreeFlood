package de.zabuza.treeflood.demo.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringJoiner;

import javax.swing.JButton;

import de.zabuza.treeflood.demo.gui.model.CoordinateTree;
import de.zabuza.treeflood.demo.gui.model.DrawableNodeData;
import de.zabuza.treeflood.demo.gui.model.Edge;
import de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener;
import de.zabuza.treeflood.demo.gui.view.MainFrame;
import de.zabuza.treeflood.demo.gui.view.Optionpanel;
import de.zabuza.treeflood.demo.gui.view.util.Window;
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
public final class LocalStorageExplorationGUIController
		implements WindowListener, ActionListener, IRobotMovedListener, INodeHoverListener {

	/**
	 * The current {@link LocalStorageExploration} in progress.
	 */
	private LocalStorageExploration algorithm;

	/**
	 * The current edgeMapping for the tree in usage.
	 */
	private NestedMap2<ITreeNode, ITreeNode, Edge> edgeMapping;

	/**
	 * The {@link ExplorationTreeBuilder} which holds the explored tree at the
	 * current step.
	 */
	private ExplorationTreeBuilder explorationTreeBuilder;

	/**
	 * A boolean determining whether the algorithm is finished or not (for the
	 * current tree).
	 */
	private boolean isFinished;

	private final List<INodeHoverListener> listeners;

	/**
	 * The current information for every node.
	 */
	private final Map<ITreeNode, List<Information>> nodeInformationMapping;

	/**
	 * The current nodeMapping for the tree in usage.
	 */
	private Map<ITreeNode, DrawableNodeData> nodeMapping;

	/**
	 * The current node storage manager used for this algorithm;
	 */
	private NodeStorageManager nodeStorageManager;

	/**
	 * The {@link RobotNodeStringifier} used.
	 */
	private RobotNodeStringifier robotNodeStringifier;

	/**
	 * The current exploration-step for
	 * {@link LocalStorageExplorationGUIController#step}.
	 */
	private int step;

	/**
	 * The current {@link CoordinateTree} in usage.
	 */
	private CoordinateTree tree;

	/**
	 * The {@link MainFrame} from which to fetch commands and data.
	 */
	private final MainFrame view;

	/**
	 * Constructs a new controller which handles the communication between the
	 * given {@link MainFrame} and {@link LocalStorageExploration}.
	 * 
	 * @param mView
	 *            The view on which to operate.
	 */
	public LocalStorageExplorationGUIController(final MainFrame mView) {
		this.view = mView;
		this.listeners = new LinkedList<>();
		this.listeners.add(this);

		this.nodeInformationMapping = new HashMap<>();

		mView.addWindowListener(this);
		mView.addUseSeedButtonListener(this);
		mView.addWithoutSeedButtonListener(this);
		mView.addStepButtonListener(this);
		mView.addFullyButtonListener(this);
		mView.addRoundButtonListener(this);

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

			if (sourceAsButton.getText().equals(Optionpanel.USE_SEED_BUTTON_TEXT)) {
				// make sure to fully finish the algorithm in a "correct" way to
				// stop it. This happens if a new tree is generated when the
				// algorithm wasn't finished on the old tree.
				if (this.algorithm != null) {
					this.algorithm.explore();
				}

				final RandomTreeGenerator generator = new RandomTreeGenerator(this.view.getTreeSize());
				this.tree = new CoordinateTree(generator.generateRandomTree(this.view.getSeed()), this.listeners);
				this.resetData(generator);

			} else if (sourceAsButton.getText().equals(Optionpanel.WITHOUT_SEED_BUTTON_TEXT)) {
				// make sure to fully finish the algorithm in a "correct" way to
				// stop it. This happens if a new tree is generated when the
				// algorithm wasn't finished on the old tree.
				if (this.algorithm != null) {
					this.algorithm.explore();
				}

				final RandomTreeGenerator generator = new RandomTreeGenerator(this.view.getTreeSize());
				this.tree = new CoordinateTree(generator.generateRandomTree(), this.listeners);
				this.resetData(generator);

			} else if (sourceAsButton.getText().equals(Optionpanel.STEP_BUTTON_TEXT)) {
				// make sure to not do anything if the user wants to further
				// execute the algorithm if its already finished.
				if (this.isFinished) {
					return;
				}

				// if the algorithm wasn't started yet we start it and don't
				// execute the command.
				if (this.algorithm == null) {
					this.startAlgorithm();
					return;
				}

				if (!this.isFinished) {
					this.isFinished = this.algorithm.exploreOneStep();
					this.step++;
				}
				this.endAlgorithmCycle();

			} else if (sourceAsButton.getText().equals(Optionpanel.FULLY_BUTTON_TEXT)) {
				// make sure to not do anything if the user wants to further
				// execute the algorithm if its already finished.
				if (this.isFinished) {
					return;
				}

				// if the algorithm wasn't started yet we start it and don't
				// execute the command.
				if (this.algorithm == null) {
					this.startAlgorithm();
				}

				while (!this.isFinished) {
					this.isFinished = this.algorithm.exploreOneStep();
					this.step++;
				}
				this.endAlgorithmCycle();

			} else if (sourceAsButton.getText().equals(Optionpanel.ROUND_BUTTON_TEXT)) {
				// make sure to not do anything if the user wants to further
				// execute the algorithm if its already finished.
				if (this.isFinished) {
					return;

				}

				// if the algorithm wasn't started yet we start it and don't
				// execute the command.
				if (this.algorithm == null) {
					this.startAlgorithm();
					return;
				}

				final int stepToGo = this.step + 3;

				while (this.step < stepToGo && !this.isFinished) {
					this.isFinished = this.algorithm.exploreOneStep();
					this.step++;
				}

				this.endAlgorithmCycle();

			}
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
		final DrawableNodeData sourceData = this.nodeMapping.get(source);
		final DrawableNodeData destinationData = this.nodeMapping.get(destination);

		sourceData.setDescription(Integer.parseInt(sourceData.getDescription()) - 1 + "");
		destinationData.setDescription(Integer.parseInt(destinationData.getDescription()) + 1 + "");

		this.paintNode(destination);
		this.paintEdge(this.edgeMapping.get(source, destination));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener#
	 * startHover(de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public void startHover(final ITreeNode mNode) {
		if (this.nodeInformationMapping.get(mNode) != null) {
			final StringJoiner joiner = new StringJoiner(System.lineSeparator());

			for (final Information information : this.nodeInformationMapping.get(mNode)) {
				joiner.add(information.toString());

			}
			final DrawableNodeData data = this.nodeMapping.get(mNode);
			data.setInformation(joiner.toString());
			data.showTooltip();

			this.view.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener#
	 * stopHover(de.zabuza.treeflood.tree.ITreeNode)
	 */
	@Override
	public void stopHover(final ITreeNode mNode) {
		this.nodeMapping.get(mNode).hideTooltip();
		this.view.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(final WindowEvent arg0) {
		// No implementation needed.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(final WindowEvent arg0) {
		// No implementation needed.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(final WindowEvent arg0) {
		if (this.algorithm == null) {
			return;

		}
		// make sure to fully finish the algorithm before closing the window.
		this.algorithm.explore();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.
	 * WindowEvent)
	 */
	@Override
	public void windowDeactivated(final WindowEvent arg0) {
		// No implementation needed.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.
	 * WindowEvent)
	 */
	@Override
	public void windowDeiconified(final WindowEvent arg0) {
		// No implementation needed.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(final WindowEvent arg0) {
		// No implementation needed.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(final WindowEvent arg0) {
		// No implementation needed.

	}

	/**
	 * Ends the current algorithm cycle. Updates the steps on the view and
	 * repaints the view, since there may have been robots moving, thus we need
	 * to update the graphics. Also sets the algorithm to <tt>null</tt> if it
	 * was finished.
	 */
	private void endAlgorithmCycle() {
		this.updateKnowledge();

		this.view.setCurrentStep(this.step);
		this.view.repaint();

		// Fiddle the aliases
		final Map<ITreeNode, ITreeNode> originalToExploredNodes = this.explorationTreeBuilder.getNodeAlias();
		final Map<ITreeNode, ITreeNode> exploredToOriginalNodes = MapUtil.reverseMap(originalToExploredNodes);
		this.robotNodeStringifier.setNodeAlias(exploredToOriginalNodes);

		if (this.isFinished) {
			this.algorithm = null;

		}

	}

	/**
	 * Paints the given edge to the color specified in
	 * {@link Window#VISITED_EDGE_COLOR}.
	 * 
	 * @param mEdgeToPaint
	 *            The edge which should be painted.
	 */
	@SuppressWarnings("static-method")
	private void paintEdge(final Edge mEdgeToPaint) {

		// happens when robots try to walk edges backwards, since there is no
		// destination -> source mapping for the edges. Handling needs to be
		// implemented for animations.
		if (mEdgeToPaint == null) {
			return;

		}
		mEdgeToPaint.setColor(Window.VISITED_EDGE_COLOR);

	}

	/**
	 * Paints the given node to the color specified in
	 * {@link Window#VISITED_NODE_COLOR}.
	 * 
	 * @param mNodeToPaint
	 *            The node which should be painted.
	 */
	private void paintNode(final ITreeNode mNodeToPaint) {
		this.nodeMapping.get(mNodeToPaint).setColor(Window.VISITED_NODE_COLOR);
	}

	/**
	 * Resets all the values, so the algorithm can start on default values.
	 * (should be called when generating a new tree).
	 * 
	 * @param mGenerator
	 *            The generator used to generate the tree.
	 */
	private void resetData(final RandomTreeGenerator mGenerator) {
		this.view.setTree(this.tree);
		this.view.setSeed(mGenerator.getSeedOfLastGeneration().longValue());
		this.view.setCurrentStep(0);
		this.nodeMapping = this.tree.getNodeMapping();
		this.edgeMapping = this.tree.getEdgeMapping();
		this.algorithm = null;
		this.isFinished = false;

	}

	/**
	 * Starts the algorithm. Should only be called if the algorithm is not
	 * running, otherwise this method may not work as expected.
	 */
	private void startAlgorithm() {
		final List<IRobotMovedListener> robotMovedListener = new LinkedList<>();
		this.explorationTreeBuilder = new ExplorationTreeBuilder(this.tree.getRoot());
		robotMovedListener.add(this.explorationTreeBuilder);

		this.robotNodeStringifier = new RobotNodeStringifier();
		robotMovedListener.add(this.robotNodeStringifier);
		robotMovedListener.add(this);

		this.nodeStorageManager = new NodeStorageManager();

		this.algorithm = new LocalStorageExploration(this.tree.getRoot(), this.view.getAmountOfRobots(),
				this.nodeStorageManager, new OneThreadPerRobotPulseManager(), robotMovedListener);

		this.paintNode(this.tree.getRoot());

		this.robotNodeStringifier.setInitialLocation(this.algorithm.getRobots());

		this.nodeMapping.get(this.tree.getRoot()).setDescription(this.algorithm.getRobots().size() + "");

		this.step = 0;

		this.view.repaint();

	}

	/**
	 * Updates an intern map view, mapping nodes to their knowledges. Does so by
	 * traversing the whole tree on which is being operated and retrieving its
	 * knowledge data.
	 */
	private synchronized void updateKnowledge() {
		final Queue<ITreeNode> queue = new LinkedList<>();
		queue.add(this.tree.getRoot());

		while (!queue.isEmpty()) {
			final ITreeNode node = queue.poll();

			final NestedMap2<Integer, Integer, Information> map = this.nodeStorageManager.read(node);

			for (int stepsToCount = 1; stepsToCount < this.step; stepsToCount++) {
				for (int i = 0; i < this.algorithm.getRobots().size(); i++) {

					final Information currentNodeInformation = map.get(Integer.valueOf(stepsToCount),
							Integer.valueOf(i));

					if (currentNodeInformation != null) {
						if (this.nodeInformationMapping.get(node) == null) {
							this.nodeInformationMapping.put(node, new ArrayList<>());

						}
						if (this.nodeInformationMapping.get(node).contains(currentNodeInformation)) {
							continue;

						}
						this.nodeInformationMapping.get(node).add(currentNodeInformation);

					}
				}
			}

			for (final ITreeNode children : node.getChildren()) {
				queue.add(children);

			}

		}
	}
}
