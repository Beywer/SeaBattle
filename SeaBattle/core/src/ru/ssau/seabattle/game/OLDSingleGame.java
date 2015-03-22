package ru.ssau.seabattle.game;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShootState;
import ru.ssau.seabattle.opponent.OLDAI;


public class OLDSingleGame implements Game {
	
	private OLDAI opponent;
	private Field myField;
	private Field opponentField;
	private boolean turn;
	
	public OLDSingleGame(OLDAI opponent, Field myField, Field opponentField ){
		this.opponent = opponent;
		this.myField = myField;
		this.opponentField = opponentField;		
		turn = false;
	}
	
	public ShootState myShoot(int x, int y){
		return opponentField.shoot(x, y);
	}
	
	public ShootState opponentShoot(){
		return opponent.makeNextTurn();
	}

	public Field getMyField() {
		return myField;
	}

	public Field getOpponentField() {
		return opponentField;
	}
	
	
}
