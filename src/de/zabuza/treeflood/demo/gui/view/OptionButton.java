package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JButton;

import de.zabuza.treeflood.demo.gui.view.util.Window;

/**
 * Provides a customized {@link JButton} having its own background color and
 * other values set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class OptionButton extends JButton {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an option button with no text set.
	 */
	public OptionButton() {
		this("");

	}

	/**
	 * Constructs an option button with the given context set as title.
	 * 
	 * @param mContext
	 *            The title of the button.
	 */
	public OptionButton(final String mContext) {
		super(mContext);
		this.setFont(Window.TEXT_BUTTON_FONT);
		this.setBackground(Window.BUTTON_COLOR);
		this.setFocusPainted(false);

	}
}
