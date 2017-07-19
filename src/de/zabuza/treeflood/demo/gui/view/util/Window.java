package de.zabuza.treeflood.demo.gui.view.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

/**
 * The window object holds the positional values of the major components of the
 * GUI used. Also provides static access to colors used throughout the view.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Window {

	/**
	 * The font used by the ports displayed at edges.
	 */
	public static final Font EDGE_PORT_FONT = new Font("", Font.BOLD, 20);

	/**
	 * The color of the buttons used throughout this view.
	 */
	public static final Color BUTTON_COLOR = new Color(255, 255, 255);

	/**
	 * The font for the descriptions.
	 */
	public static final Font DESCRIPTION_FONT = new Font("", Font.BOLD, 20);

	/**
	 * The font used by text areas throughout this view.
	 */
	public static final Font TEXT_AREA_FONT = new Font("", Font.BOLD, 20);

	/**
	 * The font used by buttons throughout this view.
	 */
	public static final Font TEXT_BUTTON_FONT = new Font("", Font.BOLD, 14);

	/**
	 * The font used by text panes throughout this view.
	 */
	public static final Font TEXT_PANE_FONT = new Font("", Font.PLAIN, 18);

	/**
	 * The color of the rectangle surrounding the tooltips.
	 */
	public static final Color TOOLTIP_FILL_COLOR = new Color(230, 230, 230, 200);

	/**
	 * The font used for the tooltips.
	 */
	public static final Font TOOLTIP_FONT = new Font("", Font.PLAIN, 18);

	/**
	 * The color of an edge which has not yet been visited by robot.
	 */
	public static final Color UNVISITED_EDGE_COLOR = new Color(201, 201, 201);

	/**
	 * The color of a node which has not yet been visited by a robot.
	 */
	public static final Color UNVISITED_NODE_COLOR = new Color(201, 201, 201);

	/**
	 * The color of an edge which has been visited by a robot.
	 */
	public static final Color VISITED_EDGE_COLOR = new Color(255, 184, 136);

	/**
	 * The color of a node which has been visited by a robot.
	 */
	public static final Color VISITED_NODE_COLOR = new Color(245, 99, 99);

	/**
	 * This is used to maintain a stable frame size no matter on which device
	 * this application is run.
	 */
	private static final Dimension SCREEN_SIZE = new Dimension(1920, 1080);

	/**
	 * The Location of the frame (the top left point of the frame where it
	 * should be placed on the screen).
	 */
	private final Point frameLocation;

	/**
	 * The Dimensions of the frame which holds every view related element.
	 */
	private final Dimension frameSize;

	/**
	 * The Dimensions of the
	 * {@link de.zabuza.treeflood.demo.gui.view.Optionpanel}.
	 */
	private final Dimension optionPanelSize;

	/**
	 * The Dimensions of the {@link de.zabuza.treeflood.demo.gui.view.Treepanel}
	 * .
	 */
	private final Dimension treePanelSize;

	/**
	 * Initializes the Window object and calculates positional values for the
	 * view.
	 */
	public Window() {
		this.frameSize = new Dimension((int) (this.getScreenWidth() * 0.75f), (int) (this.getScreenHeight() * 0.75f));
		this.treePanelSize = new Dimension((int) (this.getScreenWidth() * (0.75f * (4f / 5))),
				(int) (this.getScreenHeight() * 0.75));
		this.optionPanelSize = new Dimension((int) (this.getScreenWidth() * (0.75f * (1f / 5))),
				(int) (this.getScreenHeight() * 0.75));

		this.frameLocation = new Point(this.getScreenWidth() / 2 - this.getFrameSize().width / 2,
				this.getScreenHeight() / 2 - this.getFrameSize().height / 2);

	}

	/**
	 * Gets the location of the frame (the top left point of the frame where it
	 * should be placed on the screen).
	 * 
	 * @return The point mentioned.
	 */
	public Point getFrameLocation() {
		return this.frameLocation;

	}

	/**
	 * Gets the dimensions of the frame used for this application.
	 * 
	 * @return The dimensions mentioned.
	 */
	public Dimension getFrameSize() {
		return this.frameSize;

	}

	/**
	 * Gets the dimensions for the option panel.
	 * 
	 * @return The dimensions mentioned.
	 */
	public Dimension getOptionPanelSize() {
		return this.optionPanelSize;

	}

	/**
	 * Gets the height of the screen.
	 * 
	 * @return The height mentioned.
	 */
	@SuppressWarnings("static-method")
	public int getScreenHeight() {
		return SCREEN_SIZE.height;

	}

	/**
	 * Gets the dimensions of the screen (obsolete naming, since the screen size
	 * here is a fixed value).
	 * 
	 * @return The dimensions mentioned.
	 */
	@SuppressWarnings("static-method")
	public Dimension getScreenSize() {
		return SCREEN_SIZE;

	}

	/**
	 * Gets the width of the screen.
	 * 
	 * @return The width mentioned.
	 */
	@SuppressWarnings("static-method")
	public int getScreenWidth() {
		return SCREEN_SIZE.width;

	}

	/**
	 * Gets the dimensions for the tree panel.
	 * 
	 * @return The dimensions mentioned.
	 */
	public Dimension getTreePanelSize() {
		return this.treePanelSize;

	}
}
