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
import ru.ssau.seabattle.core.Shoot;
import ru.ssau.seabattle.core.ShootState;
import ru.ssau.seabattle.game.GameSettings;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.game.TurnToken;
import ru.ssau.seabattle.opponent.Opponent;

public class Server extends Thread implements Opponent {

	private ServerSocket server;
	private Socket connection;
	private InetAddress clientAddress;
	private boolean clientFinded;
	
	private int clientPort = 10095;
	private int sereverPort = 9095;
	
	private String serverName;
	private boolean threadStopped;
	
	private Field clientField;
	private SeaBatGame game;
	private boolean newGame;
	private GameSettings settings;
	
	public Server(String serverName, SeaBatGame game, GameSettings settings){
		try {
			server = new ServerSocket(sereverPort);
		} catch (IOException e) {e.printStackTrace();}
		
		this.serverName = serverName;
		this.game = game;
		this.settings = settings;
		clientFinded = false;
		threadStopped = false;
	}
	
	@Override
	public void run() {
		//Включаем обнаружение сервера.
		ServerDetectionThread detection = new ServerDetectionThread(serverName);
		detection.start();
		System.out.println("Server  " + "Запучщено обнаружение");
		//Ждем подключения клиента		
		createConnection(detection);
		
		while(!game.isGameEnded()){
			if(game.getTurnToken() == TurnToken.MY) 
				playerShoot();
			else
				oppShoot();
		}
		
	}
	
	private void oppShoot(){
		Shoot shoot = null;
		writeStr(Messages.YOUR_TURN);
		shoot = (Shoot)readObj();
		ShootState state = game.opponentShoot(shoot.getX(), shoot.getY());
		shoot.setState(state);
		writeObj(shoot);
	}
	
	private void playerShoot(){
		Shoot shoot  = game.getShoot();
		if(shoot != null){
			writeStr(Messages.MY_TURN);
			writeObj(shoot);
			System.out.println("Клиенту отправлен выстрел  " + shoot.getX() + "  " + shoot.getY() +
					"  " + shoot.getState() + "  " + shoot.getShooterName());
		}
	}
	
	/**
	 * Начинает работу с клиентом. Если это новая игра, то сообщяет клиенту, что от него нужно поле.
	 * Если это загруженная игра, то передает клиенту поле из файла загрузки.
	 */
	private void createConnection(Thread detection){
		String messages = readStr();
		clientAddress = connection.getInetAddress();
		if(settings.isGameLoaded()){
			loadNetGame();
		}else{
			newNetGame();
		}
		detection.interrupt();
	}
	
	private void newNetGame() {
		writeStr(Messages.NEW_GAME);
		Object field = readObj();
		if(field instanceof Field) game.setOpponentField((Field)field);
		((Field)field).print();
	}

	private void loadNetGame() {
		
	}

	private String readStr(){
		String clientMessage = null;
		try {
			if(connection != null) connection.close();
			connection = server.accept();
		}catch (IOException e) { e.printStackTrace();}
		
		try(BufferedReader fromClient=new BufferedReader(new InputStreamReader(connection.getInputStream()))){
			clientMessage = fromClient.readLine();
		}catch(IOException e){e.printStackTrace();}
		
		return clientMessage;
	}
	
	private void writeStr(String message){
		//Перенастройка на передачу
		try {
			if(connection != null) connection.close();
			connection = new Socket(clientAddress, clientPort);
		} catch (IOException e) {e.printStackTrace();}
		
		try(BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))){
			toClient.write(message);
			toClient.flush();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private Object readObj(){
		Object clientMessage = null;
		try {
			if(connection != null) connection.close();
			connection = server.accept();
		}catch (IOException e) { e.printStackTrace();}
		
		try(ObjectInputStream fromClient = new ObjectInputStream(connection.getInputStream())){
			clientMessage = fromClient.readObject();
		}
		catch(IOException e){e.printStackTrace();}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		
		return clientMessage;
	}
	
	private void writeObj(Object message){
		//Перенастройка на передачу
		try {
			if(connection != null) connection.close();
			connection = new Socket(clientAddress, clientPort);
		} catch (IOException e) {e.printStackTrace();}
		
		try(ObjectOutputStream toClient = new ObjectOutputStream(connection.getOutputStream())){
			toClient.writeObject(message);
			toClient.flush();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	
	@Override
	public void interrupt() {
		super.interrupt();
		threadStopped = true;
		try {
			server.close();
			connection.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void stopThread(){
		threadStopped = true;
	}

	@Override
	public void makeNextTurn() {
		
	}

	@Override
	public Coordinate getLastHit() {
		
		return null;
	}
}
