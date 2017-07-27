package de.zabuza.treeflood.demo.gui.model;

import de.zabuza.treeflood.demo.gui.model.properties.IHasDescription;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * An edge is defined by having its own {@link ITreeNode} = source and
 * {@link ITreeNode} = destination. Gets its positional values from its source
 * and destination nodes.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Edge implements IHasDescription {

	/**
	 * The default stroke width for this edge, when drawn onto GUI.
	 */
	public static final int DEFAULT_WIDTH = 5;

	/**
	 * The description of this edge, e.g. the port number.
	 */
	private String mDescription;

	/**
	 * The additional data for the destination-node.
	 */
	private final DrawableNodeData mDestination;

	/**
	 * determines whether this edge got visited.
	 */
	private boolean mIsVisited;

	/**
	 * The additional data for the source-node.
	 */
	private final DrawableNodeData mSource;

	/**
	 * Constructs a new Edge with the given source and destination data from
	 * their respective nodes.
	 * 
	 * @param source
	 *            The additional data for the source node.
	 * @param destination
	 *            The additional data for the destination node.
	 */
	public Edge(final DrawableNodeData source, final DrawableNodeData destination) {
		this.mSource = source;
		this.mDestination = destination;
		this.mDescription = "";
		this.mIsVisited = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.model.properties.IHasDescription#
	 * getDescription()
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
		int valueToAdd = this.getStartX();

		if (this.getStartX() > this.getEndX()) {
			valueToAdd = this.getEndX() - 20;
		}

		if (this.getSlope() == Double.MAX_VALUE) {
			valueToAdd += 10;
		} else {
			if (this.getSlope() > 0) {
				valueToAdd += (int) (5 * this.getSlope());
			}
		}

		return valueToAdd + (Math.abs(this.getStartX() - this.getEndX()) / 2);
	}

	/**
	 * Gets the y-Coordinate of the Description (the y-location where the
	 * description of this object should be rendered).
	 * 
	 * @return The y-Coordinate mentioned.
	 */
	public int getDescriptionY() {
		int valueToAdd = this.getStartY();

		if (this.getStartY() > this.getEndY()) {
			valueToAdd = this.getEndY();
		}

		int adjustmentY = 0;
		if (!(this.getSlope() == Double.MAX_VALUE) && this.getSlope() < 1 && this.getSlope() > -1) {
			adjustmentY = (int) (9 * (1 - Math.abs(this.getSlope())));
		}

		return valueToAdd + (Math.abs(this.getStartY() - this.getEndY()) / 2) - adjustmentY;
	}

	/**
	 * Gets the x-Coordinate of the destination node.
	 * 
	 * @return The x-Coordinate mentioned.
	 */
	public int getEndX() {
		return this.mDestination.getX();
	}

	/**
	 * Gets the y-Coordinate of the destination node.
	 * 
	 * @return The y-Coordinate mentioned.
	 */
	public int getEndY() {
		return this.mDestination.getY();
	}

	/**
	 * Gets the slope of this edge. Returns {@link Double#MAX_VALUE} if the
	 * slope is infinite.
	 * 
	 * @return The slope of this edge.
	 */
	public double getSlope() {
		final int x_1 = this.getStartX();
		final int y_1 = this.getStartY();

		final int x_2 = this.getEndX();
		final int y_2 = this.getEndY();

		// The slope is per definition infinite, returning special value;
		if (x_1 - x_2 == 0) {
			return Double.MAX_VALUE;
		}

		return (float) (y_2 - y_1) / (float) (x_2 - x_1);
	}

	/**
	 * Gets the x-Coordinate of the source node.
	 * 
	 * @return The x-Coordinate mentioned.
	 */
	public int getStartX() {
		return this.mSource.getX();
	}

	/**
	 * Gets the y-Coordinate of the source node.
	 * 
	 * @return The y-Coordinate mentioned.
	 */
	public int getStartY() {
		return this.mSource.getY();
	}

	/**
	 * Gets the current visited status of this edge.
	 * 
	 * @return <tt>True</tt> if the edge was visited, <tt>false</tt> otherwise.
	 */
	public boolean getVisitedStatus() {
		return this.mIsVisited;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.model.properties.IHasDescription#
	 * setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(final String description) {
		this.mDescription = description;
	}

	/**
	 * Sets the visited status of this edge to true.
	 */
	public void setVisited() {
		this.mIsVisited = true;
	}
}