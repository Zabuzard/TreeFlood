package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JComboBox;

import de.zabuza.treeflood.demo.gui.view.properties.IReColorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;

/**
 * Provides a customized {@link JComboBox} by setting own colors and other set
 * values.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * @param <E>
 *            The class of the objects this combo box contains
 *
 */
public final class OptionComboBox<E> extends JComboBox<E> implements IReColorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used for handling colors.
	 */
	private final StyleManager mManager;

	/**
	 * Creates a new option combo box setting specific parameters.
	 * 
	 * @param elements
	 *            The elements with which to initialize this combo box.
	 * 
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public OptionComboBox(final E[] elements, final StyleManager manager) {
		super(elements);
		this.setBackground(manager.getButtonColor());
		this.setForeground(manager.getDefaultFontColor());
		this.mManager = manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.mManager.getButtonColor());
		this.setForeground(this.mManager.getDefaultFontColor());
	}
}
