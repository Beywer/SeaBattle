package ru.ssau.seabattle.opponent.net.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Цель этого класса - устанолвение соединения с оппонентом.
 * @author Beywer
 *
 */
public class ClientPending implements Runnable {

	private ServerSocket server;
	private  Socket connection;
	
	public ClientPending(ServerSocket server, Socket connection){
		this.server = server;
		this.connection = connection;
	}
	
	public void run() {
		
		try 
		{
			//Получаем соединение с оппонентом
			connection = server.accept();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
