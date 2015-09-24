package com.company.foo.jna;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class JxBrowserImpl implements Sbrowser {
	
	private Browser browser;
	private JFrame frame;
	private static Sbrowser instance;
	
	private JxBrowserImpl(){
        browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        //-------------------------------------------------
        frame = new JFrame("market");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setVisible(true);
	}
	
	public static Sbrowser getInstance(){
		if(instance == null){
			instance = new JxBrowserImpl();
		}
		return instance;
	}
	
	@Override
	public void open(String url) {
		browser.loadURL(url);
		frame.setTitle(browser.getTitle());
		frame.setSize(800,600);//791,height=525
		frame.setVisible(true);
	}
	
    public static void main(String[] args) {
    	new JxBrowserImpl().open("http://google.com.ar");
    }

}