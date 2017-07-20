package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JComboBox;

import de.zabuza.treeflood.demo.gui.view.properties.IRecolorable;
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
public class OptionComboBox<E> extends JComboBox<E> implements IRecolorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used for handling colors.
	 */
	private final StyleManager manager;

	/**
	 * Creates a new option combo box setting specific parameters.
	 * 
	 * @param mElements
	 *            The elements with which to initialize this combo box.
	 * 
	 * @param mManager
	 *            The style manager used to handle colors.
	 */
	public OptionComboBox(final E[] mElements, final StyleManager mManager) {
		super(mElements);
		this.setBackground(mManager.getButtonColor());
		this.setForeground(mManager.getDefaultFontColor());
		this.manager = mManager;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.manager.getButtonColor());
		this.setForeground(this.manager.getDefaultFontColor());

	}

}
