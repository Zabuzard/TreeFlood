package de.zabuza.treeflood.demo.gui.model;

import java.util.ArrayList;
import java.util.List;

import de.zabuza.treeflood.demo.gui.view.util.Window;
import de.zabuza.treeflood.exploration.localstorage.Information;

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
	private List<Information> informations;

	/**
	 * A string representation for every information this panel currently holds.
	 */
	private List<String> informationsAsString;

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
		this.x = 0;
		this.y = 0;
		this.height = 0;
		this.width = 0;
		this.window = mWindow;
		this.informations = new ArrayList<>();
		this.informationsAsString = new ArrayList<>();
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
	public List<String> getInformation() {
		return this.informationsAsString;

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
	public void setInformation(final List<Information> mInformation) {
		this.informations = mInformation;
		this.adjustWidth();
		this.adjustHeight();

	}

	/**
	 * Adjusts the height of this panel according to the information currently
	 * stored in this panel.
	 * 
	 */
	private void adjustHeight() {
		this.height = 20 * this.informations.size() + 5;

	}

	/**
	 * Adjusts the width of this panel according to the information currently
	 * stored in this panel.
	 */
	private void adjustWidth() {

		int mostChars = 0;
		for (final Information line : this.informations) {
			final String informationString;

			if (line.wasEnteredFromParent()) {
				informationString = "#" + line.getStep() + ", [" + line.getRobotId() + "], ↑"
						+ line.getPort();
			} else {
				informationString = "#" + line.getStep() + ", [" + line.getRobotId() + "],	 ↓"
						+ line.getPort();
			}

			if (informationString.length() > mostChars) {
				mostChars = informationString.length();

			}

			if (this.informationsAsString.contains(informationString)) {
				continue;

			}
			this.informationsAsString.add(informationString);

		}
		this.width = 8 * mostChars;

	}

}
