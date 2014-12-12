package trash;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import ru.ssau.seabattle.core.Field;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {

		DatagramSocket datagramSocket = new DatagramSocket(9095);
		
		int len = 40;
		byte[] inBuf = "123456789".getBytes(); 
		DatagramPacket datagramPacket = null;
		Thread tr = new Thread(new Test2());
		tr.start();
		InetAddress adr = InetAddress.getByName("255.255.255.255");
		inBuf = InetAddress.getLocalHost().toString().getBytes();
		datagramPacket = new DatagramPacket(inBuf, inBuf.length, adr ,9092);
		datagramSocket.send(datagramPacket);
		
		System.out.println("Отправлено");
		
		datagramSocket.close();	
		
	}
}
