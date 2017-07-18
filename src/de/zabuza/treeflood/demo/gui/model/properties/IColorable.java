package de.zabuza.treeflood.demo.gui.model.properties;

import java.awt.Color;

/**
 * An interface for colorable objects.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IColorable {
	
	/**
	 * Gets the current color of this object.
	 * 
	 * @return The current color.
	 */
	public Color getColor();
	
	/**
	 * Sets the current color of this object.
	 * 
	 * @param mColor
	 * 			The new color of this object.
	 */
	public void setColor(Color mColor);

}
