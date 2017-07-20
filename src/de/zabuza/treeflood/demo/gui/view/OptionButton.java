package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JButton;

import de.zabuza.treeflood.demo.gui.view.properties.IRecolorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;
import de.zabuza.treeflood.demo.gui.view.util.Window;

/**
 * Provides a customized {@link JButton} having its own background color and
 * other values set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class OptionButton extends JButton implements IRecolorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle colors.
	 */
	private final StyleManager manager;

	/**
	 * Constructs an option button with the given context set as title.
	 * 
	 * @param mContext
	 *            The title of the button.
	 * 
	 * @param mManager
	 *            The style manager used to handle colors.
	 */
	public OptionButton(final String mContext, final StyleManager mManager) {
		super(mContext);
		this.setFont(Window.TEXT_BUTTON_FONT);
		this.setBackground(mManager.getButtonColor());
		this.setForeground(mManager.getDefaultFontColor());
		this.setFocusPainted(false);
		this.manager = mManager;

	}

	/**
	 * Constructs an option button with no text set.
	 * 
	 * @param mManager
	 *            The style manager used to handle colors.
	 */
	public OptionButton(final StyleManager mManager) {
		this("", mManager);

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
