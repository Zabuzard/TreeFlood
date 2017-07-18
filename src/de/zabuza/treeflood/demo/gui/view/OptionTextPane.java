package de.zabuza.treeflood.demo.gui.view;

import java.awt.Color;

import javax.swing.JTextPane;

import de.zabuza.treeflood.demo.gui.view.util.Window;

/**
 * Provides a customized {@link JTextPane} having some special attributes set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class OptionTextPane extends JTextPane {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new OptionTextPane with no text and the background color
	 * white.
	 */
	public OptionTextPane() {
		this("");
	}

	/**
	 * Constructs a new OptionTextPane with no text and the background color
	 * given.
	 * 
	 * @param mBackground
	 *            The background color of this object.
	 */
	public OptionTextPane(final Color mBackground) {
		this("", mBackground);

	}

	/**
	 * Constructs a new OptionTextPane with the given text and the background
	 * color white.
	 * 
	 * @param mContext
	 *            The context written onto this pane.
	 */
	public OptionTextPane(final String mContext) {
		this(mContext, Color.WHITE);

	}

	/**
	 * Constructs a new OptionTextPane with the given text and the given
	 * background.
	 * 
	 * @param mContext
	 *            The context written onto this pane.
	 * @param mBackground
	 *            The background color of this object.
	 */
	public OptionTextPane(final String mContext, final Color mBackground) {
		super();
		this.setText(mContext);
		this.setBackground(mBackground);

		this.setFocusable(false);
		this.setEditable(false);
		this.setFont(Window.TEXT_PANE_FONT);

	}

}
