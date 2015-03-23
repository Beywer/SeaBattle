package ru.ssau.seabattle2.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class ServerFinder implements Runnable {

	private DatagramSocket clientSocket;
	private InetAddress broadcast;
	private InetAddress clientAddress;
	private long startTime;
	private volatile boolean notStopped;
	private Set<InetAddress> servers;
	
	public ServerFinder() {
		notStopped = true;
		try {
			clientSocket = new DatagramSocket(9095);
			clientSocket.setSoTimeout(2500);
			broadcast = InetAddress.getByName("255.255.255.255");
			servers = new LinkedHashSet<InetAddress>();
			clientAddress = InetAddress.getLocalHost();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		notStopped = false;
		clientSocket.close();
	}
	
	public Set<InetAddress> getServers(){
		return servers;
	}
	
	public void run() {
		while(notStopped){
			byte[] bufer = new byte[10];
			DatagramPacket toServer = new DatagramPacket(bufer, bufer.length, broadcast, 9095);
			try {
				clientSocket.send(toServer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			startTime = System.currentTimeMillis();
			while(System.currentTimeMillis() - startTime < 5000){
				DatagramPacket fromServer = new DatagramPacket(bufer, bufer.length);
				try {
					clientSocket.receive (fromServer);
				} catch (SocketTimeoutException e){}
				catch (IOException e) {
					e.printStackTrace();
				} 
				InetAddress serverAdr = fromServer.getAddress();
				if(serverAdr != null && !serverAdr.equals(clientAddress)){
					servers.add(serverAdr);
				}
			}
		}
	}

}
