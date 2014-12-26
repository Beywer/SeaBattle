package ru.ssau.seabattle.game;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.Shoot;
import ru.ssau.seabattle.core.ShootState;

public class SeaBatGame implements Serializable {
	
	private transient boolean opponentFinded, opponentReady;

	private boolean gameEnded;
	private Field opponentField, myField;
	private ArrayList<Shoot> gameHistory;
	private TurnToken turnToken;
	
	//Нужны клиенту.
	private transient HashSet<String> servers;
	private transient InetAddress addressToConnect;

	public SeaBatGame(){
		gameHistory = new ArrayList<Shoot>();
		gameEnded = false;
	}
	
	public TurnToken getTurnToken() {
		return turnToken;
	}
	/**
	 * Определяет: кому принадлежит первый выстрел.
	 */
	public void chooseFirstShooter(){
		Random r = new Random();
		if(r.nextBoolean())
			turnToken = TurnToken.MY;
		else
			turnToken = TurnToken.OPPONENT;
	}

	public void myShoot(int x, int y) {
		ShootState state = opponentField.shoot(x, y);
		if(state == ShootState.MISS) turnToken = TurnToken.OPPONENT;
	}

	public ShootState opponentShoot(int x, int y) {
		ShootState state = myField.shoot(x, y);
		if(state == ShootState.MISS) turnToken = TurnToken.MY;
		return state;
	}

	/**
	 * Возвращает список непростеленных клеток для ИИ.
	 * @return Список клеток.
	 */
	public ArrayList<Coordinate> getFreeCells(){
		ArrayList<Coordinate> freeCells = new ArrayList<Coordinate>();
		for(int i =0; i < 10; i++)
			for(int j = 0; j < 10; j++){
				CellState state = myField.getCell(i, j).getState();
				if(state == CellState.SEA || state == CellState.SHIP)
					freeCells.add(new Coordinate(i, j));
			}
		return freeCells;
	}
	
	//===========================================
	//			GETTERS AND SETTERS
	//===========================================
	public Field getMyField() {
		return myField;
	}
	public Field getOpponentField() {
		return opponentField;
	}
	public boolean isOpponentFinded() {
		return opponentFinded;
	}
	public void setOpponentFinded(boolean opponentFinded) {
		this.opponentFinded = opponentFinded;
	}
	public boolean isOpponentReady() {
		return opponentReady;
	}
	public void setOpponentReady(boolean opponentReady) {
		this.opponentReady = opponentReady;
	}

	public void setOpponentField(Field opponentField) {
		this.opponentField = opponentField;
	}

	public void setMyField(Field myField) {
		this.myField = myField;
	}
	public boolean isGameEnded() {
		return gameEnded;
	}

	public ArrayList<Shoot> getGameHistory() {
		return gameHistory;
	}

	public HashSet<String> getServers() {
		return servers;
	}

	public void setServers(HashSet<String> servers) {
		this.servers = servers;
	}

	public InetAddress getAddressToConnect() {
		return addressToConnect;
	}

	public void setAddressToConnect(InetAddress addressToConnect) {
		this.addressToConnect = addressToConnect;
	}
	
}
