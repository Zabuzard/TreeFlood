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
import de.zabuza.treeflood.demo.gui.view.properties.IReColorable;
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
public final class OptionPanel extends JPanel implements IReColorable {

	/**
	 * The title of {@link OptionPanel#mFullyButton}.
	 */
	public final static String FULLY_BUTTON_TEXT = "Fully";

	/**
	 * The title of {@link OptionPanel#mFullyButton}.
	 */
	public final static String ROUND_BUTTON_TEXT = "Round";

	/**
	 * The title of {@link OptionPanel#mStepButton}.
	 */
	public final static String STEP_BUTTON_TEXT = "Step";

	/**
	 * The title of {@link OptionPanel#mUseSeedButton}.
	 */
	public final static String USE_SEED_BUTTON_TEXT = "Use Seed";

	/**
	 * The title of {@link OptionPanel#mWithoutSeedButton}.
	 */
	public final static String WITHOUT_SEED_BUTTON_TEXT = "Without Seed";

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * All components added to this panel.
	 */
	private final List<IReColorable> mComponents;

	/**
	 * The button which fully executes the algorithm.
	 */
	private final OptionButton mFullyButton;

	/**
	 * The style manager used to manage colors.
	 */
	private final StyleManager mManager;

	/**
	 * The text area which contains the amount of robots to use.
	 */
	private final OptionTextArea mRobotsArea;

	/**
	 * The button which executes the algorithm by one round.
	 */
	private final OptionButton mRoundButton;

	/**
	 * The text area which contains the current seed of the tree.
	 */
	private final OptionTextArea mSeedArea;

	/**
	 * The slider used to change the size of the tree.
	 */
	private final OptionSlider mSizeSlider;

	/**
	 * The text area which shows the amount of steps executed.
	 */
	private final OptionTextArea mStepArea;

	/**
	 * The button which executes the algorithm by one step.
	 */
	private final OptionButton mStepButton;

	/**
	 * The text pane used to display the current step type.
	 */
	private final OptionTextPane mStepTypePane;

	/**
	 * The combo box used to select different styles.
	 */
	private final OptionComboBox<EStyle> mStyleSelect;

	/**
	 * The text area which contains the current size of the tree.
	 */
	private final OptionTextArea mTreeSizeArea;

	/**
	 * The button which generates a new tree with the given seed.
	 */
	private final OptionButton mUseSeedButton;

	/**
	 * The button which generates a new tree without the given seed.
	 */
	private final OptionButton mWithoutSeedButton;

	/**
	 * Constructs a new option panel which provides options for the
	 * tree-generation and the algorithm-execution. Gets its placement
	 * information from the given window object.
	 * 
	 * @param window
	 *            The object from which to get the placement related data for
	 *            this object.
	 * 
	 * @param manager
	 *            the manager used to get the color for objects.
	 */
	public OptionPanel(final Window window, final StyleManager manager) {
		this.mComponents = new LinkedList<>();
		this.mManager = manager;

		this.setLayout(new GridBagLayout());
		this.setPreferredSize(window.getOptionPanelSize());
		this.setBorder(LineBorder.createBlackLineBorder());

		final GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.HORIZONTAL;

		final OptionTextPane textPane = new OptionTextPane("Seed:", manager);

		constraints.gridx = 0;
		constraints.insets = new Insets(0, 15, 0, 0);

		this.add(textPane, constraints);

		this.mSeedArea = new OptionTextArea(manager);

		constraints.weightx = 0.8;
		constraints.gridx = 1;
		constraints.gridwidth = 6;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 15, 0, 10);

		this.add(this.mSeedArea, constraints);

		final OptionTextPane textPane2 = new OptionTextPane("Tree Size:", manager);

		constraints.weightx = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(5, 15, 0, 0);

		this.add(textPane2, constraints);

		this.mTreeSizeArea = new OptionTextArea("10", manager);

		constraints.weightx = 0.8;
		constraints.gridx = 1;
		constraints.gridwidth = 6;
		constraints.insets = new Insets(5, 15, 0, 10);

		this.add(this.mTreeSizeArea, constraints);

		this.mUseSeedButton = new OptionButton(USE_SEED_BUTTON_TEXT, manager);

