package ru.ssau.seabattle.opponent;

import java.util.ArrayList;
import java.util.Random;

import ru.ssau.seabattle.core.Cell;
import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.core.Coordinate;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShootState;

public class AI{

	private Level level;
	private ArrayList<Coordinate> cells; 
	
	private Field playerField;
	private boolean isFinishOff;
	private boolean isDirectionRight;
	private int dx,dy;
	private int firstX,firstY;
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
		
		if(isFinishOff){ // Если добивание
			return finishOff();
		}
		else{ // Если не добивание
			Random r = new Random();
			//Выстрел в рандомную клетку без повторения выстрелов
			Coordinate nextCell = cells.get(r.nextInt(cells.size()));
			int y = nextCell.getY();
			int x = nextCell.getX();
			cells.remove(nextCell);
			lastShoot = new Coordinate(x, y);
			lastShootState = playerField.shoot(x, y);
			if(lastShootState == ShootState.INJURED){
				isFinishOff = true;  //Начинаем добивание, если ранен
				firstX = x;
				firstY = y;
				choosDirection(); //Bыбираем направление для следующего выстрела
			}
			return lastShootState;
		}
	}
	
	private ShootState finishOff() {
		int x = 0;
		int y = 0;
		if(isDirectionRight){//Если направление уже выбрано 
			if(lastShootState == ShootState.MISS){ //и при этом промах, то стреляем в другую сторону
				dx = 0 - dx;
				dy = 0 - dy;
				x = firstX + dx;
				y = firstY + dy;
				deleteCell(x,y);
				lastShoot = new Coordinate(x, y);
				lastShootState = playerField.shoot(x, y);
				if(lastShootState == ShootState.DEAD){
					isDirectionRight = false;
					isFinishOff = false;
				}
				return lastShootState;
			}
			else{ // то продолжаем стрелять дальше
				x = lastShoot.getX() + dx;
				y = lastShoot.getY() + dy;
				String str = String.valueOf(y+1)+ " " + String.valueOf((char)(x + 'A')) ;
				System.out.println(str + "то продолжаем стрелять дальше");
				System.out.println("isDirectionRight " + isDirectionRight);
				if(x < 0 || y < 0 || x > 9 || y > 9) //край корабля упирается в границу поля
				{
					System.out.println("isDirectsdfsdfsdfFFFFFFFFFFFFionRight " + isDirectionRight);
					dx = 0 - dx;
					dy = 0 - dy;
					x = firstX + dx;
					y = firstY + dy;
				}
				deleteCell(x,y);
				lastShoot = new Coordinate(x, y);
				lastShootState = playerField.shoot(x, y);
				if(lastShootState == ShootState.DEAD){
					isDirectionRight = false;
					isFinishOff = false;
				}
				return lastShootState;
			}
		}
		else{ //Если напрвление еще не достоверно
			//Делаем выстрел, и выясняем, правильно ли оно выбрано
			x = firstX + dx;
			y = firstY + dy;
			deleteCell(x,y);
			lastShoot = new Coordinate(x, y);
			lastShootState = playerField.shoot(x, y);
			if(lastShootState == ShootState.MISS){ //Если промах, то ошибка при выборе направления
				choosDirection();
			}
			if(lastShootState == ShootState.INJURED){ //Если же попали, значит нужно дальше бить в этом направлении
				isDirectionRight = true;
			}
			if(lastShootState == ShootState.DEAD){ //Если же попали, значит нужно дальше бить в этом направлении
				isFinishOff = false;
			}
			return lastShootState;
		}				
	}

	private void choosDirection(){		
		Random r= new Random();
		dx = 0; dy= 0;
		int temp = 0;
		CellState state = CellState.SEA;
		
		while(dx + dy == 0){ //пока не выбрано напрвление
			
			temp = r.nextInt(4); //Случайным образом определяем напрвлени
			switch(temp){
				case 0:{//Влево
					if(firstX - 1 >= 0){
						state = playerField.getCell(firstX - 1, firstY).getState();
						if(state != CellState.INJURED && state != CellState.MISS)
							dx = -1;
						System.out.println("Выбрано напрвление влево"); //TODO deletThis
					}
				}
				break;
				case 1:{//Вверх
					if(firstY - 1 >= 0){
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
	
	private void deleteCell(int x, int y){
		for(Coordinate coord : cells){
			if(coord.getX() == x && coord.getY() == y){
				cells.remove(coord);
				break;
			}
		}

	}
}
