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
	private final StyleManager manager;

	/**
	 * A panel providing options for the tree-generation and the algorithm
	 * execution.
	 */
	private final Optionpanel optionPanel;

	/**
	 * The panel on which the tree gets drawn.
	 */
	private final Treepanel treePanel;

	/**
	 * The frame used to display all the view related components. Is the
	 * top-level-container of the swing-hierarchy thus every element gets
	 * eventually added to this object.
	 *
	 * @param mTitle
	 *            The title of this frame.
	 * @param mManager
	 *            The style manager used to handle colors.
	 */
	public MainFrame(final String mTitle, final StyleManager mManager) {
		final Window window = new Window();
		this.manager = mManager;

		final JPanel panel = new JPanel(new BorderLayout());

		this.treePanel = new Treepanel(window, mManager);

		this.optionPanel = new Optionpanel(window, mManager);

		this.setTitle(mTitle);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setPreferredSize(window.getFrameSize());

		this.setLocation(window.getFrameLocation());

		this.setResizable(false);

		panel.add(this.treePanel, BorderLayout.WEST);
		panel.add(this.optionPanel, BorderLayout.EAST);

		this.add(panel);
	}

	/**
	 * Adds an {@link ActionListener} to the "fully"-button on the option panel.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addFullyButtonListener(final ActionListener mListener) {
		this.optionPanel.addFullyButtonListener(mListener);

	}

	/**
	 * Adds an {@link ActionListener} to the "round"-button on the option panel.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addRoundButtonListener(final ActionListener mListener) {
		this.optionPanel.addRoundButtonListener(mListener);

	}

	/**
	 * Adds a change listener to the slider used to control the size of the
	 * tree.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addSizeSliderListener(final ChangeListener mListener) {
		this.optionPanel.addSizeSliderListener(mListener);
	}

	/**
	 * Adds an {@link ActionListener} to the "step"-button on the option panel.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addStepButtonListener(final ActionListener mListener) {
		this.optionPanel.addStepButtonListener(mListener);

	}

	/**
	 * Adds an item listener to the combo box used to control the current style.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addStyleItemListener(final ItemListener mListener) {
		this.optionPanel.addStyleItemListener(mListener);

	}

	/**
	 * Adds an {@link ActionListener} to the "useSeed"-button on the option
	 * panel.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addUseSeedButtonListener(final ActionListener mListener) {
		this.optionPanel.addUseSeedButtonListener(mListener);

	}

	/**
	 * Adds an {@link ActionListener} to the "withoutSeed"-button on the option
	 * panel.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addWithoutSeedButtonListener(final ActionListener mListener) {
		this.optionPanel.addWithoutSeedButtonListener(mListener);

	}

	/**
	 * Gets the amount of robots currently inputted in a textfield on the option
	 * panel.
	 * 
	 * @return The amount of robots to use.
	 */
	public int getAmountOfRobots() {
		return this.optionPanel.getAmountOfRobots();

	}

	/**
	 * Gets the current seed inputted in a textfield on the option panel.
	 * 
	 * @return The seed mentioned.
	 */
	public long getSeed() {
		return this.optionPanel.getSeed();
	}

	/**
	 * Gets the size of the tree currently inputted in a textfield on the option
	 * panel.
	 * 
	 * @return The size of the tree.
	 */
	public int getTreeSize() {
		return this.optionPanel.getTreeSize();
	}

	@Override
	public void repaint() {
		super.repaint();
		this.treePanel.repaint();
		this.optionPanel.repaint();

	}

	/**
	 * Sets the current executed step-count on the option panel.
	 * 
	 * @param mStep
	 *            The current executed step-count.
	 */
	public void setCurrentStep(final String mStep) {
		this.optionPanel.setCurrentStep(mStep);

	}

	/**
	 * Sets the seed given to the option panel.
	 * 
	 * @param mSeed
	 *            The new seed which should be shown on the option panel.
	 */
	public void setSeed(final long mSeed) {
		this.optionPanel.setSeed(mSeed);

	}

	/**
	 * Sets the step type shown on the GUI.
	 * 
	 * @param mStepType
	 *            The step type.
	 */
	public void setStepType(final EStep mStepType) {
		this.optionPanel.setStepType(mStepType);

	}

	/**
	 * Sets the current style of this view.
	 * 
	 * @param mStyleToSet
	 *            The new style of this view.
	 */
	public void setStyle(final EStyle mStyleToSet) {
		this.manager.changeStyle(mStyleToSet);
	}

	/**
	 * Sets a new tree for the tree panel.
	 * 
	 * @param mTree
	 *            The tree to use.
	 */
	public void setTree(final CoordinateTree mTree) {
		this.treePanel.setTree(mTree);

	}

}
