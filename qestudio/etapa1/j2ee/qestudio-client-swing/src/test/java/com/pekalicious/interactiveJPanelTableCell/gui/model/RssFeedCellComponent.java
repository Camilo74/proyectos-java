package com.pekalicious.interactiveJPanelTableCell.gui.model;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.pekalicious.interactiveJPanelTableCell.data.RssFeed;

public class RssFeedCellComponent extends JPanel {
	RssFeed feed;
	
	JButton showButton;
	JLabel text;
	
	public RssFeedCellComponent() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		showButton = new JButton("View Articles");
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Reading " + feed.name);
			}
		});
		
		text = new JLabel();
		add(text);
		add(showButton);
	}
	
	public void updateData(RssFeed feed, boolean isSelected, JTable table) {
		this.feed = feed;
		
		text.setText("<html><b>" + feed.name + "</b><br>" + feed.url + "<br>Articles " + feed.articles.length + "</html>");
		if (isSelected) {
			setBackground(table.getSelectionBackground());
		}else{
			setBackground(table.getSelectionForeground());
		}
	}
}
