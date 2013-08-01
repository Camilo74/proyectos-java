package ar.com.epidataconsulting.drools;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseUtils {

	public static void click(Position ... positions){
		for (Position pos : positions) {
			try {
				Robot bob = new Robot();
				bob.mouseMove(pos.getX(), pos.getY());
				bob.mousePress(InputEvent.BUTTON1_MASK);
				bob.mouseRelease(InputEvent.BUTTON1_MASK);
				System.out.println("Click en fila " + pos.getRow() + " y columna " + pos.getCol());
				Thread.sleep(1000l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}