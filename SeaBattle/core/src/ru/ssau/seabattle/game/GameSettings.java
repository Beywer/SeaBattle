package ru.ssau.seabattle.game;

import ru.ssau.seabattle.opponent.Level;

public class GameSettings {

	private boolean singleGame;
	private Level level;
	private boolean serverGame;
	private boolean gameLoaded;
	
	public GameSettings(){
		singleGame = true;
		level = Level.LOW;
		serverGame = false;
		gameLoaded = false;
	}
	
	public void mackeItSingleGame(Level level){
		singleGame = true;
		this.level = level;
		serverGame = false;
	}
	
	public void mackeItServer(){
		serverGame = true;
		singleGame = false;
		level = null;
	}
	
	public void mackeItClient(){
		serverGame = false;
		singleGame = false;
		level = null;
	}
	
	public void setGameLoaded(boolean val){
		gameLoaded = val;
	}
	
	public boolean isSingleGame() {
		return singleGame;
	}

	public Level getLevel() {
		return level;
	}

	public boolean isServerGame() {
		return serverGame;
	}

	public boolean isGameLoaded() {
		return gameLoaded;
	}
	
}