		constraints.weightx = 0;
		constraints.gridwidth = 3;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(20, 30, 0, 10);

		this.add(this.mUseSeedButton, constraints);

		this.mWithoutSeedButton = new OptionButton(WITHOUT_SEED_BUTTON_TEXT, manager);

		constraints.gridx = 3;
		constraints.insets = new Insets(20, 0, 0, 10);

		this.add(this.mWithoutSeedButton, constraints);

		final OptionSeparator firstSeparator = new OptionSeparator(SwingConstants.HORIZONTAL, manager);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(30, 10, 30, 5);
		constraints.gridwidth = 7;

		this.add(firstSeparator, constraints);

		final OptionTextPane textPane3 = new OptionTextPane("Robots:", manager);

		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.gridy = 4;
		constraints.insets = new Insets(5, 15, 0, 0);

		this.add(textPane3, constraints);

		this.mRobotsArea = new OptionTextArea("1", manager);

		constraints.gridx = 1;
		constraints.gridwidth = 6;
		constraints.insets = new Insets(5, 15, 0, 10);

		this.add(this.mRobotsArea, constraints);

		this.mStepButton = new OptionButton(STEP_BUTTON_TEXT, manager);

		constraints.weightx = 0;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.insets = new Insets(20, 20, 0, 0);

		this.add(this.mStepButton, constraints);

		this.mRoundButton = new OptionButton(ROUND_BUTTON_TEXT, manager);

		constraints.gridx = 3;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(20, -25, 0, 0);

		this.add(this.mRoundButton, constraints);

		this.mFullyButton = new OptionButton(FULLY_BUTTON_TEXT, manager);

		constraints.fill = 0;
		constraints.gridx = 5;
		constraints.insets = new Insets(20, 0, 0, -8);

		this.add(this.mFullyButton, constraints);

		final OptionTextPane stepShow = new OptionTextPane("After Step:", manager);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.gridy = 6;
		constraints.insets = new Insets(25, 20, 0, 0);

		this.add(stepShow, constraints);

		this.mStepArea = new OptionTextArea(manager);
		this.mStepArea.setEditable(false);

		constraints.gridx = 2;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(25, 0, 0, 15);

		this.add(this.mStepArea, constraints);

		this.mStepTypePane = new OptionTextPane(manager);

		constraints.gridx = 4;
		constraints.gridwidth = 3;

		constraints.insets = new Insets(25, -10, 0, 0);

		this.add(this.mStepTypePane, constraints);

		final OptionSeparator secondSeparator = new OptionSeparator(SwingConstants.HORIZONTAL, manager);
		secondSeparator.setForeground(Color.black);

		constraints.weightx = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 7;
		constraints.gridy = 7;
		constraints.insets = new Insets(30, 10, 50, 5);
		this.add(secondSeparator, constraints);

		this.mSizeSlider = new OptionSlider(SwingConstants.HORIZONTAL, 0, DrawableNodeData.DEFAULT_RADIUS * 2,
				DrawableNodeData.DEFAULT_RADIUS, manager);

		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.gridy = 8;
		constraints.insets = new Insets(270, 20, 0, 20);

		this.add(this.mSizeSlider, constraints);

		final EStyle[] supportedStyles = { EStyle.STANDARD, EStyle.DARK };

		this.mStyleSelect = new OptionComboBox<>(supportedStyles, manager);
		this.mStyleSelect.setEditable(false);
		this.mStyleSelect.setBackground(manager.getButtonColor());

		constraints.gridx = 3;
		constraints.gridwidth = 4;

		this.add(this.mStyleSelect, constraints);

