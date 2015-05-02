package com.pekalicious.interactiveJPanelTableCell.gui.model;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.pekalicious.interactiveJPanelTableCell.data.RssFeed;

public class RssFeedCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	JPanel panel;
	
	JLabel text;
	JButton showButton;
	
	RssFeed feed;
	
	public RssFeedCell() {
		text = new JLabel();
		
		showButton = new JButton("View Articles");
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Reading " + feed.name);
			}
		});
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(text);
		panel.add(showButton);
	}
	
	private void updateData(RssFeed feed, boolean isSelected, JTable table) {
		this.feed = feed;
		
		text.setText("<html><b>" + feed.name + "</b><br>" + feed.url + "<br>Articles " + feed.articles.length + "</html>");
		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		}else{
			panel.setBackground(table.getSelectionForeground());
		}		
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		RssFeed feed = (RssFeed)value;
		updateData(feed, true, table);
		return panel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		RssFeed feed = (RssFeed)value;

		updateData(feed, isSelected, table);
		return panel;
	}
}
