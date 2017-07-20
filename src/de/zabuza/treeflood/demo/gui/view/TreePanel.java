package de.zabuza.treeflood.demo.gui.view;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import de.zabuza.treeflood.demo.gui.model.CoordinateTree;
import de.zabuza.treeflood.demo.gui.model.DrawableNodeData;
import de.zabuza.treeflood.demo.gui.model.Edge;
import de.zabuza.treeflood.demo.gui.model.InformationPanel;
import de.zabuza.treeflood.demo.gui.view.properties.IReColorable;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;
import de.zabuza.treeflood.demo.gui.view.util.Window;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * A panel which handles the drawing of the tree.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class TreePanel extends JPanel implements MouseMotionListener, IReColorable {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The rendering hints used for this trees graphics object.
	 */
	private final RenderingHints mHints;

	/**
	 * The style manager for this panel used to get current colors.s
	 */
	private final StyleManager mManager;

	/**
	 * The tree on which to operate.
	 */
	private CoordinateTree mTree;

	/**
	 * The window used for position related information.
	 */
	private final Window mWindow;

	/**
	 * Constructs a new tree panel which handles drawing of the current tree.
	 * 
	 * @param window
	 *            The window used for position related information.
	 * 
	 * @param manager
	 *            The style manager used to handle colors.
	 */
	public TreePanel(final Window window, final StyleManager manager) {
		this.mWindow = window;
		this.mHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.mHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		this.mHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		this.mHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		this.mManager = manager;
		this.addMouseMotionListener(this);

		this.setPreferredSize(window.getTreePanelSize());
		this.setBorder(LineBorder.createBlackLineBorder());
		this.setBackground(manager.getTreepanelColor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.
	 * MouseEvent)
	 */
	@Override
	public void mouseDragged(final MouseEvent event) {
		// No implementation needed.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(final MouseEvent event) {
		if (this.mTree == null) {
			return;
		}
		this.mTree.setMousePosition(event.getPoint());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.treeflood.demo.gui.view.properties.IRecolorable#reColor()
	 */
	@Override
	public void reColor() {
		if (this.mManager == null) {
			return;
		}
		this.setBackground(this.mManager.getTreepanelColor());
	}

	@Override
	public void repaint() {
		this.reColor();
		super.repaint();
	}

	/**
	 * Sets the current tree to the tree given.
	 * 
	 * @param tree
	 *            The new tree to paint.
	 */
	public void setTree(final CoordinateTree tree) {
		this.mTree = tree;
		this.mTree.alignComponents(this.mWindow);
		this.repaint();
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(8));
		g2d.setColor(this.mManager.getSeparatorColor());
		g2d.drawLine(this.getWidth(), 0, this.getWidth(), this.getHeight());

		g2d.setStroke(new BasicStroke(Edge.DEFAULT_WIDTH));

		g2d.setRenderingHints(this.mHints);

		if (this.mTree == null) {
			return;
		}

		for (final Edge edge : this.mTree.getEdges()) {
			if (edge.getVisitedStatus()) {
				g2d.setColor(this.mManager.getVisitedEdgeColor());
			} else {
				g2d.setColor(this.mManager.getUnvisitedEdgeColor());
			}

			g2d.drawLine(edge.getStartX(), edge.getStartY(), edge.getEndX(), edge.getEndY());

			g2d.setFont(Window.EDGE_PORT_FONT);
			g2d.drawString(edge.getDescription(), edge.getDescriptionX(), edge.getDescriptionY());
		}

		for (final ITreeNode node : this.mTree.getNodes()) {
			final DrawableNodeData nodeData = this.mTree.getNodeMapping().get(node);

			if (nodeData.getVisitedStatus()) {
				g2d.setColor(this.mManager.getVisitedNodeColor());
			} else {
				g2d.setColor(this.mManager.getUnvisitedNodeColor());
			}
			g2d.fillOval(nodeData.getX() - nodeData.getRadius(), nodeData.getY() - nodeData.getRadius(),
					nodeData.getRadius() * 2, nodeData.getRadius() * 2);

			if (nodeData.getVisitedStatus()) {
				g2d.setStroke(new BasicStroke(3));
				g2d.setColor(this.mManager.getFringeColor());
				g2d.drawOval(nodeData.getX() - nodeData.getRadius(), nodeData.getY() - nodeData.getRadius(),
						nodeData.getRadius() * 2, nodeData.getRadius() * 2);
			}

			g2d.setColor(this.mManager.getDefaultFontColor());
			g2d.setFont(Window.DESCRIPTION_FONT);

			if (!nodeData.getDescription().equals("0")) {
				g2d.drawString(nodeData.getDescription(), nodeData.getDescriptionX(), nodeData.getDescriptionY());
			}
		}

		for (final ITreeNode node : this.mTree.getNodes()) {
			final DrawableNodeData nodeData = this.mTree.getNodeMapping().get(node);
			g2d.setStroke(new BasicStroke(2));
			g2d.setFont(Window.TOOLTIP_FONT);

			if (nodeData.isTooltipOn()) {
				final InformationPanel panel = nodeData.getInformationPanel();
				g2d.setColor(this.mManager.getToolTipFillColor());
				g2d.fillRect(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
				g2d.setColor(this.mManager.getFringeColor());
				g2d.drawRect(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());

				g2d.setColor(this.mManager.getDefaultFontColor());

				int i = 20;
				for (final String line : panel.getInformation()) {
					g2d.drawString(line, panel.getX() + 3, panel.getY() + i);
					i += 20;
				}
			}
		}
	}
}
