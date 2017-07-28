package de.zabuza.treeflood.demo.gui.view;

import de.zabuza.treeflood.demo.gui.view.properties.IHighlightable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;

/**
 * A {@link OptionTextPane} which is highlightable.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class OptionHighlightTextPane extends OptionTextPane implements IHighlightable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Determining whether this object is currently highlighted or not.
	 */
	private boolean mHighlighted;

	/**
	 * The style manager used to get the current styles color.
	 */
	private final StyleManager mManager;

	/**
	 * A {@link OptionTextPane} which is highlightable.
	 * 
	 * @param context
	 *            The context written onto this pane.
	 * @param manager
	 *            The style manager used to get the current styles color.
	 */
	public OptionHighlightTextPane(final String context, final StyleManager manager) {
		super(context, manager);

		this.mManager = manager;
		this.mHighlighted = false;

	}

	/**
	 * A {@link OptionTextPane} which is highlightable.
	 * 
	 * @param manager
	 *            The style manager used to get the current styles color.
	 */
	public OptionHighlightTextPane(final StyleManager manager) {
		super(manager);

		this.mManager = manager;
		this.mHighlighted = false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.demo.gui.view.properties.IHighlightable#deHighlight()
	 */
	@Override
	public void deHighlight() {
		this.mHighlighted = false;
		this.setForeground(this.mManager.getDefaultFontColor());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.demo.gui.view.properties.IHighlightable#highlight()
	 */
	@Override
	public void highlight() {
		this.mHighlighted = true;
		this.setForeground(this.mManager.getHighlightingColor());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.demo.gui.view.properties.IHighlightable#isHighlighted
	 * ()
	 */
	@Override
	public boolean isHighlighted() {
		return this.mHighlighted;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		this.setBackground(this.mManager.getTextPaneColor());

		if (this.mHighlighted) {
			this.setForeground(this.mManager.getHighlightingColor());
			return;

		}
		this.setForeground(this.mManager.getDefaultFontColor());

	}
}
