package ru.ssau.seabattle.opponent.net.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShootState;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.game.TurnToken;
import ru.ssau.seabattle.opponent.OpponentTurnGetter;

public class SeaBatServer implements Runnable  {
	
	private ServerSocket server;
	private Socket connection;
	private Reciver reciver; //Отвечает за обнаружение сервера
	private SeaBatGame game;
	private DataInputStream input;
	private DataOutputStream output;
	

	private boolean opponentReady;
	private boolean fieldGot;
	
	public SeaBatServer(){
		opponentReady = false;
	}
	
	public void run() {
		
		//Создаем сервер
		try {
			server = new ServerSocket(9099);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Запускаем обнаружение сервера
		reciver = new Reciver();
		Thread thr = new Thread(reciver);
		thr.setDaemon(true);
		thr.start();
		
		//Ждем входящего подлкючения
		try {
			connection = server.accept();
			input = new DataInputStream(connection.getInputStream());
			output = new DataOutputStream(connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Оппонент найден
		game.setOpponentFinded(true);
		//Подготовка к игре: расстановка кораблей, определение первого ходящего.
		prepareToGame();
		//Выбор ходов.
		doGame();
		
		try{
			connection.close();
			server.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void prepareToGame(){
		int recivesCode = 0;
		Object oppField = null;

		try {
			//Пока не получим поле
			do{
				//Ждем готовности оппонента
				while(!opponentReady){
					Thread.sleep(150);
					recivesCode = input.readInt();
					//Оппонент готов
					if(recivesCode == SeaBatNetCodes.FIELD_READY) opponentReady = true;
				}
				try(ObjectInputStream fieldGetter = new ObjectInputStream(input)){
					//Cчитываем поле
					oppField = (Field) fieldGetter.readObject();
				}catch (ClassNotFoundException e) {	e.printStackTrace(); }
				if(oppField != null && oppField instanceof Field) fieldGot = true;
			}while(!fieldGot);
			game.setMyField((Field)oppField);
			
		}
		catch (IOException e) {e.printStackTrace();} 
		catch (InterruptedException e) {e.printStackTrace();}
	}

	private void doGame(){
		
		int x =0;
		int y = 0;		
		//Пока игра не кончилась
		while(!game.isGameEnded()){
			//Если ход противника
			if(game.getTurnToken() == TurnToken.OPPONENT){
				try {
					//Отправляем запрос хода
					output.write(SeaBatNetCodes.YOUR_TURN);
					while(input.available() == 0){}
					//Получаем координаты.
					x = input.readInt();
					y = input.readInt();
					//Производим выстрел противника
					ShootState state = game.opponentShoot(x, y);
					//Отсылаем противнику ответ
					output.writeBytes(state.name());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
