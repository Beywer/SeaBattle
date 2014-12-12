package trash;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Test2 implements Runnable {
		
	public static void main (String[] args){

		long startTime = System.currentTimeMillis();
		int len  = 30;
		byte[] inBuf = new byte[len];
		
		DatagramSocket datagramSocket = null;
		DatagramPacket datagramPacket = null;
		
		try {
	
			datagramSocket = new DatagramSocket(9092);
			datagramPacket = new DatagramPacket(inBuf, len);
	
			while(true){
				datagramSocket.receive(datagramPacket);
				System.out.print("Приянто: ");
				System.out.println(new String(inBuf));
				for(int i=0;i<len;i++)
					inBuf[i] = 0;
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			datagramSocket.close();
			datagramSocket.close();
		}
	}
	
	public void run() {
	
//		long startTime = System.currentTimeMillis();
//		int len  = 30;
//		byte[] inBuf = new byte[len];
//		
//		DatagramSocket datagramSocket = null;
//		DatagramPacket datagramPacket = null;	
//		
//		try {
//	
//			datagramSocket = new DatagramSocket(9092);
//			datagramPacket = new DatagramPacket(inBuf, len);
//	
//			while(true){
//				datagramSocket.receive(datagramPacket);
//				System.out.print("Приянто: ");
//				System.out.println(new String(inBuf));
//			}
////			datagramSocket.close();
////			datagramSocket.close();
//		} catch (SocketException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		finally{
//			datagramSocket.close();
//			datagramSocket.close();
//		}
	}
		
}
