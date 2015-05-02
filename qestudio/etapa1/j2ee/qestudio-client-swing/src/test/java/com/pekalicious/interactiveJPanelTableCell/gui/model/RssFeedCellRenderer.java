package com.pekalicious.interactiveJPanelTableCell.gui.model;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.pekalicious.interactiveJPanelTableCell.data.RssFeed;

public class RssFeedCellRenderer implements TableCellRenderer{
	RssFeedCellComponent feedComponent;
	
	public RssFeedCellRenderer() {
		feedComponent = new RssFeedCellComponent();
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		RssFeed feed = (RssFeed)value;

//		JButton showButton = new JButton("View Articles");
//		showButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				JOptionPane.showMessageDialog(null, "HA-HA!");
//			}
//		});
//		
//		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		panel.add(new JLabel("<html><b>" + feed.name + "</b><br>" + feed.url + "<br>Articles " + feed.articles.length + "</html>"));
//		panel.add(showButton);
//		
//		if (isSelected) {
//			panel.setBackground(table.getSelectionBackground());
//		}else{
//			panel.setBackground(table.getSelectionForeground());
//		}
//		return panel;
		
		feedComponent.updateData(feed, isSelected, table);
		return feedComponent;
	}
}
