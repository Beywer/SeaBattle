package ru.ssau.seabattle.core;

import ru.ssau.seabattle.ai.TurnMaker;

public class Game {
	
	private TurnMaker opponent;
	private Field myField;
	private Field opponentField;
	
	public Game(TurnMaker opponent, Field myField, Field opponentField ){
		this.opponent = opponent;
		this.myField = myField;
		this.opponentField = opponentField;		
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
