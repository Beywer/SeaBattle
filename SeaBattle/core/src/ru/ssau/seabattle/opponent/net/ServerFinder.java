package ru.ssau.seabattle.opponent.net;

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
import java.util.HashMap;

public class ServerFinder extends Thread {

	private DatagramSocket udpClientServer;
	private DatagramPacket udpMessage;

	private ServerSocket clientServer;
	private Socket serverConnection;
	
	private boolean threadStoped;
	
	private int udpPort = 10097;
	private int tcpPort = 9097;
	
	private HashMap<InetAddress, String> servers;
	
	public ServerFinder(){
		try {
			udpClientServer = new DatagramSocket(udpPort);
			clientServer = new ServerSocket(tcpPort);
		} 
		catch (SocketException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		servers = new HashMap<InetAddress, String>();
		threadStoped = false;
	}
	
	@Override
	public void run() {		
		//Делаем запрос по всем возможным серверам
		String message = "";
		int port = 9099;
		try {
			InetAddress broadcastAdr = InetAddress.getByName("255.255.255.255");
			udpMessage = new DatagramPacket(message.getBytes(),	message.length(), broadcastAdr, port);
			udpClientServer.send(udpMessage);
			System.out.println("ServerFinder" + "udp sended");
		}
		catch (SocketException e) { if(!threadStoped) e.printStackTrace();}
		catch (UnknownHostException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		//Начинаем прием имен севреров
		System.out.println("ServerFinder" + "StartReciev");
		while(!threadStoped){
			try {
				serverConnection = clientServer.accept();
				try(BufferedReader br = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()))){
					String serverMessage = br.readLine();
					System.out.println("ServerFinder" + "Recived  " + serverMessage);
					servers.put(serverConnection.getInetAddress(), serverMessage);
				}
			}
			catch (SocketException e) { if(!threadStoped) e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
		}

		System.out.println("Finder  " + servers);
		
		clientServer = null;
		serverConnection = null;
		udpClientServer = null;
		udpMessage = null;
	}
	
	public void stopFinder(){
		threadStoped = true;
	}
	
	public HashMap<InetAddress, String> getServers() {
		return servers;
	}

	@Override
	public void interrupt() {
		super.interrupt();
		threadStoped = true;
		udpClientServer.close();
		try {
			clientServer.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
