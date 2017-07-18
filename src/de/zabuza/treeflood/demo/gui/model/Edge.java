package de.zabuza.treeflood.demo.gui.model;

import java.awt.Color;

import de.zabuza.treeflood.demo.gui.model.properties.IColorable;
import de.zabuza.treeflood.demo.gui.model.properties.IHasDescription;
import de.zabuza.treeflood.demo.gui.view.util.Window;

/**
 * An edge is defined by having its own {@link ITreeNode} = source and
 * {@link ITreeNode} = destination. Gets its positional values from its source
 * and destination nodes.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Edge implements IColorable, IHasDescription {

	/**
	 * The default stroke width for this edge, when drawn onto GUI.
	 */
	public static final int DEFAULT_WIDTH = 5;

	/**
	 * The current color for this edge.
	 */
	private Color color;

	/**
	 * The description of this edge, e.g. the port number.
	 */
	private String description;

	/**
	 * The additional data for the destination-node.
	 */
	private final DrawableNodeData destination;

	/**
	 * The additional data for the source-node.
	 */
	private final DrawableNodeData source;

	/**
	 * Constructs a new Edge with the given source and destination data from
	 * their respective nodes.
	 * 
	 * @param mSource
	 *            The additional data for the source node.
	 * @param mDestination
	 *            The additional data for the destination node.
	 */
	public Edge(final DrawableNodeData mSource, final DrawableNodeData mDestination) {
		this.source = mSource;
		this.destination = mDestination;
		this.color = Window.UNVISITED_EDGE_COLOR;
		this.description = "";

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.model.properties.IHasDescription#
	 * getDescription()
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
		int valueToAdd = this.getStartX() + 10;

		if (this.getStartX() > this.getEndX()) {
			valueToAdd = this.getEndX() - 10;

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

		return valueToAdd + (Math.abs(this.getStartY() - this.getEndY()) / 2);

	}

	/**
	 * Gets the x-Coordinate of the destination node.
	 * 
	 * @return The x-Coordinate mentioned.
	 */
	public int getEndX() {
		return this.destination.getX();

	}

	/**
	 * Gets the y-Coordinate of the destination node.
	 * 
	 * @return The y-Coordinate mentioned.
	 */
	public int getEndY() {
		return this.destination.getY();

	}

	/**
	 * Gets the x-Coordinate of the source node.
	 * 
	 * @return The x-Coordinate mentioned.
	 */
	public int getStartX() {
		return this.source.getX();

	}

	/**
	 * Gets the y-Coordinate of the source node.
	 * 
	 * @return the y-Coordinate mentioned.
	 */
	public int getStartY() {
		return this.source.getY();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.model.properties.IHasDescription#
	 * setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(final String mDescription) {
		this.description = mDescription;

	}

}
