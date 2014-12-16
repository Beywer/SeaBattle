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
	
	private Field playerField;
	private boolean isFinishOff;
	private boolean isDirectionRight;
	private int dx,dy;
	private int firstX,firstY;	
	private int nextX,nextY;
	private Coordinate lastShoot;
	private ShootState lastShootState;
	
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
