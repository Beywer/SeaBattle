package ru.ssau.seabattle.opponent;

import ru.ssau.seabattle.core.Coordinate;

/**
 * Интерфейс-маркер оппонента в морском бою.
 * @author Beywer
 *
 */
public interface Opponent {
	public void makeNextTurn();
	public Coordinate getLastHit();
}
