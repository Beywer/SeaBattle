package trash;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Reciver {
	
	public static void main (String[] args) throws IOException{
		
		Socket socket ;
		ServerSocket server = new ServerSocket(9090);
		
		socket = server.accept();
		System.out.println("Reciver");
		
		
		InputStream io = socket.getInputStream();
		DataInputStream dio = new DataInputStream(io);

		System.out.println(dio.available());
		byte[] mess = new byte[dio.available()];
		dio.read(mess, 0, dio.available());
		String str = new String(mess, "UTF-8");
			System.out.print(str);
		socket.close();server.close();
	}
}
