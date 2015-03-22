package ru.ssau.seabattle.core;

import java.io.Serializable;

public class Coordinate implements Serializable {
	
	private static final long serialVersionUID = -2648628792067059361L;
	
	private int _x;
	private int _y;
	
	public Coordinate(int x, int y){
		_x = x;
		_y = y;
	}
	
	public int getX(){
		return _x;
	}	
	public int getY(){
		return _y;
	}
}
