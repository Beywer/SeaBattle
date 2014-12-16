package ru.ssau.seabattle.opponent;

import java.util.ArrayList;
import java.util.Random;

import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShipState;
import ru.ssau.seabattle.core.ShootState;

public class AI{

	private Level level;
	private ArrayList<Coordinate> cells; 
	private int[][] find4=new int[26][2];
	private int[][] find3=new int[34][2];
	private int[][] find2=new int[50][2];
	private Field playerField;
	private boolean isFinishOff;
	private boolean isDirectionRight;
	private int dx,dy;
	private int firstX,firstY;	
	private int nextX,nextY;
	private Coordinate lastShoot;
	private ShootState lastShootState;
	
	private void fillmass(){
		find4[0][0]=0; find4[0][1]=0;
		find4[1][0]=1; find4[1][1]=1;
		find4[2][0]=2; find4[2][1]=2;
		find4[3][0]=3; find4[3][1]=3;
		find4[4][0]=4; find4[4][1]=4;
		find4[5][0]=5; find4[5][1]=5;
		find4[6][0]=6; find4[6][1]=6;
		find4[7][0]=7; find4[7][1]=7;
		find4[8][0]=8; find4[8][1]=8;
		find4[9][0]=9; find4[9][1]=9;
		find4[10][0]=0; find4[10][1]=4;
		find4[11][0]=1; find4[11][1]=5;
		find4[12][0]=2; find4[12][1]=6;
		find4[13][0]=3; find4[13][1]=7;
		find4[14][0]=4; find4[14][1]=8;
		find4[15][0]=5; find4[15][1]=9;
		find4[16][0]=0; find4[16][1]=8;
		find4[17][0]=1; find4[17][1]=9;
		find4[18][0]=4; find4[18][1]=0;
		find4[19][0]=5; find4[19][1]=1;
		find4[20][0]=6; find4[20][1]=2;
		find4[21][0]=7; find4[21][1]=3;
		find4[22][0]=8; find4[22][1]=4;
		find4[23][0]=9; find4[23][1]=5;
		find4[24][0]=8; find4[24][1]=0;
		find4[25][0]=9; find4[25][1]=1;
		
		///////////////////////
		find3[0][0]=0; find3[0][1]=0;
		find3[1][0]=1; find3[1][1]=1;
		find3[2][0]=2; find3[2][1]=2;
		find3[3][0]=3; find3[3][1]=3;
		find3[4][0]=4; find3[4][1]=4;
		find3[5][0]=5; find3[5][1]=5;
		find3[6][0]=6; find3[6][1]=6;
		find3[7][0]=7; find3[7][1]=7;
		find3[8][0]=8; find3[8][1]=8;
		find3[9][0]=9; find3[9][1]=9;
		find3[10][0]=0; find3[10][1]=3;
		find3[11][0]=1; find3[11][1]=4;
		find3[12][0]=2; find3[12][1]=5;
		find3[13][0]=3; find3[13][1]=6;
		find3[14][0]=4; find3[14][1]=7;
		find3[15][0]=5; find3[15][1]=8;
		find3[26][0]=6; find3[26][1]=9;
		find3[16][0]=0; find3[16][1]=6;
		find3[17][0]=1; find3[17][1]=7;
		find3[27][0]=2; find3[27][1]=8;
		find3[28][0]=3; find3[28][1]=9;
		find3[29][0]=0; find3[29][1]=9;
		find3[18][0]=3; find3[18][1]=0;
		find3[19][0]=4; find3[19][1]=1;
		find3[20][0]=5; find3[20][1]=2;
		find3[21][0]=6; find3[21][1]=3;
		find3[22][0]=7; find3[22][1]=4;
		find3[23][0]=8; find3[23][1]=5;
		find3[30][0]=9; find3[30][1]=6;
		find3[24][0]=6; find3[24][1]=0;
		find3[25][0]=7; find3[25][1]=1;
		find3[31][0]=8; find3[31][1]=2;
		find3[32][0]=9; find3[32][1]=3;
		find3[33][0]=9; find3[33][1]=0;
		
		//////////////////////
		find2[0][0]=0; find2[0][1]=0;
		find2[1][0]=1; find2[1][1]=1;
		find2[2][0]=2; find2[2][1]=2;
		find2[3][0]=3; find2[3][1]=3;
		find2[4][0]=4; find2[4][1]=4;
		find2[5][0]=5; find2[5][1]=5;
		find2[6][0]=6; find2[6][1]=6;
		find2[7][0]=7; find2[7][1]=7;
		find2[8][0]=8; find2[8][1]=8;
		find2[9][0]=9; find2[9][1]=9;
		find2[10][0]=0; find2[10][1]=2;
		find2[11][0]=1; find2[11][1]=3;
		find2[12][0]=2; find2[12][1]=4;
		find2[13][0]=3; find2[13][1]=5;
		find2[14][0]=4; find2[14][1]=6;
		find2[15][0]=5; find2[15][1]=7;
		find2[26][0]=6; find2[26][1]=8;
		find2[34][0]=7; find2[34][1]=9;
		find2[16][0]=0; find2[16][1]=4;
		find2[17][0]=1; find2[17][1]=5;
		find2[27][0]=2; find2[27][1]=6;
		find2[28][0]=3; find2[28][1]=7;
		find2[35][0]=4; find2[35][1]=8;
		find2[36][0]=5; find2[36][1]=9;
		find2[29][0]=0; find2[29][1]=6;
		find2[37][0]=1; find2[37][1]=7;
		find2[38][0]=2; find2[38][1]=8;
		find2[39][0]=3; find2[39][1]=9;
		find2[40][0]=0; find2[40][1]=8;
		find2[41][0]=1; find2[41][1]=9;
		find2[18][0]=2; find2[18][1]=0;
		find2[19][0]=3; find2[19][1]=1;
		find2[20][0]=4; find2[20][1]=2;
		find2[21][0]=5; find2[21][1]=3;
		find2[22][0]=6; find2[22][1]=4;
		find2[23][0]=7; find2[23][1]=5;
		find2[30][0]=8; find2[30][1]=6;
		find2[42][0]=9; find2[42][1]=7;
		find2[24][0]=4; find2[24][1]=0;
		find2[25][0]=5; find2[25][1]=1;
		find2[31][0]=6; find2[31][1]=2;
		find2[32][0]=7; find2[32][1]=3;
		find2[43][0]=8; find2[43][1]=4;
		find2[44][0]=9; find2[44][1]=5;
		find2[33][0]=6; find2[33][1]=0;
		find2[45][0]=7; find2[45][1]=1;
		find2[46][0]=8; find2[46][1]=2;
		find2[47][0]=9; find2[47][1]=3;
		find2[48][0]=8; find2[48][1]=0;
		find2[49][0]=9; find2[49][1]=1;
		
	}
	
