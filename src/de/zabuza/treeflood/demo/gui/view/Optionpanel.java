package de.zabuza.treeflood.demo.gui.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;

import de.zabuza.treeflood.demo.gui.model.DrawableNodeData;
import de.zabuza.treeflood.demo.gui.view.properties.EStyle;
import de.zabuza.treeflood.demo.gui.view.properties.IRecolorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;
import de.zabuza.treeflood.demo.gui.view.util.Window;
import de.zabuza.treeflood.exploration.localstorage.EStep;

/**
 * A panel which provides options for the tree-generation and the
 * algorithm-execution.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Optionpanel extends JPanel implements IRecolorable {

	/**
	 * The title of {@link Optionpanel#fullyButton}.
	 */
	public final static String FULLY_BUTTON_TEXT = "Fully";

	/**
	 * The title of {@link Optionpanel#fullyButton}.
	 */
	public final static String ROUND_BUTTON_TEXT = "Round";

	/**
	 * The title of {@link Optionpanel#stepButton}.
	 */
	public final static String STEP_BUTTON_TEXT = "Step";

	/**
	 * The title of {@link Optionpanel#useSeedButton}.
	 */
	public final static String USE_SEED_BUTTON_TEXT = "Use Seed";

	/**
	 * The title of {@link Optionpanel#withoutSeedButton}.
	 */
	public final static String WITHOUT_SEED_BUTTON_TEXT = "Without Seed";

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * All components added to this panel.
	 */
	private final List<IRecolorable> components;

	/**
	 * The button which fully executes the algorithm.
	 */
	private final OptionButton fullyButton;

	/**
	 * The style manager used to manage colors.
	 */
	private final StyleManager manager;

	/**
	 * The text area which contains the amount of robots to use.
	 */
	private final OptionTextArea robotsArea;

	/**
	 * The button which executes the algorithm by one round.
	 */
	private final OptionButton roundButton;

	/**
	 * The text area which contains the current seed of the tree.
	 */
	private final OptionTextArea seedArea;

	/**
	 * The slider used to change the size of the tree.
	 */
	private final OptionSlider sizeSlider;

	/**
	 * The text area which shows the amount of steps executed.
	 */
	private final OptionTextArea stepArea;

	/**
	 * The button which executes the algorithm by one step.
	 */
	private final OptionButton stepButton;

	/**
	 * The text pane used to display the current step type.
	 */
	private final OptionTextPane stepTypePane;

	/**
	 * The combo box used to select different styles.
	 */
	private final OptionComboBox<EStyle> styleSelect;

	/**
	 * The text area which contains the current size of the tree.
	 */
	private final OptionTextArea treeSizeArea;

	/**
	 * The button which generates a new tree with the given seed.
	 */
	private final OptionButton useSeedButton;

	/**
	 * The button which generates a new tree without the given seed.
	 */
	private final OptionButton withoutSeedButton;

	/**
	 * Constructs a new option panel which provides options for the
	 * tree-generation and the algorithm-execution. Gets its placement
	 * information from the given window object.
	 * 
	 * @param mWindow
	 *            The object from which to get the placement related data for
	 *            this object.
	 * 
	 * @param mManager
	 *            the manager used to get the color for objects.
	 */
	public Optionpanel(final Window mWindow, final StyleManager mManager) {
		this.components = new LinkedList<>();
		this.manager = mManager;

		this.setLayout(new GridBagLayout());
		this.setPreferredSize(mWindow.getOptionPanelSize());
		this.setBorder(LineBorder.createBlackLineBorder());

		final GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.HORIZONTAL;

		final OptionTextPane textPane = new OptionTextPane("Seed:", mManager);

		constraints.gridx = 0;
		constraints.insets = new Insets(0, 15, 0, 0);

		this.add(textPane, constraints);

		this.seedArea = new OptionTextArea(mManager);

		constraints.weightx = 0.8;
		constraints.gridx = 1;
		constraints.gridwidth = 6;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 15, 0, 10);

		this.add(this.seedArea, constraints);

		final OptionTextPane textPane2 = new OptionTextPane("Tree Size:", mManager);

		constraints.weightx = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(5, 15, 0, 0);

		this.add(textPane2, constraints);

		this.treeSizeArea = new OptionTextArea("10", mManager);

		constraints.weightx = 0.8;
		constraints.gridx = 1;
		constraints.gridwidth = 6;
		constraints.insets = new Insets(5, 15, 0, 10);

		this.add(this.treeSizeArea, constraints);

		this.useSeedButton = new OptionButton(USE_SEED_BUTTON_TEXT, mManager);

		constraints.weightx = 0;
		constraints.gridwidth = 3;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(20, 30, 0, 10);

		this.add(this.useSeedButton, constraints);

		this.withoutSeedButton = new OptionButton(WITHOUT_SEED_BUTTON_TEXT, mManager);

		constraints.gridx = 3;
		constraints.insets = new Insets(20, 0, 0, 10);

		this.add(this.withoutSeedButton, constraints);

		final OptionSeparator firstSeparator = new OptionSeparator(SwingConstants.HORIZONTAL, mManager);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(30, 10, 30, 5);
		constraints.gridwidth = 7;

		this.add(firstSeparator, constraints);

		final OptionTextPane textPane3 = new OptionTextPane("Robots:", mManager);

		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.gridy = 4;
		constraints.insets = new Insets(5, 15, 0, 0);

		this.add(textPane3, constraints);

		this.robotsArea = new OptionTextArea("1", mManager);

		constraints.gridx = 1;
		constraints.gridwidth = 6;
		constraints.insets = new Insets(5, 15, 0, 10);

		this.add(this.robotsArea, constraints);

		this.stepButton = new OptionButton(STEP_BUTTON_TEXT, mManager);

		constraints.weightx = 0;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.insets = new Insets(20, 20, 0, 0);

		this.add(this.stepButton, constraints);

		this.roundButton = new OptionButton(ROUND_BUTTON_TEXT, mManager);

		constraints.gridx = 3;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(20, -25, 0, 0);

		this.add(this.roundButton, constraints);

		this.fullyButton = new OptionButton(FULLY_BUTTON_TEXT, mManager);

		constraints.fill = 0;
		constraints.gridx = 5;
		constraints.insets = new Insets(20, 0, 0, -8);

		this.add(this.fullyButton, constraints);

		final OptionTextPane stepShow = new OptionTextPane("After Step:", mManager);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.gridy = 6;
		constraints.insets = new Insets(25, 20, 0, 0);

		this.add(stepShow, constraints);

		this.stepArea = new OptionTextArea(mManager);
		this.stepArea.setEditable(false);

		constraints.gridx = 2;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(25, 0, 0, 15);

		this.add(this.stepArea, constraints);

		this.stepTypePane = new OptionTextPane(mManager);

		constraints.gridx = 4;
		constraints.gridwidth = 3;

		constraints.insets = new Insets(25, -10, 0, 0);

		this.add(this.stepTypePane, constraints);

		final OptionSeparator secondSeparator = new OptionSeparator(SwingConstants.HORIZONTAL, mManager);
		secondSeparator.setForeground(Color.black);

		constraints.weightx = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 7;
		constraints.gridy = 7;
		constraints.insets = new Insets(30, 10, 50, 5);
		this.add(secondSeparator, constraints);

		this.sizeSlider = new OptionSlider(SwingConstants.HORIZONTAL, 0, DrawableNodeData.DEFAULT_RADIUS * 2,
				DrawableNodeData.DEFAULT_RADIUS, mManager);

		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.gridy = 8;
		constraints.insets = new Insets(270, 20, 0, 20);

		this.add(this.sizeSlider, constraints);

		final EStyle[] supportedStyles = { EStyle.STANDARD, EStyle.DARK };

		this.styleSelect = new OptionComboBox<>(supportedStyles, mManager);
		this.styleSelect.setEditable(false);
		this.styleSelect.setBackground(mManager.getButtonColor());

		constraints.gridx = 3;
		constraints.gridwidth = 4;

		this.add(this.styleSelect, constraints);

		this.components.add(textPane);
		this.components.add(this.seedArea);
		this.components.add(textPane2);
		this.components.add(textPane3);
		this.components.add(this.treeSizeArea);
		this.components.add(this.useSeedButton);
		this.components.add(this.withoutSeedButton);
		this.components.add(firstSeparator);
		this.components.add(this.robotsArea);
		this.components.add(this.stepButton);
		this.components.add(this.roundButton);
		this.components.add(this.fullyButton);
		this.components.add(stepShow);
		this.components.add(this.stepArea);
		this.components.add(secondSeparator);
		this.components.add(this.sizeSlider);
		this.components.add(this.styleSelect);
		this.components.add(this.stepTypePane);

	}

	/**
	 * Adds an {@link ActionListener} to {@link Optionpanel#fullyButton}.
	 * 
	 * @param mListener
	 *            The listener to be added
	 */
	public void addFullyButtonListener(final ActionListener mListener) {
		this.fullyButton.addActionListener(mListener);

	}

	/**
	 * Adds an {@link ActionListener} to {@link Optionpanel#roundButton}.
	 * 
	 * @param mListener
	 *            The listener to be added
	 */
	public void addRoundButtonListener(final ActionListener mListener) {
		this.roundButton.addActionListener(mListener);

	}

	/**
	 * Adds a change listener to the slider used to control the size of the
	 * tree.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addSizeSliderListener(final ChangeListener mListener) {
		this.sizeSlider.addChangeListener(mListener);
	}

	/**
	 * Adds an {@link ActionListener} to {@link Optionpanel#stepButton}.
	 * 
	 * @param mListener
	 *            The listener to be added
	 */
	public void addStepButtonListener(final ActionListener mListener) {
		this.stepButton.addActionListener(mListener);

	}

	/**
	 * Adds an item listener to the combo box used to control the current style.
	 * 
	 * @param mListener
	 *            The listener to be added.
	 */
	public void addStyleItemListener(final ItemListener mListener) {
		this.styleSelect.addItemListener(mListener);

	}

	/**
	 * Adds an {@link ActionListener} to {@link Optionpanel#useSeedButton}.
	 * 
	 * @param mListener
	 *            The listener to be added
	 */
	public void addUseSeedButtonListener(final ActionListener mListener) {
		this.useSeedButton.addActionListener(mListener);

	}

	/**
	 * Adds an {@link ActionListener} to {@link Optionpanel#withoutSeedButton}.
	 * 
	 * @param mListener
	 *            The listener to be added
	 */
	public void addWithoutSeedButtonListener(final ActionListener mListener) {
		this.withoutSeedButton.addActionListener(mListener);

	}

	/**
	 * Gets the amount of robots from {@link Optionpanel#robotsArea}.
	 * 
	 * @return The amount of robots to use.
	 */
	public int getAmountOfRobots() {
		// TODO: handle parsing errors;
		return Integer.parseInt(this.robotsArea.getText());

	}

	/**
	 * Gets the seed from {@link Optionpanel#seedArea}.
	 * 
	 * @return The seed.
	 */
	public long getSeed() {
		// TODO: handle parsing errors
		return Long.parseLong(this.seedArea.getText());
	}

	/**
	 * Gets the tree size from {@link Optionpanel#treeSizeArea}.
	 * 
	 * @return The tree size.
	 */
	public int getTreeSize() {
		// TODO: handle parsing errors
		return Integer.parseInt(this.treeSizeArea.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.manager.getOptionpanelColor());

	}

	@Override
	public void repaint() {
		super.repaint();
		if (this.components == null) {
			return;

		}
		for (final IRecolorable comp : this.components) {
			comp.reColor();
		}
		this.reColor();
	}

	/**
	 * Sets the amount of current steps executed.
	 * 
	 * @param mSteps
	 *            The new amount of executed steps.
	 */
	public void setCurrentStep(final String mSteps) {
		this.stepArea.setText(mSteps);

	}

	/**
	 * Sets the seed on {@link Optionpanel#seedArea}.
	 * 
	 * @param mSeed
	 *            The seed to be set.
	 */
	public void setSeed(final long mSeed) {
		this.seedArea.setText("" + mSeed);

	}

	/**
	 * Sets the step type on the GUI.
	 * 
	 * @param mStepType
	 *            The step type.
	 */
	public void setStepType(final EStep mStepType) {
		if (mStepType == null) {
			this.stepTypePane.setText("");
			return;

		}
		this.stepTypePane.setText(mStepType.toString());

	}

}
