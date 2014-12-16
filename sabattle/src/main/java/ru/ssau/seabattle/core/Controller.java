package ru.ssau.seabattle.core;

import java.io.DataInputStream;
import java.io.IOException;

import ru.ssau.seabattle.ai.AI;
import ru.ssau.seabattle.ai.Level;
import ru.ssau.seabattle.ai.TurnMaker;

public class Controller {

	private static Field myField,opponentField;
	private static TurnMaker opponent;
	private static Game game;
	
	public static void main(String[] args) throws IOException {

		myField = new Field();
		myField.generate();
		opponentField = new Field();
		opponentField.generate();
		
		opponent = new AI(Level.LOW, myField);
		
		game = new Game(opponent, myField, opponentField);
//		printFields();
		
		DataInputStream dis = new DataInputStream(System.in);
		
		while(true){
			System.out.println("======================================");
			printFields();
			System.out.println("Ваш ход :");
			String str = dis.readLine();
			str = str.trim().toUpperCase();
			int x = str.getBytes()[0] - 'A';
			int y = Integer.valueOf(str.substring(1).trim()) - 1;
			ShootState state = game.myShoot(x, y);
			System.out.println(state);
			while(state!= ShootState.MISS){
				printFields();
				System.out.println("\nВаш ход :");
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
			System.out.println("Ход противника : " + x + " " + y);
			System.out.println("Ход противника : " + (char)(x + 'A')  + " " + y);
			System.out.println(state);
			
			while(state!= ShootState.MISS){
				state = game.opponentShoot();
				x = opponent.getLastHit().getX();
				y = opponent.getLastHit().getY();
				System.out.println("Ход противника : " + x + " " + y);
				System.out.println("Ход противника : " + (char)(x + 'A')  + " " + y);
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
