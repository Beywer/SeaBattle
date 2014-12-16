package ru.ssau.seabattle.opponent;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.ShootState;

public class Net implements TurnMaker {

	private Socket socket;
	private ServerSocket server;
	private DatagramSocket broadcast;
	private Coordinate lastHit;
	
	public ShootState makeNextTurn() {

		return null;
	}

	public Coordinate getLastHit() {

		return null;
	}
	

}
