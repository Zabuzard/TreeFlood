package de.zabuza.treeflood.demo.gui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import de.zabuza.treeflood.demo.gui.model.CoordinateTree;
import de.zabuza.treeflood.demo.gui.view.util.Window;

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
	 */
	public MainFrame(final String mTitle) {
		final Window window = new Window();

		final JPanel panel = new JPanel(new BorderLayout());

		this.treePanel = new Treepanel(window);

		this.optionPanel = new Optionpanel(window);

		this.setTitle(mTitle);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setPreferredSize(window.getFrameSize());

		this.setLocation(window.getFrameLocation());

		this.setResizable(false);

		panel.add(this.treePanel, BorderLayout.WEST);
		panel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
		panel.add(this.optionPanel, BorderLayout.EAST);

		this.add(panel);

		this.setVisible(true);
		this.pack();

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
	 * Adds an {@link ActionListener} to the "step"-button on the option panel.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addStepButtonListener(final ActionListener mListener) {
		this.optionPanel.addStepButtonListener(mListener);

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
	 * Sets a new tree for the tree panel.
	 * 
	 * @param mTree
	 *            The tree to use.
	 */
	public void setTree(final CoordinateTree mTree) {
		this.treePanel.setTree(mTree);

	}
}
