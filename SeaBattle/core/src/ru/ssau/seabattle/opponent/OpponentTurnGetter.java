package ru.ssau.seabattle.opponent;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.ShootState;

public interface OpponentTurnGetter {
	
	public ShootState makeNextTurn();
	public Coordinate getLastHit();
}
