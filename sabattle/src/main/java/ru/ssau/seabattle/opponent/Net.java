package ru.ssau.seabattle.opponent;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.ShootState;

public class Net {

	private Socket socket;
	private ServerSocket server;
	private Coordinate lastHit;
	private OpponentFinder opFinder;
	
	private ArrayList<String> aviableOpponents;
	
	public Net(){

		aviableOpponents = new ArrayList<String>();
		
		try {
			socket = new Socket(InetAddress.getLocalHost(), 9099);
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
		opFinder = new OpponentFinder(15000, server);
	}

	public ArrayList<String> getAviableOpponents() {
		aviableOpponents = opFinder.getOponentsFinded();
		return aviableOpponents;
	}
}
