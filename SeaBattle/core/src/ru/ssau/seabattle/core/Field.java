package ru.ssau.seabattle.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Field implements Serializable {
	
	private static final long serialVersionUID = 3732389266114014786L;
	private Cell[][] cells = new Cell[10][10];
	private ArrayList<Ship> ships;
	private ShipState lastState;
	
	public Field(){		
		for(int i = 0; i<10; i++){
			for(int j =0; j<10; j++){
				cells[i][j] = new Cell(i,j);
			}
		}
		ships = new ArrayList<Ship>();
	}
	
	/**
	 * Создает корабль на поле, если это возможно.
	 * @param x
	 * @param y
	 * @param decksNumber
	 * @param direction
	 * @return
	 */
	public boolean setShip (int x, int y, int decksNumber, boolean direction){

		Ship ship = new Ship(x, y, decksNumber, direction);		//создаение корабля
		if(inspectShip(x, y, decksNumber, direction)){			//если возможно разместить корабль
			if(direction){										//корабля ставится вправо или вниз, в зависимости от направления
				for(int i = x; i < x+decksNumber ; i++){
					cells[i][y].setState(CellState.SHIP);		
					cells[i][y].setShip(ship);	
					ship.addCell(cells[i][y]);
				}
				ships.add(ship);
				return true;
			}
			else{
				for(int i = y; i < y+decksNumber ; i++){
					cells[x][i].setState(CellState.SHIP);
					cells[x][i].setShip(ship);	
					ship.addCell(cells[x][i]);
				}
				ships.add(ship);
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Проверяет, может ли коралбль быть расположен в заданном месте
	 */
	private boolean inspectShip(int x, int y, int decksNumber, boolean direction){
		
		boolean result = true;
		if(x>=0 && x<10 && y>=0 && y<10){  		//если нос корабля на поле
			if(direction){ 						//координата кормы считается в зависимости от направления корабля 
				int poop = x+decksNumber-1;
				if( poop < 10 ){				//если корма на поле
					int i = x;
					while(i<=poop & result){
						result = cellIsFree(i,y);	//проверка клетки и её окрестности
						i++;
					}						
				}
				else result = false;
			}
			else{
				int poop = y+decksNumber-1;
				if( poop < 10 ){				//если корма на поле
					int i = y;
					while(i<=poop & result){
						result = cellIsFree(x,i);	//проверка клетки и её окрестности
						i++;
					}						
				}
				else result = false;
			}
		}
		else result = false;
		return result;
	}

	/**
	 * Проверка свободности клетки и её окрестности от кораблей
	 */
	private boolean cellIsFree(int x, int y) {
		for(int i = x - 1; i <= x + 1; i++){
			for(int j = y - 1; j <= y + 1; j++){   // пробег по клетке и её окрестности
				if(i >= 0 & j >= 0 & i < 10 & j < 10)	//на случай, если клетка в углу или скраю
				{
					if(cells[i][j].getState() == CellState.SHIP) return false;  //если обнаружен корабль, клетку нельзя использовать
				}
			}
		}
		return true;
	}
	
	/**
	 * Удаляет корабль
	 * @param x
	 * @param y
	 * @param decksNumber
	 * @param direction
	 */
	public void deleteShip (int x, int y, int decksNumber, boolean direction){
		
		if(x >= 0 & y >= 0 & x < 10 & y < 10)			//если нос попадает в поле
			{
			if(direction){				//в зависимости от направления удаляются клитки вниз или вправо
				if(x + decksNumber -1 < 10){
					for(int i=x; i< x+decksNumber;i++){
						cells[i][y].setState(CellState.SEA);	//Очистка клеток
					}
					ships.remove(cells[x][y].getShip());		//Удаление корабля из списка
				}
			}
			else{
				if(y + decksNumber -1 < 10){
					for(int j=y; j< y+decksNumber;j++)
						cells[x][j].setState(CellState.SEA);	//Очистка клеток
					ships.remove(cells[x][y].getShip());		//Удаление корабля из списка
				}
			}
		}
	}
	
	/**
	 * Удаляет корабль.
	 * @param ship
	 */
	public void deleteShip (Ship ship){
		int x = ship.getX();
		int y = ship.getY();
		int decks = ship.getDecksNumber();
		boolean direction = ship.isDirection();
		deleteShip(x,y,decks,direction);
	}
	
	/**
	 * Поворачивает корабль. В ходе поворота напрвавлени изменится на противоположное.
	 * @param x
	 * @param y
	 * @param decksNumber
	 * @param direction
	 */
	public void turnShip (int x, int y, int decksNumber, boolean direction){
		boolean newDirection = !direction;
		if(inspectShip(x, y, decksNumber, newDirection)){   //Если можно разместить корабль с другим направлением
			deleteShip(x, y, decksNumber, direction);		//Удалить ставый корабль
			setShip(x, y, decksNumber, newDirection);		//Поставить корабль с новым направлением
		}
	}
		
	/**
	 * Очистка поля.
	 */
	public void clear(){
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				cells[i][j].setState(CellState.SEA);
		ships = new ArrayList<Ship>();
	}
	
	/**
	 * Создает поле со случайным расположением кораблей.
	 */
	public void generate(){
		
		Random r = new Random();
		int x=0,y=0;
		boolean direction = true;
		
		clear();		//предварительная очистка поля
		for(int i=4; i>=1; i--){		//расстановка от 4хпалубных к 1палубным
			for(int j=5-i; j>=1; j--){	//Количество кораблей 5-количесво палуб
				do{
					x = r.nextInt(10);
					y = r.nextInt(10);
					direction = r.nextBoolean();		//генерируются координаты и напрвление корабля
				}while(!setShip(x, y, i, direction));	//пока он наконец не будет поставлен.
			}
		}
	}
	
	/**
	 * Делает выстрел по полю.
	 * @param x
	 * @param y
	 * @return возвращает состояние выстрела: MISS,INJURED,DEAD.
	 */
	public ShootState shoot(int x, int y){
		ShootState result = cells[x][y].checkResult();
		if(result == ShootState.DEAD) missAround(cells[x][y].getShip());
		Ship ship = cells[x][y].getShip();
		if(ship != null) lastState  = ship.getState();
		else lastState = null;
		return result;
	}
		
	public Cell getCell(int x, int y){
		return cells[x][y];
	}
	
	public ShipState getLastState(){
		return lastState;
	}
	
	//TODO  delete this method
	public void print(){

		System.out.print("		||--||		 	A B C D E F G H I J\n");
		for(int j = 0; j < 10 ; j++){
			System.out.print("		||--||		" + j + "	");
			for(int i = 0; i < 10 ; i++){
				switch(cells[i][j].getState()){
				case SEA : System.out.print("\u25FB  ");
				break;
				case INJURED : System.out.print("X ");
				break;
				case MISS : System.out.print("\u29C8  ");
				break;
				case SHIP : System.out.print("\u25A3  ");
				break;
				default : assert false;
				}
			}

			System.out.print("\n");
		}
	}


	private void missAround(Ship ship){
		int x = ship.getX();
		int y = ship.getY();
		if(ship.isDirection()){
			for(int i = x; i < x+ship.getDecksNumber(); i++)
				missAround(i,y);			
		}else{
			for(int i = y; i < y+ship.getDecksNumber(); i++)
				missAround(x,i);			
		}
	}
	private void missAround(int x, int y){
		for(int i = x-1; i<=x+1;i++){
			for(int j=y-1;j<=y+1;j++)
				if(i>=0 && j >= 0 && j < 10 && i < 10)
					if(cells[i][j].getState() ==  CellState.SEA)
						cells[i][j].setState(CellState.MISS);
		}
	}
	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	public boolean isFieldEmpty(){
		for(int i =0; i < 10; i ++)
			for(int j = 0; j < 10 ; j++)
				if(cells[i][j].getState() == CellState.SHIP) return false;
		return true;
	}
}
