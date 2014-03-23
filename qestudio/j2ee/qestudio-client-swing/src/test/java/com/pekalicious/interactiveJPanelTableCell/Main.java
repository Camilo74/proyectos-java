package com.pekalicious.interactiveJPanelTableCell;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.pekalicious.interactiveJPanelTableCell.gui.JInteractiveTableExample;

public class Main {
	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e) {}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JInteractiveTableExample().setVisible(true);
			}
		});
	}
}
