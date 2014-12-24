package ru.ssau.seabattle.core;

import java.util.ArrayList;


public class Ship {
	ArrayList<Cell> cells;
	private int decksNumber;
	private int x,y;
	private boolean direction;
	private int cellIndex;
	private ShipState state;
	
	public Ship(int x, int y, int decksNumber, boolean direction){
		this.x = x;
		this.y = y;
		this.decksNumber = decksNumber;
		this.direction = direction;
		cells = new ArrayList<Cell>();
		cellIndex = 0;
		state = ShipState.HEALTHY;
	}
	
	public void addCell(Cell cell){
		if(cellIndex < decksNumber){
			cells.add(cell);
			cellIndex++;
		}
	}

	public int getDecksNumber() {
		return decksNumber;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isDirection() {
		return direction;
	}

	public ShipState getState() {
		return state;
	}
	
	public ShootState shipShoot(Cell cell){
		cells.remove(cell);
		System.out.println(cells.size());
		if(cells.size() == 0) {
			state = ShipState.DEAD;
			return ShootState.DEAD;
		}
		else{
			state = ShipState.INJURED;
			return ShootState.INJURED;
		}
	}
}
