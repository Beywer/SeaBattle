package ru.ssau.seabattle.opponent;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;

public class ServerFinder implements Runnable {

	private HashSet<String> oponentsFinded;
	private ReentrantLock opLock;
	private long time;
	
	private DatagramSocket broadcast;
	private DatagramPacket broadcastMessage;
	private ServerSocket server;
	
	public ServerFinder(long time, ServerSocket server){
		opLock = new ReentrantLock();
		oponentsFinded = new HashSet<String>();
		this.time = time;
		this.server = server;
		try {
			//Получаем свой адрес в байтах
			byte[] message = InetAddress.getLocalHost().getAddress();
			//Задаем широковещательную рассылку
			InetAddress adr = InetAddress.getByName("255.255.255.255");
			//Создаем сообщение с адресом
			broadcastMessage = new DatagramPacket(message, message.length,adr,9092);
			broadcast = new DatagramSocket(9090);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		//Отправка UDP датаграммы 5 раз за указанное время
		int sendsNumber = 2;
		time /=sendsNumber;
		for(int i =0; i < sendsNumber; i++)
		{
			long startTime = System.currentTimeMillis();
			try {
				//Отправка сообщенияы
				broadcast.send(broadcastMessage);
				System.out.println("Controller sended ");
				//Принятие сообщения
				while(System.currentTimeMillis() - startTime < time){
					Socket socket = server.accept();				
					DataInputStream dis = new DataInputStream(socket.getInputStream());				
					String recivedMessage = dis.readLine();	
					opLock.lock();			
					oponentsFinded.add(recivedMessage);
					opLock.unlock();
					System.out.println("Controller recive " + recivedMessage);						
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public HashSet<String> getOponentsFinded() {
		opLock.lock();
		HashSet<String> buf = oponentsFinded;
		opLock.unlock();
		return buf;
	}
}
