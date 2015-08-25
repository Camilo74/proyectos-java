package org.jnativehook.example;

/*
 * Browser example snippet: bring up a browser
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SwtBrowser{
	public static void main(String[] args) {
		new SwtBrowser().start();
	}

	public void start() {

		Display display = new Display();
		
		
//		Shell parentShell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
		
		final Shell shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setLayout(new FillLayout());
		Browser browser = new Browser(shell, SWT.NONE);
		browser.addTitleListener(new TitleListener() {
			public void changed(TitleEvent event) {
				shell.setText(event.title);
			}
		});
		browser.setBounds(200, 200, 600, 400);
		// shell.pack();
		shell.open();
		browser.setUrl("http://localhost:8080/index");
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
