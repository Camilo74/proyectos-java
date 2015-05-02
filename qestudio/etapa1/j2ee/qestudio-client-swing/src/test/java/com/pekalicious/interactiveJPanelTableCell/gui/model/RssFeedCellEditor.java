package com.pekalicious.interactiveJPanelTableCell.gui.model;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import com.pekalicious.interactiveJPanelTableCell.data.RssFeed;

public class RssFeedCellEditor extends AbstractCellEditor implements TableCellEditor {
	RssFeedCellComponent feedComponent;

	public RssFeedCellEditor() {
		feedComponent = new RssFeedCellComponent();
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		RssFeed feed = (RssFeed)value;
		feedComponent.updateData(feed, true, table);
		return feedComponent;
	}

	public Object getCellEditorValue() {
		return null;
	}

}
