package de.zabuza.treeflood.demo.gui.model;

import java.awt.Point;
import java.util.List;

import de.zabuza.treeflood.demo.gui.model.properties.IHasDescription;
import de.zabuza.treeflood.demo.gui.model.properties.INodeHoverListener;
import de.zabuza.treeflood.demo.gui.view.util.Window;
import de.zabuza.treeflood.exploration.localstorage.Information;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * Provides additional information for {@link ITreeNode}.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class DrawableNodeData implements IHasDescription {

	/**
	 * The default value for the radius for the node mapped to this data object.
	 */
	public static final int DEFAULT_RADIUS = 30;

	/**
	 * The depth of the node mapped to this.
	 */
	private int mDepth;

	/**
	 * The description of this node.
	 */
	private String mDescription;

	/**
	 * Determines whether the mouse is currently hovering over this element or
	 * not.
	 */
	private boolean mHovering;

	/**
	 * The tooltip shown for this node when hovered.
	 */
	private final InformationPanel mInformationTooltip;

	/**
	 * Whether this node was yet visited or not.
	 */
	private boolean mIsVisited;

	/**
	 * The x-Coordinate of the child with the largest x-Coordinate (the one
	 * being on the rightmost side).
	 */
	private int mLargestChildX;

	/**
	 * The node which gets mapped to this object.
	 */
	private final ITreeNode mNode;

	/**
	 * The current radius of this data object.
	 */
	private int mRadius;

	/**
	 * The "should-be-x-coordinate" of this node. Only has a valid value if
	 * every child of this node reported its x-Coordinate to this node by
	 * calling this nodes {@link DrawableNodeData#setChildX(int)} method.
	 */
	private int mRelativeXPosition;

	/**
	 * A boolean determining whether the tooltip should be rendered or not.
	 */
	private boolean mShowTooltip;

	/**
	 * The x-Coordinate of the child with the smallest x-Coordinate (the one
	 * being on the leftmost side).
	 */
	private int mSmallestChildX;

	/**
	 * The x-Coordinate of this data object. The x-Coordinate describes the
	 * center of the node mapped to this data object.
	 */
	private int mX;

	/**
	 * The y-Coordinate of this data object. The y-Coordinate describes the
	 * center of the node mapped to this data object.
	 */
	private int mY;

	/**
	 * Constructs a new DrawableNodeData with the given parameters.
	 * 
	 * @param x
	 *            The x-Coordinate of this object.
	 * @param y
	 *            The y-Coordinate of this object.
	 * @param radius
	 *            The radius of this object.
	 * @param depth
	 *            The depth of this object.
	 * @param node
	 *            The node which gets mapped to this object.
	 * @param window
	 *            The Window object used for position related values.
	 */
	public DrawableNodeData(final int x, final int y, final int radius, final int depth, final ITreeNode node,
			final Window window) {
		this.mX = x;
		this.mY = y;
		this.mRadius = radius;
		this.mDepth = depth;
		this.mRelativeXPosition = -1;
		this.mSmallestChildX = -1;
		this.mLargestChildX = -1;
		this.mDescription = "";
		this.mIsVisited = false;

		this.mNode = node;

		this.mDescription = "0";
		this.mInformationTooltip = new InformationPanel(this, window);
	}

	/**
	 * Constructs a new DrawableNodeData object with the given parameters. The
	 * radius is specified by {@link DrawableNodeData#DEFAULT_RADIUS}.
	 * 
	 * @param x
	 *            The x-Coordinate of this object.
	 * @param y
	 *            The y-Coordinate of this object.
	 * @param depth
	 *            The depth of this object.
	 * @param node
	 *            The node which gets mapped to this object.
	 * @param window
	 *            The Window object used for position related values.
	 */
	public DrawableNodeData(final int x, final int y, final int depth, final ITreeNode node, final Window window) {
		this(x, y, DEFAULT_RADIUS, depth, node, window);
	}

	/**
	 * Constructs a new DrawableNodeData object with the given depth, the (x,
	 * y)-Coordinates being (0, 0) and the radius being
	 * {@link DrawableNodeData#DEFAULT_RADIUS}.
	 * 
	 * @param depth
	 *            The depth of this node.
	 * @param node
	 *            The node which gets mapped to this object.
	 * @param window
	 *            The Window object used for position related values.
	 */
	public DrawableNodeData(final int depth, final ITreeNode node, final Window window) {
		this(0, 0, depth, node, window);
	}

	/**
	 * Constructs a new DrawableNodeData object with the depth 0, the (x,
	 * y)-Coordinates being (0, 0) and the radius being
	 * {@link DrawableNodeData#DEFAULT_RADIUS}.
	 * 
	 * @param node
	 *            The node which gets mapped to this object.
	 * 
	 * @param window
	 *            The Window object used for position related values.
	 */
	public DrawableNodeData(final ITreeNode node, final Window window) {
		this(0, node, window);
	}

	/**
	 * Checks if a hover occurred on this element and fires a hover listener
	 * callback if one occurred on all listeners.
	 * 
	 * @param point
	 *            The point of the mouse.
	 * 
	 * @param listeners
	 *            The listeners on which to fire the event.
	 */
	public void checkHover(final Point point, final List<INodeHoverListener> listeners) {
		if (!this.mHovering && this.contains(point)) {
			for (final INodeHoverListener listener : listeners) {
				listener.startHover(this.mNode);
			}
			this.mHovering = true;

		} else if (this.mHovering && !this.contains(point)) {
			for (final INodeHoverListener listener : listeners) {
				listener.stopHover(this.mNode);
			}
			this.mHovering = false;
		}
	}

	/**
	 * Checks if the given point is contained in this node.
	 * 
	 * @param point
	 *            The point of which to check if its contained in this node.
	 * @return <tt>True</tt> if the point is contained in this node,
	 *         <tt>false</tt> otherwise.
	 */
	public boolean contains(final Point point) {
		if (Math.pow((point.x - this.getX()), 2) + Math.pow((point.y - this.getY()), 2) < Math.pow(this.getRadius(),
				2)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the depth of this object.
	 * 
	 * @return The depth.
	 */
	public int getDepth() {
		return this.mDepth;
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
		return this.mDescription;
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
		return this.mInformationTooltip;
	}

	/**
	 * Gets the radius of this object.
	 * 
	 * @return The radius.
	 */
	public int getRadius() {
		return this.mRadius;
	}

	/**
	 * Gets the "should-be-x-coordinate" of this node. Only has a valid value if
	 * every child of this node reported its x-Coordinate to this node by
	 * calling this nodes {@link DrawableNodeData#setChildX(int)} method.
	 * 
	 * @return the "should-be-x-coordinate" of this node
	 */
	public int getRelativeXLocation() {
		return this.mRelativeXPosition;
	}

	/**
	 * Gets the current visited status of this node.
	 * 
	 * @return <tt>True</tt> if the node was visited, <tt>false</tt> otherwise.
	 */
	public boolean getVisitedStatus() {
		return this.mIsVisited;
	}

	/**
	 * Gets the x-Coordinate of this object.
	 * 
	 * @return The x-Coordinate.
	 */
	public int getX() {
		return this.mX;
	}

	/**
	 * Gets the y-Coordinate of this object.
	 * 
	 * @return The y-Coordinate.
	 */
	public int getY() {
		return this.mY;
	}

	/**
	 * Sets {@link DrawableNodeData#mShowTooltip} to <tt>false</tt>, so the
	 * tooltip-rendering can be stopped.
	 */
	public void hideTooltip() {
		this.mShowTooltip = false;
	}

	/**
	 * Gets a boolean that signalizes whether the tooltip should be rendered or
	 * not.
	 * 
	 * @return The boolean mentioned.
	 */
	public boolean isTooltipOn() {
		return this.mShowTooltip;
	}

	/**
	 * Sets the center for this object. Simply does so by calling
	 * {@link DrawableNodeData#setX(int)} and {@link DrawableNodeData#setY(int)}
	 * .
	 * 
	 * @param x
	 *            The new value for the x-Coordinate.
	 * @param y
	 *            The new value for the y-Coordinate.
	 */
	public void setCenter(final int x, final int y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Updates the value described in
	 * {@link DrawableNodeData#getRelativeXLocation()}. Has to be called by
	 * every child to achieve the desired effect, before operating on this
	 * object.
	 * 
	 * @param childX
	 *            The x-Coordinate of this objects child.
	 */
	public void setChildX(final int childX) {
		if (this.mLargestChildX == -1 || this.mLargestChildX < childX) {
			this.mLargestChildX = childX;
		}
		if (this.mSmallestChildX == -1 || this.mSmallestChildX > childX) {
			this.mSmallestChildX = childX;
		}
		this.mRelativeXPosition = ((this.mLargestChildX - this.mSmallestChildX) / 2) + this.mSmallestChildX;
	}

	/**
	 * Sets the depth for this object.
	 * 
	 * @param depth
	 *            The new value for the depth.
	 */
	public void setDepth(final int depth) {
		this.mDepth = depth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.treeflood.controller.properties.IHasDescription#setDescription(
	 * java.lang.String)
	 */
	@Override
	public void setDescription(final String description) {
		this.mDescription = description;
	}

	/**
	 * Sets the information for this objects {@link InformationPanel}.
	 * 
	 * @param information
	 *            The informations to be set.
	 */
	public synchronized void setInformation(final List<Information> information) {
		this.mInformationTooltip.setInformation(information);
	}

	/**
	 * Sets the radius for this object.
	 * 
	 * @param radius
	 *            The new value for the radius.
	 */
	public void setRadius(final int radius) {
		this.mRadius = radius;
	}

	/**
	 * Sets the visited status of this node to true.
	 */
	public void setVisited() {
		this.mIsVisited = true;
	}

	/**
	 * Sets the x-Coordinate for this object.
	 * 
	 * @param x
	 *            The new value for the x-Coordinate.
	 */
	public void setX(final int x) {
		this.mX = x;
	}

	/**
	 * Sets the y-Coordinate for this object.
	 * 
	 * @param y
	 *            The new value for the y-Coordinate.
	 */
	public void setY(final int y) {
		this.mY = y;
	}

	/**
	 * Sets {@link DrawableNodeData#mShowTooltip} to <tt>true</tt>, so the
	 * tooltip can be rendered.
	 */
	public void showTooltip() {
		this.mShowTooltip = true;
	}
}
