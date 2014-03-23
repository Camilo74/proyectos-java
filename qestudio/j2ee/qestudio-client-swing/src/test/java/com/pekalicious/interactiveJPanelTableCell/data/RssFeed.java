package com.pekalicious.interactiveJPanelTableCell.data;


public class RssFeed {
	public String name;
	public String url;
	public Article[] articles;
	
	public RssFeed(String name, String url, Article[] articles) {
		this.name = name;
		this.url = url;
		this.articles = articles;
	}
}
