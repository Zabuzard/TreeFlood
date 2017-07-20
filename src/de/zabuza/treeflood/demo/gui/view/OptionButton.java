package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JButton;

import de.zabuza.treeflood.demo.gui.view.properties.IReColorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;
import de.zabuza.treeflood.demo.gui.view.util.Window;

/**
 * Provides a customized {@link JButton} having its own background color and
 * other values set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class OptionButton extends JButton implements IReColorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle colors.
	 */
	private final StyleManager mManager;

	/**
	 * Constructs an option button with the given context set as title.
	 * 
	 * @param context
	 *            The title of the button.
	 * 
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public OptionButton(final String context, final StyleManager manager) {
		super(context);
		this.setFont(Window.TEXT_BUTTON_FONT);
		this.setBackground(manager.getButtonColor());
		this.setForeground(manager.getDefaultFontColor());
		this.setFocusPainted(false);
		this.mManager = manager;
	}

	/**
	 * Constructs an option button with no text set.
	 * 
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public OptionButton(final StyleManager manager) {
		this("", manager);
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
