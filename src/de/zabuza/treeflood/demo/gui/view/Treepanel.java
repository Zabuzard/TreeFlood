package de.zabuza.treeflood.demo.gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
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
import de.zabuza.treeflood.demo.gui.view.util.Window;
import de.zabuza.treeflood.tree.ITreeNode;

/**
 * A panel which handles the drawing of the tree
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class Treepanel extends JPanel implements MouseMotionListener {

	/**
	 * The serial version UID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The rendering hints used for this trees graphics object.
	 */
	private final RenderingHints hints;

	/**
	 * The tree on which to operate.
	 */
	private CoordinateTree tree;

	/**
	 * The window used for position related information.
	 */
	private final Window window;

	/**
	 * Constructs a new Treepanel which handles drawing of the current tree.
	 * 
	 * @param mWindow
	 *            The window used for position related information.
	 */
	public Treepanel(final Window mWindow) {
		this.window = mWindow;
		this.hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		this.hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		this.hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		this.addMouseMotionListener(this);

		this.setPreferredSize(mWindow.getTreePanelSize());
		this.setBorder(LineBorder.createBlackLineBorder());
		this.setBackground(Color.WHITE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.
	 * MouseEvent)
	 */
	@Override
	public void mouseDragged(final MouseEvent e) {
		// No implementation needed.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(final MouseEvent e) {
		if (this.tree == null) {
			return;

		}
		this.tree.setMousePosition(e.getPoint());

	}

	/**
	 * Sets the current tree to the tree given.
	 * 
	 * @param mTree
	 *            The new tree to paint.
	 */
	public void setTree(final CoordinateTree mTree) {
		this.tree = mTree;
		this.tree.alignComponents(this.window);
		this.repaint();

	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(Edge.DEFAULT_WIDTH));

		g2d.setRenderingHints(this.hints);

		if (this.tree == null) {
			return;

		}

		for (final Edge edge : this.tree.getEdges()) {
			g2d.setColor(edge.getColor());

			g2d.drawLine(edge.getStartX(), edge.getStartY(), edge.getEndX(), edge.getEndY());

			g2d.drawString(edge.getDescription(), edge.getDescriptionX(), edge.getDescriptionY());

		}

		for (final ITreeNode node : this.tree.getNodes()) {

			final DrawableNodeData nodeData = this.tree.getNodeMapping().get(node);

			g2d.setColor(nodeData.getColor());
			g2d.fillOval(nodeData.getX() - nodeData.getRadius(), nodeData.getY() - nodeData.getRadius(),
					nodeData.getRadius() * 2, nodeData.getRadius() * 2);

			g2d.setColor(Color.BLACK);
			g2d.setFont(Window.DESCRIPTION_FONT);

			if (!nodeData.getDescription().equals("0")) {
				g2d.drawString(nodeData.getDescription(), nodeData.getDescriptionX(), nodeData.getDescriptionY());

			}
		}

		for (final ITreeNode node : this.tree.getNodes()) {
			final DrawableNodeData nodeData = this.tree.getNodeMapping().get(node);
			g2d.setStroke(new BasicStroke(2));
			g2d.setFont(Window.TOOLTIP_FONT);

			if (nodeData.isTooltipOn()) {

				final InformationPanel panel = nodeData.getInformationPanel();
				g2d.setColor(Window.TOOLTIP_FILL_COLOR);
				g2d.fillRect(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
				g2d.setColor(Color.BLACK);
				g2d.drawRect(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());

				int i = 20;
				for (final String line : panel.getInformation().split(System.lineSeparator())) {
					g2d.drawString(line, panel.getX() + 3, panel.getY() + i);
					i += 20;
				}
			}
		}
	}
}
