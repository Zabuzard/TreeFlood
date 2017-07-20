package de.zabuza.treeflood.demo.gui.view;

import javax.swing.JTextPane;

import de.zabuza.treeflood.demo.gui.view.properties.IRecolorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;
import de.zabuza.treeflood.demo.gui.view.util.Window;

/**
 * Provides a customized {@link JTextPane} having some special attributes set.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class OptionTextPane extends JTextPane implements IRecolorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The style manager used to handle colors.
	 */
	private final StyleManager manager;

	/**
	 * Constructs a new OptionTextPane with the given text and the given style
	 * manager.
	 * 
	 * @param mContext
	 *            The context written onto this pane.
	 * @param mManager
	 *            The style manager used to handle colors.
	 */
	public OptionTextPane(final String mContext, final StyleManager mManager) {
		super();
		this.setText(mContext);

		this.setFocusable(false);
		this.setEditable(false);
		this.setFont(Window.TEXT_PANE_FONT);

		this.manager = mManager;

		this.setBackground(mManager.getTextPaneColor());
		this.setForeground(mManager.getDefaultFontColor());
	}

	/**
	 * Constructs a new OptionTextPane with no text and the style manager given.
	 * 
	 * @param mManager
	 *            The style manager used to handle colors.
	 */
	public OptionTextPane(final StyleManager mManager) {
		this(" ", mManager);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.manager.getTextPaneColor());
		this.setForeground(this.manager.getDefaultFontColor());

	}

}
