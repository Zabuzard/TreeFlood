package de.zabuza.treeflood.demo.gui.model;

import de.zabuza.treeflood.demo.gui.view.util.Window;

/**
 * The information panel holds
 * {@link de.zabuza.treeflood.exploration.localstorage.Information}s for the
 * given node.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class InformationPanel {

	/**
	 * The information this panel holds at the moment.
	 */
	private String information;

	/**
	 * The node on which to operate.
	 */
	private final DrawableNodeData nodeData;

	/**
	 * The window object used for placement related values.
	 */
	private final Window window;

	/**
	 * The placement related coordinates of this panel.
	 */
	private int x, y, height, width;

	/**
	 * Constructs a new information panel, which holds
	 * {@link de.zabuza.treeflood.exploration.localstorage.Information}s for the
	 * given node.
	 * 
	 * @param mNodeData
	 *            The node on which to operate.
	 * @param mWindow
	 *            The window object used for placement related values.
	 */
	public InformationPanel(final DrawableNodeData mNodeData, final Window mWindow) {
		this.nodeData = mNodeData;
		this.information = "";
		this.x = 0;
		this.y = 0;
		this.height = 0;
		this.width = 0;
		this.window = mWindow;
	}

	/**
	 * Gets the height of this panel.
	 * 
	 * @return The height mentioned.
	 */
	public int getHeight() {
		return this.height;

	}

	/**
	 * Gets the information holded by this panel.
	 * 
	 * @return The information mentioned.
	 */
	public String getInformation() {
		return this.information;

	}

	/**
	 * Gets the width of this panel.
	 * 
	 * @return The width mentioned.
	 */
	public int getWidth() {
		return this.width;

	}

	/**
	 * Gets the x-Coordinate of this panel.
	 * 
	 * @return The x-Coordinate mentioned.
	 */
	public int getX() {
		this.x = this.nodeData.getX() + this.nodeData.getRadius();

		if (this.x + this.width > this.window.getTreePanelSize().width) {
			this.x = this.x - this.width - this.nodeData.getRadius() * 2;

		}
		return this.x;

	}

	/**
	 * Gets the y-Coordinate of this panel.
	 * 
	 * @return The y-Coordinate mentioned.
	 */
	public int getY() {
		this.y = this.nodeData.getY() - this.nodeData.getRadius();

		if (this.y < 0) {
			this.y = 0;

		}
		return this.y;

	}

	/**
	 * Sets the information holded by this panel.
	 * 
	 * @param mInformation
	 *            The new information for this panel.
	 */
	public void setInformation(final String mInformation) {
		this.information = mInformation;
		this.adjustWidth();
		this.adjustHeight();

	}

	/**
	 * Adjusts the height of this panel according to the information currently
	 * stored in this panel.
	 * 
	 */
	private void adjustHeight() {
		final String[] splitInformation = this.information.split(System.lineSeparator());

		this.height = 20 * splitInformation.length + 5;

	}

	/**
	 * Adjusts the width of this panel according to the information currently
	 * stored in this panel.
	 */
	private void adjustWidth() {
		final String[] splitInformation = this.information.split(System.lineSeparator());

		int mostChars = 0;
		for (final String line : splitInformation) {
			if (line.length() > mostChars) {
				mostChars = line.length();

			}
		}
		this.width = 8 * mostChars + 5;

	}

}
