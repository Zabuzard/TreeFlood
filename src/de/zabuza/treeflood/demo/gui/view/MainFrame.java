package de.zabuza.treeflood.demo.gui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeListener;

import de.zabuza.treeflood.demo.gui.model.CoordinateTree;
import de.zabuza.treeflood.demo.gui.view.properties.EStyle;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;
import de.zabuza.treeflood.demo.gui.view.util.Window;
import de.zabuza.treeflood.exploration.localstorage.EStage;
import de.zabuza.treeflood.exploration.localstorage.EStep;

/**
 * The frame used to display all the view related components. Is the
 * top-level-container of the swing-hierarchy thus every element gets eventually
 * added to this object.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class MainFrame extends JFrame {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle styles.
	 */
	private final StyleManager mManager;

	/**
	 * A panel providing options for the tree-generation and the algorithm
	 * execution.
	 */
	private final OptionPanel mOptionPanel;

	/**
	 * The panel on which the tree gets drawn.
	 */
	private final TreePanel mTreePanel;

	/**
	 * The frame used to display all the view related components. Is the
	 * top-level-container of the swing-hierarchy thus every element gets
	 * eventually added to this object.
	 *
	 * @param title
	 *            The title of this frame.
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public MainFrame(final String title, final StyleManager manager) {
		final Window window = new Window();
		this.mManager = manager;

		final JPanel panel = new JPanel(new BorderLayout());

		this.mTreePanel = new TreePanel(window, manager);

		this.mOptionPanel = new OptionPanel(window, manager);

		this.setTitle(title);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setPreferredSize(window.getFrameSize());

		this.setLocation(window.getFrameLocation());

		this.setResizable(false);

		panel.add(this.mTreePanel, BorderLayout.WEST);
		panel.add(this.mOptionPanel, BorderLayout.EAST);

		this.add(panel);
	}

	/**
	 * Adds an {@link ActionListener} to the "fully"-button on the option panel.
	 * 
	 * @param listener
	 *            The listener to be added.
	 */
	public void addFullyButtonListener(final ActionListener listener) {
		this.mOptionPanel.addFullyButtonListener(listener);
	}

	/**
	 * Adds an {@link ActionListener} to the "round"-button on the option panel.
	 * 
	 * @param listener
	 *            The listener to be added.
	 */
	public void addRoundButtonListener(final ActionListener listener) {
		this.mOptionPanel.addRoundButtonListener(listener);
	}

	/**
	 * Adds a change listener to the slider used to control the size of the
	 * tree.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addSizeSliderListener(final ChangeListener mListener) {
		this.mOptionPanel.addSizeSliderListener(mListener);
	}

	/**
	 * Adds an {@link ActionListener} to the "step"-button on the option panel.
	 * 
	 * @param listener
	 *            The listener to be added.
	 */
	public void addStepButtonListener(final ActionListener listener) {
		this.mOptionPanel.addStepButtonListener(listener);
	}

	/**
	 * Adds an item listener to the combo box used to control the current style.
	 * 
	 * @param listener
	 *            The listener to be added.
	 */
	public void addStyleItemListener(final ItemListener listener) {
		this.mOptionPanel.addStyleItemListener(listener);
	}

	/**
	 * Adds an {@link ActionListener} to the "useSeed"-button on the option
	 * panel.
	 * 
	 * @param listener
	 *            The listener to be added.
	 */
	public void addUseSeedButtonListener(final ActionListener listener) {
		this.mOptionPanel.addUseSeedButtonListener(listener);
	}

	/**
	 * Adds an {@link ActionListener} to the "withoutSeed"-button on the option
	 * panel.
	 * 
	 * @param listener
	 *            The listener to be added.
	 */
	public void addWithoutSeedButtonListener(final ActionListener listener) {
		this.mOptionPanel.addWithoutSeedButtonListener(listener);
	}

	/**
	 * Gets the amount of robots currently present in a text field on the option
	 * panel.
	 * 
	 * @return The amount of robots to use.
	 */
	public int getAmountOfRobots() {
		return this.mOptionPanel.getAmountOfRobots();
	}

	/**
	 * Gets the current seed present in a text field on the option panel.
	 * 
	 * @return The seed mentioned.
	 */
	public long getSeed() {
		return this.mOptionPanel.getSeed();
	}

	/**
	 * Gets the size of the tree currently present in a text field on the option
	 * panel.
	 * 
	 * @return The size of the tree.
	 */
	public int getTreeSize() {
		return this.mOptionPanel.getTreeSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#repaint()
	 */
	@Override
	public void repaint() {
		super.repaint();
		this.mTreePanel.repaint();
		this.mOptionPanel.repaint();
	}

	/**
	 * Sets the current executed step-count on the option panel.
	 * 
	 * @param step
	 *            The current executed step-count.
	 */
	public void setCurrentStep(final String step) {
		this.mOptionPanel.setCurrentStep(step);
	}

	/**
	 * Sets the seed given to the option panel.
	 * 
	 * @param seed
	 *            The new seed which should be shown on the option panel.
	 */
	public void setSeed(final long seed) {
		this.mOptionPanel.setSeed(seed);
	}

	/**
	 * Sets the step type shown on the GUI.
	 * 
	 * @param stepType
	 *            The step type.
	 */
	public void setStepType(final EStep stepType) {
		this.mOptionPanel.setStepType(stepType);
	}

	/**
	 * Sets the current style of this view.
	 * 
	 * @param styleToSet
	 *            The new style of this view.
	 */
	public void setStyle(final EStyle styleToSet) {
		this.mManager.changeStyle(styleToSet);
	}

	/**
	 * Sets a new tree for the tree panel.
	 * 
	 * @param tree
	 *            The tree to use.
	 */
	public void setTree(final CoordinateTree tree) {
		this.mTreePanel.setTree(tree);
	}

	/**
	 * Sets the stage type on the GUI.
	 * 
	 * @param stageType
	 *            The stage type.
	 */
	public void setStageType(final EStage stageType) {
		this.mOptionPanel.setStageType(stageType);

	}
}
