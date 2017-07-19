package de.zabuza.treeflood.demo.gui.model;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import de.zabuza.treeflood.demo.gui.model.properties.IColorable;
import de.zabuza.treeflood.demo.gui.model.properties.IHasDescription;
import de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener;
import de.zabuza.treeflood.demo.gui.view.util.Window;
import de.zabuza.treeflood.exploration.localstorage.Information;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Provides additional information for {@link ITreeNode}. Additional information
 * means, every {@link ITreeNode} gets the following values:<br>
 * <b>x-Coordinate, y-Coordinate, radius, depth, description, color</b><br>
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class DrawableNodeData implements IHasDescription, IColorable {

	/**
	 * The default value for the radius for the node mapped to this data object.
	 */
	public static final int DEFAULT_RADIUS = 30;

	/**
	 * The color of this node.
	 */
	private Color color;

	/**
	 * The depth of the node mapped to this.
	 */
	private int depth;

	/**
	 * The description of this node.
	 */
	private String description;

	/**
	 * Determines whether the mouse is currently hovering over this element or
	 * not.
	 */
	private boolean hovering;

	/**
	 * The tooltip shown for this node when hovered.
	 */
	private final InformationPanel informationTooltip;

	/**
	 * The x-Coordinate of the child with the largest x-Coordinate (the one
	 * being on the rightmost side).
	 */
	private int largestChildX;

	/**
	 * The node which gets mapped to this object.
	 */
	private final ITreeNode node;

	/**
	 * The current radius of this data object.
	 */
	private int radius;

	/**
	 * The "should-be-x-coordinate" of this node. Only has a valid value if
	 * every child of this node reported its x-Coordinate to this node by
	 * calling this nodes {@link DrawableNodeData#setChildX(int)} method.
	 */
	private int relativeXPosition;

	/**
	 * A boolean determining whether the tooltip should be rendered or not.
	 */
	private boolean showTooltip;

	/**
	 * The x-Coordinate of the child with the smallest x-Coordinate (the one
	 * being on the leftmost side).
	 */
	private int smallestChildX;

	/**
	 * The x-Coordinate of this data object. The x-Coordinate describes the
	 * center of the node mapped to this data object.
	 */
	private int x;

	/**
	 * The y-Coordinate of this data object. The y-Coordinate describes the
	 * center of the node mapped to this data object.
	 */
	private int y;

	/**
	 * Constructs a new DrawableNodeData with the given parameters.
	 * 
	 * @param mX
	 *            The x-Coordinate of this object.
	 * @param mY
	 *            The y-Coordinate of this object.
	 * @param mRadius
	 *            The radius of this object.
	 * @param mDepth
	 *            The depth of this object.
	 * @param mNode
	 *            the node which gets mapped to this object.
	 * @param mWindow
	 *            the Window object used for position related values.
	 */
	public DrawableNodeData(final int mX, final int mY, final int mRadius, final int mDepth, final ITreeNode mNode,
			final Window mWindow) {
		this.x = mX;
		this.y = mY;
		this.radius = mRadius;
		this.depth = mDepth;
		this.relativeXPosition = -1;
		this.smallestChildX = -1;
		this.largestChildX = -1;
		this.description = "";
		this.color = Window.UNVISITED_NODE_COLOR;

		this.node = mNode;

		this.description = "0";
		this.informationTooltip = new InformationPanel(this, mWindow);

	}

	/**
	 * Constructs a new DrawableNodeData object with the given parameters. The
	 * radius is specified by {@link DrawableNodeData#DEFAULT_RADIUS}.
	 * 
	 * @param mX
	 *            The x-Coordinate of this object.
	 * @param mY
	 *            The y-Coordinate of this object.
	 * @param mDepth
	 *            The depth of this object.
	 * @param mNode
	 *            the node which gets mapped to this object.
	 * @param mWindow
	 *            the Window object used for position related values.
	 */
	public DrawableNodeData(final int mX, final int mY, final int mDepth, final ITreeNode mNode, final Window mWindow) {
		this(mX, mY, DEFAULT_RADIUS, mDepth, mNode, mWindow);

	}

	/**
	 * Constructs a new DrawableNodeData object with the given depth, the (x,
	 * y)-Coordinates being (0, 0) and the radius being
	 * {@link DrawableNodeData#DEFAULT_RADIUS}.
	 * 
	 * @param mDepth
	 *            The depth of this node.
	 * @param mNode
	 *            the node which gets mapped to this object.
	 * @param mWindow
	 *            the Window object used for position related values.
	 */
	public DrawableNodeData(final int mDepth, final ITreeNode mNode, final Window mWindow) {
		this(0, 0, mDepth, mNode, mWindow);

	}

	/**
	 * Constructs a new DrawableNodeData object with the depth 0, the (x,
	 * y)-Coordinates being (0, 0) and the radius being
	 * {@link DrawableNodeData#DEFAULT_RADIUS}.
	 * 
	 * @param mNode
	 *            the node which gets mapped to this object.
	 * 
	 * @param mWindow
	 *            the Window object used for position related values.
	 */
	public DrawableNodeData(final ITreeNode mNode, final Window mWindow) {
		this(0, mNode, mWindow);

	}

	/**
	 * Checks if a hover occurred on this element and fires a hover listener
	 * callback if one occurred on all listeners.
	 * 
	 * @param mPoint
	 *            The point of the mouse.
	 */
	public void checkHover(final Point mPoint, final List<INodeHoverListener> mListeners) {
		if (!this.hovering && this.contains(mPoint)) {
			for (final INodeHoverListener listener : mListeners) {
				listener.startHover(this.node);

			}
			this.hovering = true;

		} else if (this.hovering && !this.contains(mPoint)) {
			for (final INodeHoverListener listener : mListeners) {
				listener.stopHover(this.node);

			}
			this.hovering = false;

		}
	}

	public boolean contains(final Point mPoint) {
		if (Math.pow((mPoint.x - this.getX()), 2) + Math.pow((mPoint.y - this.getY()), 2) < Math.pow(this.getRadius(),
				2)) {
			return true;

		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.demo.gui.controller.properties.IColorable#getColor()
	 */
	@Override
	public Color getColor() {
		return this.color;

	}

	/**
	 * Gets the depth of this object.
	 * 
	 * @return The depth.
	 */
	public int getDepth() {
		return this.depth;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.controller.properties.IHasDescription#getDescription(
	 * )
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gets the x-Coordinate of the Description (the x-location where the
	 * description of this object should be rendered).
	 * 
	 * @return The x-Coordinate mentioned.
	 */
	public int getDescriptionX() {
		return this.getX() - (this.getDescription().length() * 5);

	}

	/**
	 * Gets the y-Coordinate of the Description (the y-location where the
	 * description of this object should be rendered).
	 * 
	 * @return The y-Coordinate mentioned.
	 */
	public int getDescriptionY() {
		return this.getY() + 6;

	}

	/**
	 * Gets this objects {@link InformationPanel}.
	 * 
	 * @return The information panel mentioned.
	 */
	public InformationPanel getInformationPanel() {
		return this.informationTooltip;

	}

	/**
	 * Gets the radius of this object.
	 * 
	 * @return The radius.
	 */
	public int getRadius() {
		return this.radius;

	}

	/**
	 * Gets the "should-be-x-coordinate" of this node. Only has a valid value if
	 * every child of this node reported its x-Coordinate to this node by
	 * calling this nodes {@link DrawableNodeData#setChildX(int)} method.
	 * 
	 * @return the "should-be-x-coordinate" of this node
	 */
	public int getRelativeXLocation() {
		return this.relativeXPosition;

	}

	/**
	 * Gets the x-Coordinate of this object.
	 * 
	 * @return The x-Coordinate.
	 */
	public int getX() {
		return this.x;

	}

	/**
	 * Gets the y-Coordinate of this object.
	 * 
	 * @return The y-Coordinate.
	 */
	public int getY() {
		return this.y;

	}

	/**
	 * Sets {@link DrawableNodeData#showTooltip} to <tt>false</tt>, so the
	 * tooltip-rendering can be stopped.
	 */
	public void hideTooltip() {
		this.showTooltip = false;

	}

	/**
	 * Gets a boolean signalizing whether the tooltip should be rendered or not.
	 * 
	 * @return The boolean mentioned.
	 */
	public boolean isTooltipOn() {
		return this.showTooltip;

	}

	/**
	 * Sets the center for this object. Simply does so by calling
	 * {@link DrawableNodeData#setX(int)} and {@link DrawableNodeData#setY(int)}
	 * .
	 * 
	 * @param mX
	 *            The new value for the x-Coordinate.
	 * @param mY
	 *            The new value for the y-Coordinate.
	 */
	public void setCenter(final int mX, final int mY) {
		this.setX(mX);
		this.setY(mY);

	}

	/**
	 * Updates the value described in
	 * {@link DrawableNodeData#getRelativeXLocation()}. Has to be called by
	 * every child to achieve the desired effect, before operating on this
	 * object.
	 * 
	 * @param mChildX
	 *            The x-Coordinate of this objects child.
	 */
	public void setChildX(final int mChildX) {
		if (this.largestChildX == -1 || this.largestChildX < mChildX) {
			this.largestChildX = mChildX;

		}
		if (this.smallestChildX == -1 || this.smallestChildX > mChildX) {
			this.smallestChildX = mChildX;

		}
		this.relativeXPosition = ((this.largestChildX - this.smallestChildX) / 2) + this.smallestChildX;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.demo.gui.controller.properties.IColorable#setColor(
	 * java.awt.Color)
	 */
	@Override
	public void setColor(final Color mColor) {
		this.color = mColor;

	}

	/**
	 * Sets the depth for this object.
	 * 
	 * @param mDepth
	 *            The new value for the depth.
	 */
	public void setDepth(final int mDepth) {
		this.depth = mDepth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.controller.properties.IHasDescription#setDescription(
	 * java.lang.String)
	 */
	@Override
	public void setDescription(final String mDescription) {
		this.description = mDescription;

	}

	/**
	 * Sets the information for this objects {@link InformationPanel}.
	 * 
	 * @param mInformation
	 *            The informations to be set.
	 */
	public void setInformation(final List<Information> mInformation) {
		this.informationTooltip.setInformation(mInformation);

	}

	/**
	 * Sets the radius for this object.
	 * 
	 * @param mRadius
	 *            The new value for the radius.
	 */
	public void setRadius(final int mRadius) {
		this.radius = mRadius;

	}

	/**
	 * Sets the x-Coordinate for this object.
	 * 
	 * @param mX
	 *            The new value for the x-Coordinate.
	 */
	public void setX(final int mX) {
		this.x = mX;

	}

	/**
	 * Sets the y-Coordinate for this object.
	 * 
	 * @param mY
	 *            The new value for the y-Coordinate.
	 */
	public void setY(final int mY) {
		this.y = mY;

	}

	/**
	 * Sets {@link DrawableNodeData#showTooltip} to <tt>true</tt>, so the
	 * tooltip can be rendered.
	 */
	public void showTooltip() {
		this.showTooltip = true;

	}
}
