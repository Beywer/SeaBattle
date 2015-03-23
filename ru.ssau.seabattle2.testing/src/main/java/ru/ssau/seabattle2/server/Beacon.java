package ru.ssau.seabattle2.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Прнимает UDP broadcast сообщения и подтверждает запросившему свое существование.
 * @author Beywer
 *
 */
public class Beacon implements Runnable {

	private DatagramSocket serverSocket;
	private volatile boolean notStopped;
	
	public Beacon() {
		try {
			serverSocket = new DatagramSocket(9095);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		notStopped = true;
	}
	
	public void run() {
		while(notStopped){
			byte[] buffer = new byte[0];
			DatagramPacket fromClient = new DatagramPacket(buffer,0);
			try {
				serverSocket.receive(fromClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			InetAddress clientAdr = fromClient.getAddress();
			System.out.println(clientAdr);
			
			DatagramPacket toClient = new DatagramPacket(buffer, 0, clientAdr, 9095);
			try {
				serverSocket.send(toClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
