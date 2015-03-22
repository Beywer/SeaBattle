package ru.ssau.seabattle.opponent.net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerDetectionThread extends Thread {

	private Socket connection;

	private DatagramSocket udpServer;
	private DatagramPacket udpMessage;
	private String serverName;
	
	
	
	private boolean threadStopped;
	
	public ServerDetectionThread(String serverName){
		try {
			udpServer = new DatagramSocket(9099);
			udpMessage = new DatagramPacket(new byte[]{}, 0);
		} catch (SocketException e) {e.printStackTrace();}
		
		//TODO осторожнее с этим
		this.setDaemon(true);
		this.serverName = serverName;
		threadStopped = false;
	}
		
	@Override
	public void run() {
		System.out.println("Detection" + "Wait#");
		while(!threadStopped){
			try {
				//Ждем запросов
				System.out.println("Detection" + "Wait");
				udpServer.receive(udpMessage);
				System.out.println("Detection  " + udpMessage.getPort());
				System.out.println("Detection  " + udpMessage.getAddress());
				//Отправляем на каждый запрос имя сервера по TCP
				//TODO Поставить номальный порт
				connection = new Socket(udpMessage.getAddress(), 9097);
				try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))){
					bw.write(serverName);
					bw.flush();
				}
			}
			catch (SocketException e){if(!threadStopped) e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();} 
		}
		
		udpServer = null;
		udpMessage = null;
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		threadStopped = true;
		udpServer.close();
		try {
			if(connection != null) connection.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
