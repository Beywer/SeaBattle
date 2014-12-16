package ru.ssau.seabattle.core;

import java.io.DataInputStream;
import java.io.IOException;

import ru.ssau.seabattle.opponent.AI;
import ru.ssau.seabattle.opponent.Level;
import ru.ssau.seabattle.opponent.TurnMaker;

public class Controller {

	private static Field myField,opponentField;
	private static TurnMaker opponent;
	private static Game game;
	private static boolean flag;
	
	public static void main(String[] args) throws IOException {

		myField = new Field();
		myField.generate();
		opponentField = new Field();
		opponentField.generate();
		
		opponent = new AI(Level.MIDDLE, myField);
		
		game = new Game(opponent, myField, opponentField);
//		printFields();
		
		DataInputStream dis = new DataInputStream(System.in);
		
		while(true){
			System.out.println("======================================");
			printFields();
			if(flag){
				System.out.println("#####");//TODO УДАЛИТЬ
				System.out.println("#####");
				flag = false;
			}
			System.out.print("Ваш ход : ");
			String str = dis.readLine();
			str = str.trim().toUpperCase();
			int x = str.getBytes()[0] - 'A';
			int y = Integer.valueOf(str.substring(1).trim()) - 1;
			ShootState state = game.myShoot(x, y);
			System.out.println(state);
			while(state!= ShootState.MISS){
				printFields();
				System.out.print("\nВаш ход : ");
				str = dis.readLine();
				str = str.trim().toUpperCase();
				x = str.getBytes()[0] - 'A';
				y = Integer.valueOf(str.substring(1).trim()) - 1;
				state = game.myShoot(x, y);
			}
			
			printFields();
			state = game.opponentShoot();
			x = opponent.getLastHit().getX();
			y = opponent.getLastHit().getY();
			System.out.println("Ход противника : " + (char)(x + 'A')  + " " + (y+1) );
			System.out.println(state);
			
			while(state!= ShootState.MISS){
				flag = true;
				printFields();
				state = game.opponentShoot();
				x = opponent.getLastHit().getX();
				y = opponent.getLastHit().getY();
				System.out.println("Ход противника : " + (char)(x + 'A')  + " " + (y+1));
				System.out.println(state);
			}
		}
		
	}
	
	private static void printFields(){
		Field field = game.getMyField();
		System.out.println("  A B C D E F G H I J		  A B C D E F G H I J");
		for(int j =0; j < 10 ; j++){
			if(j+1 != 10)
				System.out.print(" " + String.valueOf(j+1));
			else
				System.out.print(j+1);
				
			for(int i = 0; i < 10; i++){
				switch(myField.getCell(i, j).getState()){
					case SEA : System.out.print("\u25FB  ");
					break;
					case INJURED : System.out.print("X ");
					break;
					case MISS : System.out.print("\u29C8  ");
					break;
					case SHIP : System.out.print("\u25A3  ");
					break;
				}
			}
			System.out.print("		");
			if(j+1 != 10)
				System.out.print(" " + String.valueOf(j+1));
			else
				System.out.print(j+1);
			field = game.getOpponentField();
			for(int i = 0; i < 10; i++){
				switch(opponentField.getCell(i, j).getState()){
					case INJURED : System.out.print("X ");
					break;
					case MISS : System.out.print("\u29C8  ");
					break;
					default : System.out.print("\u25FB  ");
				}
			}
			

			System.out.print("		");
			if(j+1 != 10)
				System.out.print(" " + String.valueOf(j+1));
			else
				System.out.print(j+1);
			field = game.getOpponentField();
			for(int i = 0; i < 10; i++){
				switch(opponentField.getCell(i, j).getState()){
				case SEA : System.out.print("\u25FB  ");
				break;
				case INJURED : System.out.print("X ");
				break;
				case MISS : System.out.print("\u29C8  ");
				break;
				case SHIP : System.out.print("\u25A3  ");
				break;
				}
			}
			System.out.println();
		}
	}
}
