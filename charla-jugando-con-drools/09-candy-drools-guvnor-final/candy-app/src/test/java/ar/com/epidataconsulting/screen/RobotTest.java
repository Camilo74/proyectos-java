package ar.com.epidataconsulting.screen;

import java.awt.Robot;
import java.awt.event.InputEvent;

import org.junit.Test;

public class RobotTest {
	
	private static int X = 400;
	private static int Y = 400;

	@Test
	public void rgbTest(){
		try {
			Robot bob = new Robot();
			int rgb = bob.getPixelColor(X, Y).getRGB();
			System.out.println("get rgb color:" + rgb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void clickTest(){
		try {
			Robot bob = new Robot();
			bob.mouseMove(X, Y);
			bob.mousePress(InputEvent.BUTTON1_MASK);
			bob.mouseRelease(InputEvent.BUTTON1_MASK);
			System.out.println("Click en fila " + X + " y columna " + Y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}