package ru.ssau.seabattle.game;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.opponent.AI;
import ru.ssau.seabattle.opponent.Level;
import ru.ssau.seabattle.opponent.ServerFinder;

public class Controller2 {

	private static Field myField,opponentField;
	private static AI opponent;
	private static SingleGame game;
	private static boolean flag;
	
	public static void main(String[] args) throws IOException, InterruptedException {

		ServerSocket server= new ServerSocket(9090);
		ServerFinder finder = new ServerFinder(15000, server);
		Thread thr = new Thread(finder);
		thr.start();	
		
		Thread.sleep(15000);
		
		HashSet<String> set = finder.getOponentsFinded();

		for(String str : set){
			System.out.println(str);
		}
		
		System.out.println("isAlive " + thr.isAlive());
		
//		System.out.p
		
		myField = new Field();
		myField.generate();
//		myField.setShip(0, 0, 4, true);
//		myField.setShip(0, 2, 4, true);
//		myField.setShip(0, 4, 4, true);
//		myField.setShip(0, 6, 4, true);
//		myField.setShip(0, 8, 4, true);
//		myField.setShip(5, 0, 4, true);
//		myField.setShip(5, 2, 4, true);
//		myField.setShip(5, 4, 4, true);
//		myField.setShip(5, 6, 4, true);
//		myField.setShip(5, 8, 4, true);
//		opponentField = new Field();
//		opponentField.generate();
//		
//		opponent = new AI(Level.MIDDLE, myField);
//		
//		game = new SingleGame(opponent, myField, opponentField);
////		printFields();
//		
//		DataInputStream dis = new DataInputStream(System.in);
//		
//		while(true){
//			System.out.println("======================================");
//			printFields();
//			if(flag){
//				System.out.println("#####");//TODO УДАЛИТЬ
//				System.out.println("#####");
//				flag = false;
//			}
//			System.out.print("Ваш ход : ");
//			String str = dis.readLine();
//			str = str.trim().toUpperCase();
////			Thread.sleep(1000);
//			int x = 1;
//			int y = 1;
//			ShootState state = game.myShoot(x, y);
//			System.out.println(state);
//			while(state!= ShootState.MISS){
//				printFields();
//				System.out.print("\nВаш ход : ");
//				str = dis.readLine();
//				str = str.trim().toUpperCase();
////				Thread.sleep(1000);
//				x = 1;
//				y = 1;
//				state = game.myShoot(x, y);
//			}
//			
//			printFields();
//			state = game.opponentShoot();
//			x = opponent.getLastHit().getX();
//			y = opponent.getLastHit().getY();
//			System.out.println("Ход противника : " + (char)(x + 'A')  + " " + (y+1) );
//			System.out.println(state);
//			
//			while(state!= ShootState.MISS){
//				flag = true;
//				printFields();
//				state = game.opponentShoot();
//				x = opponent.getLastHit().getX();
//				y = opponent.getLastHit().getY();
//				System.out.println("Ход противника : " + (char)(x + 'A')  + " " + (y+1));
//				System.out.println(state);
//			}
//		}
//		
//	}
//	
//	private static void printFields(){
//		Field field = game.getMyField();
//		System.out.println("  A B C D E F G H I J		  A B C D E F G H I J");
//		for(int j =0; j < 10 ; j++){
//			if(j+1 != 10)
//				System.out.print(" " + String.valueOf(j+1));
//			else
//				System.out.print(j+1);
//				
//			for(int i = 0; i < 10; i++){
//				switch(myField.getCell(i, j).getState()){
//					case SEA : System.out.print("\u25FB  ");
//					break;
//					case INJURED : System.out.print("X ");
//					break;
//					case MISS : System.out.print("\u29C8  ");
//					break;
//					case SHIP : System.out.print("\u25A3  ");
//					break;
//				}
//			}
//			System.out.print("		");
//			if(j+1 != 10)
//				System.out.print(" " + String.valueOf(j+1));
//			else
//				System.out.print(j+1);
//			field = game.getOpponentField();
//			for(int i = 0; i < 10; i++){
//				switch(opponentField.getCell(i, j).getState()){
//					case INJURED : System.out.print("X ");
//					break;
//					case MISS : System.out.print("\u29C8  ");
//					break;
//					default : System.out.print("\u25FB  ");
//				}
//			}
//			
//
//			System.out.print("		");
//			if(j+1 != 10)
//				System.out.print(" " + String.valueOf(j+1));
//			else
//				System.out.print(j+1);
//			field = game.getOpponentField();
//			for(int i = 0; i < 10; i++){
//				switch(opponentField.getCell(i, j).getState()){
//				case SEA : System.out.print("\u25FB  ");
//				break;
//				case INJURED : System.out.print("X ");
//				break;
//				case MISS : System.out.print("\u29C8  ");
//				break;
//				case SHIP : System.out.print("\u25A3  ");
//				break;
//				}
//			}
//			System.out.println();
//		}
	}
}
