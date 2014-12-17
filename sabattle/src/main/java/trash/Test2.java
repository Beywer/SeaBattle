package trash;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Test2 implements Runnable {
		
	public static void main (String[] args){

		long startTime = System.currentTimeMillis();
		int len  = 4;
		byte[] inBuf = new byte[len];
		
		DatagramSocket datagramSocket = null;
		DatagramPacket datagramPacket = null;
		
		ServerSocket server = null;
		
		try {
	
			datagramSocket = new DatagramSocket(9092);
			datagramPacket = new DatagramPacket(inBuf, len);
	
			server = new ServerSocket(9095);
			Socket socket ;
			
			
			
			while(true){
				System.out.println("Test2 waiting 9092: ");
				datagramSocket.receive(datagramPacket);
				System.out.print("Приянто: ");
				System.out.println(inBuf);
				System.out.println(new String(inBuf));
				
				socket = new Socket(InetAddress.getByAddress(inBuf), 9090);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				
				for(int i=0;i<len;i++)
					inBuf[i] = 0;
				
				String mess = "Это мой адрес";
				System.out.println("Test2 send answer: " + mess);
				dos.write(mess.getBytes());
				dos.close();
				
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
