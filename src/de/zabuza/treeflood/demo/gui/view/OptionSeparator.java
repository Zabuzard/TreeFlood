package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JSeparator;

import de.zabuza.treeflood.demo.gui.view.properties.IRecolorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;

/**
 * Provides a customized {@link JSeparator} with its own color and other values
 * set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class OptionSeparator extends JSeparator implements IRecolorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle colors.
	 */
	private final StyleManager manager;

	/**
	 * Provides a customized {@link JSeparator} with its own color and other
	 * values set.
	 * 
	 * @param mOrientation
	 *            The orientation for this separator
	 * 
	 * @param mManager
	 *            The style manager used to handle colors.
	 */
	public OptionSeparator(final int mOrientation, final StyleManager mManager) {
		super(mOrientation);
		this.setForeground(mManager.getSeparatorColor());
		this.manager = mManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setForeground(this.manager.getSeparatorColor());

	}

}
