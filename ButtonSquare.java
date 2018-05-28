import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class ButtonSquare extends JButton{
	
	int width;
	int height;
	int xCoord;
	int yCoord;
	Color color;
	int value = 0;
	boolean occupied = false;;
	
	public ButtonSquare(int _width, int _height, int _xCoord, int _yCoord, Color _color) {
		width = _width;
		height = _height;
		xCoord = _xCoord;
		yCoord = _yCoord;
		color = _color;
		// Button setup
		initialise();
	}
	
	public void initialise() {
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		setFont(new Font("Arial", Font.BOLD, 30));
		setText("");
		setBackground(Color.GRAY);
	}
	
	public boolean getOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean toPut) {
		occupied = toPut;
	}
	
	public void setVal(int toPut) {
		value = toPut;
	}
	
	public int getVal() {
		return value;
	}
	
	public int getXCoord() {
		return xCoord;
	}
	
	public int getYCoord() {
		return yCoord;
	}

}
