package ru.ssau.seabattle.opponent.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShootState;
import ru.ssau.seabattle.opponent.OpponentTurnGetter;

public class NetServer implements OpponentTurnGetter {

	private Field myField, opponentField;
	private Coordinate lastHit;
	
	private ServerSocket server;
	private Socket connection;
	
	public NetServer(){
		try {
			server = new ServerSocket(9099);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void waitConnection(){
		//Ждем подключения оппонента. После ожидания получаем connection.
		ClientPending waitClient = new ClientPending(server, connection);
		Thread thread = new Thread(waitClient);
		thread.start();
	}
	
	public ShootState makeNextTurn() {
		
		return null;
	}

	public Coordinate getLastHit() {
		return lastHit;
	}

}
