package ru.ssau.seabattle.game;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShootState;

public class SeaBatGame implements Game {
	
	private boolean gameEnd;
	private TurnToken turnToken;

	public boolean isGameEnd() {
		return gameEnd;
	}
	
	public TurnToken getTurnToken() {
		return turnToken;
	}

	public ShootState myShoot(int x, int y) {
		return null;
	}

	public ShootState opponentShoot(int x, int y) {
		return null;
	}

	public Field getMyField() {
		return null;
	}

	public Field getOpponentField() {
		return null;
	}

	public ShootState opponentShoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
