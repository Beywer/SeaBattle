package ru.ssau.seabattle.core;

import ru.ssau.seabattle.opponent.AI;


public class SingleGame {
	
	private AI opponent;
	private Field myField;
	private Field opponentField;
	
	public SingleGame(AI opponent, Field myField, Field opponentField ){
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
