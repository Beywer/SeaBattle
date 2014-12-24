package ru.ssau.seabattle.opponent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.ShootState;

public class Net {

	private Socket socket;
	private ServerSocket server;
	private ServerFinder opFinder;
	
	private HashSet<String> aviableOpponents;
	
	public Net(){

		aviableOpponents = new HashSet<String>();
		
		try {
			server = new ServerSocket(9099);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ShootState makeNextTurn() {

		return null;
	}

	public Coordinate getLastHit() {

		return null;
	}
	
	public void findOpponents(){
		
		aviableOpponents.clear();		
		opFinder = new ServerFinder(15000, server);
	}

	public HashSet<String> getAviableOpponents() {
		aviableOpponents = opFinder.getOponentsFinded();
		return aviableOpponents;
	}
}
