package ar.com.epidataconsulting.drools.model;

import java.awt.Color;
import java.awt.Robot;
import java.util.Date;

import ar.com.epidataconsulting.drools.CandyCrush;

public class Position {
	
	private int x;
	private int y;
	
	private int row;
	private int col;
	
	private Date timestamp;

	private static int RANGO = 1;
	private static int ERROR = 5;
	
	public Position(Position position){
		this.x = position.x;
		this.y = position.y;
		this.row = position.row;
		this.col = position.col;
		this.setTimestamp(new Date());
	}
	
	public Position get(String direction){
		try {
			
			int cRow = 0;
			int cCol = 0;
			
			if("right".equalsIgnoreCase(direction)){
				cCol++;
			}else if("left".equalsIgnoreCase(direction)){
				cCol--;
			}else if("up".equalsIgnoreCase(direction)){
				cRow++;
			}else if("down".equalsIgnoreCase(direction)){
				cRow--;
			}
			
			return CandyCrush.matriz[row + cRow][col + cCol];
		} catch (Exception e) {
			return null;
		}
	}
	
	public Position(){
		super();
	}
	
	public Position(int x, int y, int row, int col){
		this.x = x;
		this.y = y;
		this.row = row;
		this.col = col;
	}
	
	public Color getColor() {
		try {
			Robot bob = new Robot();
			int rgb = 0;
			int cdor = 0;
			for (int xx = -RANGO; xx < RANGO; xx++) {
				for (int yy = -RANGO; yy < RANGO; yy++) {
					rgb+= bob.getPixelColor(x + xx, y + yy).getRGB();
					cdor++;
				}
			}
			return new Color(rgb / cdor); //calculamos el color con el promedio de la densidad de colores
		} catch (Exception e) {
			return null;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	@Override
	public String toString() {
		return new String("Row: " + row + ", Col: " + col + ", Color: " + getColor().toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Position){
			Position pos = (Position)obj;
			return isPosible(pos.getColor().getRed(), this.getColor().getRed()) && 
					isPosible(pos.getColor().getGreen(), this.getColor().getGreen()) &&
					isPosible(pos.getColor().getBlue(), this.getColor().getBlue());
		}else{
			return false;
		}
	}

	private boolean isPosible(int color1, int color2) {
		int value = color1 > color2 ? color1 - color2 : color2 - color1;
		return value < ERROR;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}