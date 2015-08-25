package org.jnativehook.example;

/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

/*
 * Browser snippet: bring up a browser (pop-up blocker)
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet173 implements Sbrowser{

	private Display display;
	private Shell shell;
	private Browser browser;
	
	private static Sbrowser instance;
	
	private Snippet173(){
		display = Display.getDefault();
		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		browser = new Browser(shell, SWT.ON_TOP);
	}
	
	public static Sbrowser getInstance(){
		if(instance == null){
			instance = new Snippet173();
		}
		return instance;
	}

	@Override
	public void open(final String url) {
	   // do something on the UI thread asynchronously
	   Display.getDefault().syncExec(new Runnable() {
	      public void run() {
	    	browser.setUrl(url);
	    	shell.open();
	    	while (display.getShells().length > 0) {
	    		if (!display.readAndDispatch()) {
	    			display.sleep();
	    		}
	    	}
	    	display.dispose();
	      }
	   });
		
	}
	
	public static void main(String args[]) {
		Snippet173.getInstance().open("http://ipchicken.com");
	}
}