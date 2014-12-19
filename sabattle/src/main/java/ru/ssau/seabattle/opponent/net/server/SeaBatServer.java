package ru.ssau.seabattle.opponent.net.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.ShootState;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.game.TurnToken;
import ru.ssau.seabattle.opponent.OpponentTurnGetter;

public class SeaBatServer implements Runnable, OpponentTurnGetter  {
	
	private ServerSocket server;
	private Socket connection;
	private Reciver reciver; //Отвечает за обнаружение сервера
	private SeaBatGame game;
	private DataInputStream input;
	private DataOutputStream output;
	
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
		
		
		
		doGame();
	}
	
	private void doGame(){
		
		byte[] buf = new byte[50];
		
		//Пока игра не кончилась
		while(!game.isGameEnd()){
			//Если ход противника
			if(game.getTurnToken() == TurnToken.OPPONENT){
				try {
					//Отправляем запрос хода
					output.writeBytes(ServerMessages.YOUR_TURN);
					while(input.available() == 0){}
					//Получаем 2 байта: х и у
					input.read(buf, 0, 2);
					//Производим выстрел противника
					ShootState state = game.opponentShoot(buf[0], buf[1]);
					//Отсылаем противнику ответ
					output.writeBytes(state.name());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//TODO Возможно их придется удалить, если изменится смысл интерфейса OpponentTurnGetter
	public ShootState makeNextTurn() {
		return null;
	}
	public Coordinate getLastHit() {
		return null;
	}
	
	
	
}
