package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JTextPane;

import de.zabuza.treeflood.demo.gui.view.properties.IReColorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;
import de.zabuza.treeflood.demo.gui.view.util.Window;

/**
 * Provides a customized {@link JTextPane} having some special attributes set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class OptionTextPane extends JTextPane implements IReColorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle colors.
	 */
	private final StyleManager mManager;

	/**
	 * Constructs a new OptionTextPane with the given text and the given style
	 * manager.
	 * 
	 * @param context
	 *            The context written onto this pane.
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public OptionTextPane(final String context, final StyleManager manager) {
		super();
		this.setText(context);

		this.setFocusable(false);
		this.setEditable(false);
		this.setFont(Window.TEXT_PANE_FONT);

		this.mManager = manager;

		this.setBackground(manager.getTextPaneColor());
		this.setForeground(manager.getDefaultFontColor());
	}

	/**
	 * Constructs a new OptionTextPane with no text and the style manager given.
	 * 
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public OptionTextPane(final StyleManager manager) {
		this(" ", manager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.mManager.getTextPaneColor());
		this.setForeground(this.mManager.getDefaultFontColor());
	}
}
