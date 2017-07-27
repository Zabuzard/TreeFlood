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
public final class InformationPanel {

	/**
	 * The height-placement related coordinates of this panel.
	 */
	private int mHeight;

	/**
	 * The information this panel holds at the moment.
	 */
	private List<Information> mInformations;

	/**
	 * A string representation for every information this panel currently holds.
	 */
	private final List<String> mInformationsAsString;

	/**
	 * The node on which to operate.
	 */
	private final DrawableNodeData mNodeData;

	/**
	 * The width-placement related coordinates of this panel.
	 */
	private int mWidth;
	/**
	 * The window object used for placement related values.
	 */
	private final Window mWindow;
	/**
	 * The x-placement related coordinates of this panel.
	 */
	private int mX;
	/**
	 * The y-placement related coordinates of this panel.
	 */
	private int mY;

	/**
	 * Constructs a new information panel, which holds
	 * {@link de.zabuza.treeflood.exploration.localstorage.Information}s for the
	 * given node.
	 * 
	 * @param nodeData
	 *            The node on which to operate.
	 * @param window
	 *            The window object used for placement related values.
	 */
	public InformationPanel(final DrawableNodeData nodeData, final Window window) {
		this.mNodeData = nodeData;
		this.mX = 0;
		this.mY = 0;
		this.mHeight = 0;
		this.mWidth = 0;
		this.mWindow = window;
		this.mInformations = new ArrayList<>();
		this.mInformationsAsString = new ArrayList<>();
	}

	/**
	 * Gets the height of this panel.
	 * 
	 * @return The height mentioned.
	 */
	public int getHeight() {
		return this.mHeight;
	}

	/**
	 * Gets the information hold by this panel.
	 * 
	 * @return The information mentioned.
	 */
	public List<String> getInformation() {
		return this.mInformationsAsString;
	}

	/**
	 * Gets the width of this panel.
	 * 
	 * @return The width mentioned.
	 */
	public int getWidth() {
		return this.mWidth;
	}

	/**
	 * Gets the x-Coordinate of this panel.
	 * 
	 * @return The x-Coordinate mentioned.
	 */
	public int getX() {
		this.mX = this.mNodeData.getX() + this.mNodeData.getRadius();

		if (this.mX + this.mWidth > this.mWindow.getTreePanelSize().width) {
			this.mX = this.mX - this.mWidth - this.mNodeData.getRadius() * 2;
		}

		return this.mX;
	}

	/**
	 * Gets the y-Coordinate of this panel.
	 * 
	 * @return The y-Coordinate mentioned.
	 */
	public int getY() {
		this.mY = this.mNodeData.getY() - this.mNodeData.getRadius();

		if (this.mY < 0) {
			this.mY = 0;
		}

		return this.mY;
	}

	/**
	 * Sets the information hold by this panel. Also calls
	 * {@link InformationPanel#adjustHeight()} and
	 * {@link InformationPanel#adjustWidth()}.
	 * 
	 * @param information
	 *            The new information for this panel.
	 */
	public synchronized void setInformation(final List<Information> information) {
		this.mInformations = information;
		adjustWidth();
		adjustHeight();
	}

	/**
	 * Adjusts the height of this panel according to the information currently
	 * stored in this panel.
	 * 
	 */
	private void adjustHeight() {
		this.mHeight = 20 * this.mInformations.size() + 5;
	}

	/**
	 * Adjusts the width of this panel according to the information currently
	 * stored in this panel.
	 */
	private void adjustWidth() {
		int highestStep = 0;
		for (final Information line : this.mInformations) {
			if (line.getStep() > highestStep) {
				highestStep = line.getStep();
			}
		}

		int mostChars = 0;
		for (final Information line : this.mInformations) {
			String informationString = "#";

			final int spacesToAdd = ("" + highestStep).length() - (line.getStep() + "").length();

			for (int i = 0; i < spacesToAdd; i++) {
				informationString += "  ";
			}

			if (line.wasEnteredFromParent()) {
				informationString += line.getStep() + ", [" + line.getRobotId() + "], \u2193 " + line.getPort();
			} else {
				informationString += line.getStep() + ", [" + line.getRobotId() + "], \u2191 " + line.getPort();
			}

			if (informationString.length() > mostChars) {
				mostChars = informationString.length();
			}

			if (this.mInformationsAsString.contains(informationString)) {
				continue;
			}

			this.mInformationsAsString.add(informationString);
		}

		this.mWidth = mostChars * 8 + 5;
	}
}
