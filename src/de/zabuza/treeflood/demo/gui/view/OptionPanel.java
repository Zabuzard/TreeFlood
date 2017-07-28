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
import de.zabuza.treeflood.exploration.localstorage.EStage;
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

	public final static String INITIAL_TEXT = "INITIAL";

	public final static String NOP_TEXT = "NOP";

	public final static String REGULAR_TEXT = "REGULAR";

	public final static String UPDATE_TEXT = "UPDATE";

	public final static String RETURN_TEXT = "RETURN";

	public final static String MOVE_TEXT = "MOVE";

	public final static String WRITE_TEXT = "WRITE";

	public final static String READ_TEXT = "READ";

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
	 * The pane used to display the initial step type.
	 */
	private final OptionHighlightTextPane mInitialPane;

	/**
	 * The pane used to display the NOP step type.
	 */
	private final OptionHighlightTextPane mNopPane;

	/**
	 * The pane used to display the regular step type.
	 */
	private final OptionHighlightTextPane mRegularPane;

	/**
	 * The pane used to display the update step type.
	 */
	private final OptionHighlightTextPane mUpdatePane;

	/**
	 * The pane used to display the return step type.
	 */
	private final OptionHighlightTextPane mReturnPane;

	/**
	 * The pane used to display the move stage.
	 */
	private final OptionHighlightTextPane mMovePane;

	/**
	 * The pane used to display the write stage.
	 */
	private final OptionHighlightTextPane mWritePane;

	/**
	 * The pane used to display the read stage.
	 */
	private final OptionHighlightTextPane mReadPane;

	/**
	 * A list holding all the step types displayed on the GUI.
	 */
	private final List<OptionHighlightTextPane> mStepTypes;

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
		this.mStepTypes = new LinkedList<>();

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
		constraints.insets = new Insets(20, -37, 0, 0);

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
		constraints.gridwidth = 5;
		constraints.insets = new Insets(25, 10, 0, 10);

		this.add(this.mStepArea, constraints);

		final OptionSeparator secondSeparator = new OptionSeparator(SwingConstants.HORIZONTAL, manager);

		constraints.weightx = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 7;
		constraints.gridy = 7;
		constraints.insets = new Insets(30, 10, 10, 5);
		this.add(secondSeparator, constraints);

		this.mInitialPane = new OptionHighlightTextPane(INITIAL_TEXT, manager);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(-20, 20, 30, 0);
		constraints.gridy = 8;

		this.add(this.mInitialPane, constraints);

		this.mMovePane = new OptionHighlightTextPane(MOVE_TEXT, manager);

		constraints.gridx = 3;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(30, 40, 30, 0);
		constraints.gridy = 8;

		this.add(this.mMovePane, constraints);

		this.mNopPane = new OptionHighlightTextPane(NOP_TEXT, manager);

		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(-35, 20, 15, 0);
		constraints.gridy = 9;

		this.add(this.mNopPane, constraints);

		final OptionSeparator stepSeperator = new OptionSeparator(SwingConstants.HORIZONTAL, manager);

		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.gridy = 10;
		constraints.insets = new Insets(0, 10, 15, 0);

		this.add(stepSeperator, constraints);

		this.mRegularPane = new OptionHighlightTextPane(REGULAR_TEXT, manager);

		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(0, 20, 30, 0);
		constraints.gridy = 11;

		this.add(this.mRegularPane, constraints);

		this.mWritePane = new OptionHighlightTextPane(WRITE_TEXT, manager);

		constraints.gridx = 3;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(0, 38, 30, 0);
		constraints.gridy = 11;

		this.add(this.mWritePane, constraints);

		this.mUpdatePane = new OptionHighlightTextPane(UPDATE_TEXT, manager);

		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(-30, 20, 30, 0);
		constraints.gridy = 12;

		this.add(this.mUpdatePane, constraints);

		this.mReadPane = new OptionHighlightTextPane(READ_TEXT, manager);

		constraints.gridx = 3;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(30, 40, 30, 0);
		constraints.gridy = 12;

		this.add(this.mReadPane, constraints);

		this.mReturnPane = new OptionHighlightTextPane(RETURN_TEXT, manager);

		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(-30, 20, 30, 0);
		constraints.gridy = 13;

		this.add(this.mReturnPane, constraints);

		final OptionSeparator finalSeparator = new OptionSeparator(SwingConstants.HORIZONTAL, manager);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 14;
		constraints.insets = new Insets(0, 10, 0, 5);
		constraints.gridwidth = 7;

		this.add(finalSeparator, constraints);

		this.mSizeSlider = new OptionSlider(SwingConstants.HORIZONTAL, 0, DrawableNodeData.DEFAULT_RADIUS * 2,
				DrawableNodeData.DEFAULT_RADIUS, manager);

		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.gridy = 15;
		constraints.insets = new Insets(30, 20, 0, 20);

		this.add(this.mSizeSlider, constraints);

		final EStyle[] supportedStyles = { EStyle.STANDARD, EStyle.DARK };

		this.mStyleSelect = new OptionComboBox<>(supportedStyles, manager);
		this.mStyleSelect.setEditable(false);
		this.mStyleSelect.setBackground(manager.getButtonColor());

		constraints.gridx = 3;
		constraints.gridwidth = 4;

		this.add(this.mStyleSelect, constraints);

		final OptionSeparator stepSeperatorVertical = new OptionSeparator(SwingConstants.VERTICAL, manager);
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.gridx = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 6;
		constraints.insets = new Insets(-10, 0, 0, 45);
		constraints.gridy = 8;

		this.add(stepSeperatorVertical, constraints);

		this.mStepTypes.add(this.mInitialPane);
		this.mStepTypes.add(this.mNopPane);
		this.mStepTypes.add(this.mRegularPane);
		this.mStepTypes.add(this.mUpdatePane);
		this.mStepTypes.add(this.mReturnPane);

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
		this.mComponents.add(this.mInitialPane);
		this.mComponents.add(this.mNopPane);
		this.mComponents.add(this.mRegularPane);
		this.mComponents.add(this.mUpdatePane);
		this.mComponents.add(this.mReturnPane);
		this.mComponents.add(stepSeperator);
		this.mComponents.add(stepSeperatorVertical);
		this.mComponents.add(finalSeparator);
		this.mComponents.add(this.mMovePane);
		this.mComponents.add(this.mWritePane);
		this.mComponents.add(this.mReadPane);

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

		for (final OptionHighlightTextPane stepPane : this.mStepTypes) {
			if (stepType == null) {
				stepPane.deHighlight();
				continue;

			}

			if (stepPane.getText().equals(stepType.toString())) {
				stepPane.highlight();

			} else {
				stepPane.deHighlight();

			}
		}
	}

	/**
	 * Sets the stage type on the GUI.
	 * 
	 * @param stageType
	 *            The stage type.
	 */
	public void setStageType(final EStage stageType) {

	}
}
