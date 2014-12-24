package ru.ssau.seabattle.opponent.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;

/**
 * Ведет рассылку UDP дейтаграмм с целью найти
 * и составить список всех доступных серверов.
 * @author Beywer
 *
 */
public class ServerFinder implements Runnable {

	private DatagramSocket socket;
	private ServerSocket clientServer;
	private Socket connection;
	private DatagramPacket message;
	private HashSet<String> servers;
	
	public ServerFinder(ServerSocket clientServer){
		this.clientServer = clientServer;
		try {
			socket = new DatagramSocket(9095);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		long startAnsTime = 0;
		while(System.currentTimeMillis() - startTime < 4000 ){
			try {
				System.out.println("Ожидание");
				//Создаем сообщение со своим IP адресом.
				byte[] mess = InetAddress.getLocalHost().getAddress();
				mess = InetAddress.getLocalHost().getAddress();
				message = new DatagramPacket(mess, mess.length,InetAddress.getByName("255.255.255.255"),9095);
				//Делаем широковещательный запрос серверам.
				socket.send(message);
				//Начинаем прием ответов.
				startAnsTime = System.currentTimeMillis();
				while(System.currentTimeMillis() - startAnsTime < 800){
					connection = clientServer.accept();
					BufferedReader serverAns = new BufferedReader(new
				            InputStreamReader(connection.getInputStream()));
					String recived = serverAns.readLine();
					//Заноситм их в список.
					servers.add(recived);
					connection.close();
				}
				
			} 
			catch (UnknownHostException e) {e.printStackTrace();}
			catch (IOException e) {	e.printStackTrace();}
		}
	}

	public HashSet<String> getServers() {
		return servers;
	}
	
}
