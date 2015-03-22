package ru.ssau.seabattle.opponent.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Tester {

	public static void main(String[] args) throws InterruptedException, IOException {
		
//		System.out.println(InetAddress.getLocalHost());
//		ServerSocket server = new ServerSocket(9095);
//		Socket connection = server.accept();
//		System.out.println(connection.getInetAddress());
		
		DatagramSocket server = new DatagramSocket(9095);
		DatagramPacket pack = new DatagramPacket("".getBytes(), 0);
		server.receive(pack);
		System.out.println(server.getInetAddress());
	}

}
