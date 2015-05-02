package com.pekalicious.interactiveJPanelTableCell.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.pekalicious.interactiveJPanelTableCell.data.RssFeed;

public class RssFeedTableModel extends AbstractTableModel implements TableModel{
	List<RssFeed> feeds;
	
	public RssFeedTableModel(List<RssFeed> feeds) {
		this.feeds = feeds;
	}
	
	public Class<?> getColumnClass(int columnIndex) { return RssFeed.class; }
	public int getColumnCount() { return 1; }
	public String getColumnName(int columnIndex) { return "Feed"; }
	public int getRowCount() { return (feeds == null) ? 0 : feeds.size(); }
	public Object getValueAt(int rowIndex, int columnIndex) { return (feeds == null) ? null : feeds.get(rowIndex); }
	public boolean isCellEditable(int arg0, int arg1) { return true; }
}
