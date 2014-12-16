package ru.ssau.seabattle.ai;

import java.util.ArrayList;
import java.util.Random;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShipState;
import ru.ssau.seabattle.core.ShootState;

public class AI implements TurnMaker {

	private Level level;
	private ArrayList<Coordinate> cells; 
	
	private Field playerField;
	private Coordinate firstHit;
	private Coordinate lastHit;
	private ShootState lastShootState;
	private ShipState lastShipState;
	
	public AI(Level level,Field field){
		this.level = level;
		this.playerField = field;
		cells = new ArrayList<Coordinate>();
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				cells.add(new Coordinate(i, j));
			}
		}
	}
	/**
//	 * Ищет координату для добивания корабля.
//	 * @return
//	 */
//	private Coordinate finishShip(){
//		
//	}
	
	private ShootState makeLowLevelTurn(){
		Random r = new Random();
		int x = r.nextInt(10); 
		int y = r.nextInt(10);
		lastHit = new Coordinate(x, y);
		return playerField.shoot(x, y);
	}
	
	private ShootState makeMiddleLevelTurn(){
		return null;
	}
	
	private ShootState makeHardLevelTurn(){
		return null;
	}
	
	public ShootState makeNextTurn() {
		switch(level){
			case LOW:{return makeLowLevelTurn();}
			case MIDDLE:{return makeMiddleLevelTurn();}
			default:{return makeHardLevelTurn();}
		}
	}
	public Coordinate getLastHit() {
		return lastHit;
	}
}
