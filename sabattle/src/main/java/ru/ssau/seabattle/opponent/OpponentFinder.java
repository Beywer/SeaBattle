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

public class OpponentFinder implements Runnable {

	private HashSet<String> oponentsFinded;
	private ReentrantLock opLock;
	private long time;
	
	private DatagramSocket broadcast;
	private DatagramPacket broadcastMessage;
	private ServerSocket server;
	
	public OpponentFinder(long time, ServerSocket server){
		opLock = new ReentrantLock();
		oponentsFinded = new HashSet<String>();
		this.time = time;
		this.server = server;
		try {
			byte[] message = InetAddress.getLocalHost().toString().getBytes();
			broadcastMessage = new DatagramPacket(message, message.length);
			broadcast = new DatagramSocket(9090);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < time){
			try {
				broadcast.send(broadcastMessage);
				
				Socket socket = server.accept();
				
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				
				String recivedMessage = dis.readLine();
				
				oponentsFinded.add(recivedMessage);
				
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
