package ru.ssau.seabattle.game;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShootState;

public interface Game {

	public ShootState myShoot(int x, int y);  
	
	public ShootState opponentShoot();

	public Field getMyField() ;

	public Field getOpponentField() ;
	
}