		this.mComponents.add(textPane);
		this.mComponents.add(this.mSeedArea);
		this.mComponents.add(textPane2);
		this.mComponents.add(textPane3);
		this.mComponents.add(this.mTreeSizeArea);
		this.mComponents.add(this.mUseSeedButton);
		this.mComponents.add(this.mWithoutSeedButton);
		this.mComponents.add(firstSeparator);
		this.mComponents.add(this.mRobotsArea);
		this.mComponents.add(this.mStepButton);
		this.mComponents.add(this.mRoundButton);
		this.mComponents.add(this.mFullyButton);
		this.mComponents.add(stepShow);
		this.mComponents.add(this.mStepArea);
		this.mComponents.add(secondSeparator);
		this.mComponents.add(this.mSizeSlider);
		this.mComponents.add(this.mStyleSelect);
		this.mComponents.add(this.mStepTypePane);
	}

	/**
	 * Adds an {@link ActionListener} to {@link OptionPanel#mFullyButton}.
	 * 
	 * @param listener
	 *            The listener to be added
	 */
	public void addFullyButtonListener(final ActionListener listener) {
		this.mFullyButton.addActionListener(listener);
	}

	/**
	 * Adds an {@link ActionListener} to {@link OptionPanel#mRoundButton}.
	 * 
	 * @param listener
	 *            The listener to be added
	 */
	public void addRoundButtonListener(final ActionListener listener) {
		this.mRoundButton.addActionListener(listener);
	}

	/**
	 * Adds a change listener to the slider used to control the size of the
	 * tree.
	 * 
	 * @param listener
	 *            The listener to be added.
	 */
	public void addSizeSliderListener(final ChangeListener listener) {
		this.mSizeSlider.addChangeListener(listener);
	}

	/**
	 * Adds an {@link ActionListener} to {@link OptionPanel#mStepButton}.
	 * 
	 * @param listener
	 *            The listener to be added
	 */
	public void addStepButtonListener(final ActionListener listener) {
		this.mStepButton.addActionListener(listener);
	}

	/**
	 * Adds an item listener to the combo box used to control the current style.
	 * 
	 * @param listener
	 *            The listener to be added.
	 */
	public void addStyleItemListener(final ItemListener listener) {
		this.mStyleSelect.addItemListener(listener);
	}

	/**
	 * Adds an {@link ActionListener} to {@link OptionPanel#mUseSeedButton}.
	 * 
	 * @param listener
	 *            The listener to be added
	 */
	public void addUseSeedButtonListener(final ActionListener listener) {
		this.mUseSeedButton.addActionListener(listener);
	}

	/**
	 * Adds an {@link ActionListener} to {@link OptionPanel#mWithoutSeedButton}.
	 * 
	 * @param listener
	 *            The listener to be added
	 */
	public void addWithoutSeedButtonListener(final ActionListener listener) {
		this.mWithoutSeedButton.addActionListener(listener);
	}

	/**
	 * Gets the amount of robots from {@link OptionPanel#mRobotsArea}.
	 * 
	 * @return The amount of robots to use.
	 */
	public int getAmountOfRobots() {
		return Integer.parseInt(this.mRobotsArea.getText());
	}

	/**
	 * Gets the seed from {@link OptionPanel#mSeedArea}.
	 * 
	 * @return The seed.
	 */
	public long getSeed() {
		return Long.parseLong(this.mSeedArea.getText());
	}

	/**
	 * Gets the tree size from {@link OptionPanel#mTreeSizeArea}.
	 * 
	 * @return The tree size.
	 */
	public int getTreeSize() {
		return Integer.parseInt(this.mTreeSizeArea.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.mManager.getOptionPanelColor());
	}

	@Override
	public void repaint() {
		super.repaint();
		if (this.mComponents == null) {
			return;
		}

		for (final IReColorable comp : this.mComponents) {
			comp.reColor();
		}

		this.reColor();
	}

	/**
	 * Sets the amount of current steps executed.
	 * 
	 * @param steps
	 *            The new amount of executed steps.
	 */
	public void setCurrentStep(final String steps) {
		this.mStepArea.setText(steps);
	}

	/**
	 * Sets the seed on {@link OptionPanel#mSeedArea}.
	 * 
	 * @param seed
	 *            The seed to be set.
	 */
	public void setSeed(final long seed) {
		this.mSeedArea.setText("" + seed);
	}

	/**
	 * Sets the step type on the GUI.
	 * 
	 * @param stepType
	 *            The step type.
	 */
	public void setStepType(final EStep stepType) {
		if (stepType == null) {
			this.mStepTypePane.setText("");
			return;
		}

		this.mStepTypePane.setText(stepType.toString());
	}
}
