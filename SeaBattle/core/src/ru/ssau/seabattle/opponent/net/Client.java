package ru.ssau.seabattle.opponent.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.opponent.Opponent;

public class Client extends Thread implements Opponent {
	
	private InetAddress serverAddress;
	private ServerSocket clientServer;
	private Socket serverConnection;
	
	private int serverPort = 9095;
	private int clientServerPort = 10095;
	
	private volatile boolean gameEnded;
	private volatile boolean fieldReady;
	
	private Field opponentField; //Поле клиента
	private Field playerField;
	
	public Client(InetAddress serverAddress, Field opponentField){

		try {clientServer = new ServerSocket(clientServerPort);}
		catch (IOException e) {e.printStackTrace();}
		
		this.serverAddress = serverAddress;
		gameEnded = false;
		this.opponentField = opponentField;
		playerField = new Field();
	}
	
	@Override
	public void run() {
		//Подключение к серверу
		writeStr("");
		System.out.println("Клиент" + "connected");
		String serverMessage = readStr();
		if(serverMessage.equals(Messages.NEW_GAME))
			createNewGame();
	}
	
	private void createNewGame() {
		writeObj(opponentField);
		System.out.println("Клиент" + "fieldSended");
	}
	

	private String readStr(){
		String clientMessage = null;
		try {
			if(serverConnection != null) serverConnection.close();
			serverConnection = clientServer.accept();
		}catch (IOException e) { e.printStackTrace();}
		
		try(BufferedReader fromClient=new BufferedReader(new InputStreamReader(serverConnection.getInputStream()))){
			clientMessage = fromClient.readLine();
		}catch(IOException e){e.printStackTrace();}
		
		return clientMessage;
	}
	
	private void writeStr(String message){
		//Перенастройка на передачу
		try {
			if(serverConnection != null) serverConnection.close();
			serverConnection = new Socket(serverAddress, serverPort);
		} catch (IOException e) {e.printStackTrace();}
		
		try(BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(serverConnection.getOutputStream()))){
			toClient.write(message);
			toClient.flush();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private Object readObj(){
		Object clientMessage = null;
		try {
			if(serverConnection != null) serverConnection.close();
			serverConnection = clientServer.accept();
		}catch (IOException e) { e.printStackTrace();}
		
		try(ObjectInputStream fromClient = new ObjectInputStream(serverConnection.getInputStream())){
			clientMessage = fromClient.readObject();
		}
		catch(IOException e){e.printStackTrace();}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		
		return clientMessage;
	}
	
	private void writeObj(Object message){
		//Перенастройка на передачу
		try {
			if(serverConnection != null) serverConnection.close();
			serverConnection = new Socket(serverAddress, serverPort);
		} catch (IOException e) {e.printStackTrace();}
		
		try(ObjectOutputStream toClient = new ObjectOutputStream(serverConnection.getOutputStream())){
			toClient.writeObject(message);
			toClient.flush();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	
	
	
	
	
	public void gameStop(){
		gameEnded = true;
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		gameEnded = true;
	}

	@Override
	public void makeNextTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Coordinate getLastHit() {
		// TODO Auto-generated method stub
		return null;
	}
}
