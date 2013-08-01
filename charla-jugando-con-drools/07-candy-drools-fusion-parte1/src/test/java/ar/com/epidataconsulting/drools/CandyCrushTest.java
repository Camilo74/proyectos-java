package ar.com.epidataconsulting.drools;

import javax.swing.SwingUtilities;

public class CandyCrushTest {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CandyCrush();
			}
		});
	}
	
}