	public AI(Level level,Field field){
		this.level = level;
		this.playerField = field;
		cells = new ArrayList<Coordinate>();
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				cells.add(new Coordinate(i, j));
			}
		}
		isFinishOff = false;
		isDirectionRight = false;
	}

	private ShootState makeLowLevelTurn(){
		Random r = new Random();
		int x = r.nextInt(10); 
		int y = r.nextInt(10);
		lastShoot = new Coordinate(x, y);
		return playerField.shoot(x, y);
	}
	
	private ShootState makeMiddleLevelTurn(){
		if(isFinishOff){ //Если идет добивание
			if(lastShootState != ShootState.MISS){//Если при добивании не был промах, то стреляем дльше
				int x = lastShoot.getX() + dx;
				int y = lastShoot.getY() + dy;
				if(x > 0 && y > 0 && x < 10 &&y < 10){
					lastShoot = new Coordinate(x, y); //Запоминаем послендний выстрел
					lastShootState = playerField.shoot(x, y);
					if(lastShootState == ShootState.INJURED) isDirectionRight =  true;//правильно выбрано напрвление
					if(lastShootState == ShootState.DEAD) {
						isFinishOff = false;//Конец добивания
						isDirectionRight = false;
					}
				}
				else{
					//изеняем напрвление на проитвоположное.
					dx*=-1;
					dx*=-1;
					//Возвращаяемя назад по раненым палубам
					x = lastShoot.getX() + dx;
					y = lastShoot.getY() + dy;
					while(playerField.getCell(x, y).getState() == CellState.INJURED){
						x+=dx;
						y+=dy;
					}
					lastShootState = playerField.shoot(x, y);
					if(lastShootState == ShootState.DEAD) isFinishOff = false;//Конец добивания
				}
			}
			else{//Если промахулись 
				if(isDirectionRight) //Если направлиени при этом выбрано правильно
				{
					//изеняем напрвление на проитвоположное.
					dx*=-1;
					dx*=-1;
					//Возвращаяемя назад по раненым палубам
					int x = lastShoot.getX() + dx;
					int y = lastShoot.getY() + dy;
					while(playerField.getCell(x, y).getState() == CellState.INJURED){
						x+=dx;
						y+=dy;
					}
					lastShootState = playerField.shoot(x, y);
					if(lastShootState == ShootState.DEAD) isFinishOff = false;//Конец добивания
				}
				else{
					//Заного ищем направление
					choosDirection();
					int x = firstX + dx;
					int y = firstY + dy;
					lastShoot = new Coordinate(x, y); //Запоминаем послендний выстрел
					lastShootState = playerField.shoot(x, y);
					if(lastShootState == ShootState.INJURED) isDirectionRight =  true;//правильно выбрано напрвление
					if(lastShootState == ShootState.DEAD) {
						isFinishOff = false;//Конец добивания
						isDirectionRight = false;
					}
				}
					
			}
			return lastShootState;
		}
		else{ //Если не идет добивание, то выстрел - рандом
			Random r = new Random();
			int x = r.nextInt(10); 
			int y = r.nextInt(10);
			lastShoot = new Coordinate(x, y); //Запоминаем послендний выстрел
			lastShootState = playerField.shoot(x, y);			
			if(lastShootState == ShootState.INJURED){//Если ранили
				isFinishOff = true; //то включаем добивание
				firstX = x;//Запоминаем координаты удачного выстрела
				firstY = y;
				choosDirection(); //Ищем напрвеление следующего выстрела
			}
			return lastShootState;
		}
	}
	
	private void choosDirection(){
		
		Random r= new Random();
		
		System.out.println(firstX + " " + firstY);
		if(firstX - 1 > 0)
			System.out.print(playerField.getCell(firstX - 1, firstY).getState());
		if(firstY - 1 > 0)
			System.out.print(playerField.getCell(firstX, firstY - 1).getState());
		if(firstX + 1 > 10)
			System.out.print(playerField.getCell(firstX + 1, firstY).getState());
		if(firstY + 1 < 10)
			System.out.println(playerField.getCell(firstX, firstY + 1).getState());
		
		dx = 0; dy= 0;
		int temp = 0;
		CellState state = CellState.SEA;
		while(dx + dy == 0){ //пока не выбрано напрвление
			temp = r.nextInt(4); //Случайным образом определяем напрвлени
			switch(temp){
				case 0:{//Влево
					if(firstX - 1 > 0){
						state = playerField.getCell(firstX - 1, firstY).getState();
						if(state != CellState.INJURED && state != CellState.MISS)
							dx = -1;
						System.out.println("Выбрано напрвление влево"); //TODO deletThis
					}
				}
				break;
				case 1:{//Вверх
					if(firstY - 1 > 0){
						state = playerField.getCell(firstX, firstY  - 1).getState();
						if(state != CellState.INJURED && state != CellState.MISS)
							dy = -1;
						System.out.println("Выбрано напрвление вверх"); //TODO deletThis
					}
				}
				break;
				case 2:{//Вправо
					if(firstX + 1 < 10){
						state = playerField.getCell(firstX + 1, firstY).getState();
						if(state != CellState.INJURED && state != CellState.MISS)
							dx = 1;
						System.out.println("Выбрано напрвление вправо"); //TODO deletThis
					}
				}
				break;
				case 3:{//Вниз
					if(firstY + 1 < 10){
						state = playerField.getCell(firstX, firstY + 1).getState();
						if(state != CellState.INJURED && state != CellState.MISS)
							dy = 1;
						System.out.println("Выбрано напрвление вниз"); //TODO deletThis
					}
				}
				break;
				default : assert false;
			}
		}
	}
	
	private ShootState makeHardLevelTurn(){
		return null;
	}
	
	public ShootState makeNextTurn() {
		switch(level){
			case LOW:{return makeLowLevelTurn();}
			case MIDDLE:{return makeMiddleLevelTurn();}
			default:{return makeHardLevelTurn();}
		}
	}
	public Coordinate getLastHit() {
		return lastShoot;
	}
}
