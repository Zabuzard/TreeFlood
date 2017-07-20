package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JSlider;

import de.zabuza.treeflood.demo.gui.model.DrawableNodeData;
import de.zabuza.treeflood.demo.gui.view.properties.IReColorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;

/**
 * Provides a customized {@link JSlider} with its own color and other values
 * set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class OptionSlider extends JSlider implements IReColorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle colors.
	 */
	private final StyleManager mManager;

	/**
	 * Provides a customized {@link JSlider} with its own color and other values
	 * set.
	 * 
	 * @param sliderOrientation
	 *            The orientation for this slider.
	 * @param minValue
	 *            The minimum value for this slider.
	 * @param maxValue
	 *            The maximum value for this slider.
	 * @param initialValue
	 *            The initial value of this slider.
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public OptionSlider(final int sliderOrientation, final int minValue, final int maxValue, final int initialValue,
			final StyleManager manager) {
		super(sliderOrientation, minValue, maxValue, initialValue);
		this.setPaintTicks(true);
		this.setMajorTickSpacing(DrawableNodeData.DEFAULT_RADIUS);
		this.setBackground(manager.getSliderColor());
		this.mManager = manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.mManager.getSliderColor());
	}
}
