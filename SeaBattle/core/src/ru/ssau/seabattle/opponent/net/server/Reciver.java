package ru.ssau.seabattle.opponent.net.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Цель класса - сообщять всем обращающимся, что данный сервер существует и готов работать.
 * @author Beywer
 *
 */
public class Reciver implements Runnable {
	
	private DatagramSocket udpServer;
	private DatagramPacket recivedAddress;
	private boolean oppFinded; //Нужен для будущей остановки обнаружения
	
	public Reciver(){
	}
	
	public void run() {
		while(true){
			byte[] address = new byte[4];
			DataOutputStream dos = null;
			Socket socket = null;
			try {
				//Ждем адрес
				udpServer = new DatagramSocket(9095);
				recivedAddress = new DatagramPacket(address, 4);
				udpServer.receive(recivedAddress);
				//Устанавливается соединенеие по указанному IP
				InetAddress callBackAddress = InetAddress.getByAddress(address);
				socket = new Socket(callBackAddress, 9099);				
				dos = new DataOutputStream(socket.getOutputStream());
				//Формирование совего IP для передачи
				byte[] myAdr = InetAddress.getLocalHost().getAddress();
				//Отправка адреса
				dos.write(myAdr);				
			} 
			catch (SocketException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
