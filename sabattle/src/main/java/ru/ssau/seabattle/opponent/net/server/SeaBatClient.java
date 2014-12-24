package ru.ssau.seabattle.opponent.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.opponent.Opponent;

/**
 * Ищет серверы и подключается к ним.
 * @author Beywer
 *
 */
public class SeaBatClient implements Runnable, Opponent {

	private SeaBatGame game;
	private ServerFinder finder;
	private Socket connection;
//	private ServerSocket clientServer;
	private InetAddress addressToConnect;
	private BufferedReader input;
	private PrintWriter output;
	
	public SeaBatClient(SeaBatGame game, InetAddress addressToConnect){
		this.game = game;
		this.addressToConnect = addressToConnect;
	}
	
	@Override
	public void run() {
		//Устанавливаем соединение.
		try {
			connection = new Socket(addressToConnect, 9099);
			input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			output = new PrintWriter(connection.getOutputStream());
		} 
		catch (IOException e) {e.printStackTrace();}
		
		String serverMessage = "";
		while(!game.isGameEnded()){
			try {
				serverMessage = input.readLine();
			}
			catch (IOException e) {e.printStackTrace();}
			switch(serverMessage){
				case SeaBatNetCodes.YOURTURN :
					break;
				case SeaBatNetCodes.ARE_YOU_READY:
					break;
				default : assert false;
			}
		}
	}
}
