package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JSeparator;

import de.zabuza.treeflood.demo.gui.view.properties.IReColorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;

/**
 * Provides a customized {@link JSeparator} with its own color and other values
 * set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class OptionSeparator extends JSeparator implements IReColorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle colors.
	 */
	private final StyleManager mManager;

	/**
	 * Provides a customized {@link JSeparator} with its own color and other
	 * values set.
	 * 
	 * @param orientation
	 *            The orientation for this separator
	 * 
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public OptionSeparator(final int orientation, final StyleManager manager) {
		super(orientation);
		this.setForeground(manager.getSeparatorColor());
		this.mManager = manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setForeground(this.mManager.getSeparatorColor());
	}
}
