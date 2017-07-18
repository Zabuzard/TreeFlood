package de.zabuza.treeflood.demo.gui.model.properties;

/**
 * An interface for every object which has a description, e.g. a String further
 * describing the objects behavior or the like.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IHasDescription {

	/**
	 * Gets the description of this object.
	 * 
	 * @return The description.
	 */
	public String getDescription();

	/**
	 * Sets the description of this object. A description can be used to
	 * describe an objects behavior or the like.
	 * 
	 * @param mDescription
	 *            The description of this object.
	 */
	public void setDescription(String mDescription);

}
