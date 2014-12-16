package ru.ssau.seabattle.opponent;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.ShootState;

public interface TurnMaker {
		
	public ShootState makeNextTurn();
	public Coordinate getLastHit();
}
