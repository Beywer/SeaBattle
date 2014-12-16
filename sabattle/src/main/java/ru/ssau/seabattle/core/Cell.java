package ru.ssau.seabattle.core;


public class Cell {
	
	private CellState state;
	private int x,y;
	private Ship ship;
	
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		state = CellState.SEA;
	}
	
	public void setState(CellState state){
		this.state = state;
	}	
	public CellState getState() {
		return state;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	
	public Ship getShip() {
		return ship;
	}
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	public ShootState checkResult(){
		switch(state){
			case SEA:{
				state = CellState.MISS;
				return ShootState.MISS;
			}
			case INJURED:{
				return ShootState.MISS;
			}
			case MISS:{
				return ShootState.MISS;				
			}
			case SHIP : {
				state  = CellState.INJURED;
				return ship.shipShoot(this);
			}
			default : {
				assert false;
				return null;
			}
		}
	}

}
