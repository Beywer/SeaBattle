package ru.ssau.seabattle2.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerFinder implements Runnable {

	private DatagramSocket clientSocket;
	private InetAddress broadcast;
	private long startTime;
	private volatile boolean notStopped;
	
	public ServerFinder() {
		notStopped = true;
		try {
			clientSocket = new DatagramSocket(9095);
			broadcast = InetAddress.getByName("255.255.255.255");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		notStopped = false;
	}
	
	public void run() {
		while(notStopped){
			byte[] buffer = "IsThereServer?".getBytes();
			try {
				buffer = InetAddress.getLocalHost().getAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			DatagramPacket toServer = new DatagramPacket(buffer, buffer.length, broadcast, 9095);
			try {
				clientSocket.send(toServer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			startTime = System.currentTimeMillis();
			while(System.currentTimeMillis() - startTime < 5000){
				System.out.println(buffer.length);
				DatagramPacket fromServer = new DatagramPacket(buffer, buffer.length);
				try {
					clientSocket.receive(fromServer);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(new String(buffer));
				System.out.println(fromServer.getAddress());
			}
		}
	}

}
