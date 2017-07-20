package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JSlider;

import de.zabuza.treeflood.demo.gui.model.DrawableNodeData;
import de.zabuza.treeflood.demo.gui.view.properties.IRecolorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;

/**
 * Provides a customized {@link JSlider} with its own color and other values
 * set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class OptionSlider extends JSlider implements IRecolorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle colors.
	 */
	private final StyleManager manager;

	/**
	 * Provides a customized {@link JSlider} with its own color and other values
	 * set.
	 * 
	 * @param mOrientation
	 *            The orientation for this slider.
	 * @param mMinValue
	 *            The minimum value for this slider.
	 * @param mMaxValue
	 *            The maximum value for this slider.
	 * @param mInitialValue
	 *            The initial value of this slider.
	 * @param mManager
	 *            The style manager used to handle colors.
	 */
	public OptionSlider(final int mOrientation, final int mMinValue, final int mMaxValue, final int mInitialValue,
			final StyleManager mManager) {
		super(mOrientation, mMinValue, mMaxValue, mInitialValue);
		this.setPaintTicks(true);
		this.setMajorTickSpacing(DrawableNodeData.DEFAULT_RADIUS);
		this.setBackground(mManager.getSliderColor());
		this.manager = mManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.manager.getSliderColor());

	}
}
