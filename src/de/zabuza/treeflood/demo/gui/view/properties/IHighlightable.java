package de.zabuza.treeflood.demo.gui.view.properties;

/**
 * An interface for objects which are highlightable.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IHighlightable {

	/**
	 * Highlights this object.
	 */
	public void highlight();

	/**
	 * De-Highlights this object.
	 */
	public void deHighlight();

	/**
	 * Returns whether this object is currently highlighted or not.
	 * 
	 * @return <tt>True</tt> if the object is highlighted, <tt>false</tt>
	 *         otherwise.
	 */
	public boolean isHighlighted();

}
