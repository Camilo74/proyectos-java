package ar.com.epidataconsulting.drools;

import java.awt.Robot;
import java.awt.event.InputEvent;

import org.apache.log4j.Logger;

public class MouseUtils {

	private static Logger logger = Logger.getLogger(MouseUtils.class);
	
	public synchronized static Click click(Position ... positions){
		for (Position pos : positions) {
			try {
				Robot bob = new Robot();
				bob.mouseMove(pos.getX(), pos.getY());
				bob.mousePress(InputEvent.BUTTON1_MASK);
				bob.mouseRelease(InputEvent.BUTTON1_MASK);
				System.out.println("Click en fila " + pos.getRow() + " y columna " + pos.getCol());
				Thread.sleep(1000l);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return new Click();
	}
}