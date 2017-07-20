package de.zabuza.treeflood.demo;

import de.zabuza.treeflood.demo.gui.controller.LocalStorageExplorationGUIController;
import de.zabuza.treeflood.demo.gui.view.MainFrame;
import de.zabuza.treeflood.demo.gui.view.properties.EStyle;
import de.zabuza.treeflood.demo.gui.view.util.StyleManager;
import de.zabuza.treeflood.exploration.localstorage.LocalStorageExploration;

/**
 * GUI tool that demonstrates the execution of the algorithm
 * {@link LocalStorageExploration} step by step.
 *
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public class LocalStorageExplorationGUIDemo {

	/**
	 * GUI tool that demonstrates the execution of the algorithm
	 * {@link LocalStorageExploration} step by step.
	 * 
	 * @param args
	 *            Not supported.
	 */
	@SuppressWarnings("unused")
	public static void main(final String[] args) {
		new LocalStorageExplorationGUIController(
				new MainFrame("Local Storage Exploration", new StyleManager(EStyle.STANDARD)));

	}

	/**
	 * Utility class. No instantiation needed.
	 */
	private LocalStorageExplorationGUIDemo() {

	}

}
