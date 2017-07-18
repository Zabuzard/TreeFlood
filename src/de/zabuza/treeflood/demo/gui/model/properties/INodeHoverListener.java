package de.zabuza.treeflood.demo.gui.model.properties;

import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Interface for objects that want to listen to events of type node hovered.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface INodeHoverListener {
	/**
	 * Event listener callback for node hover event starts. A start is then
	 * fired once, if the mouse enters the node.
	 * 
	 * @param mNode
	 *            The node on which the event got fired.
	 */
	public void startHover(ITreeNode mNode);

	/**
	 * Event listener callback for node hover event stops. A stop is then fired
	 * once, if the mouse exits the node.
	 * 
	 * @param mNode
	 *            The node on which the event got fired.
	 */
	public void stopHover(ITreeNode mNode);

}
