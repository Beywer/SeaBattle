package trash;

import java.io.*;
import java.util.Random;

public class OldField {

	public enum Cell {SEA,MISS,SHIP,STRICKEN};
	
	public Cell[][] cells;
	
	public OldField (){		
		cells = new Cell[10][10];
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				cells[i][j] = Cell.SEA;
	}
	
	private boolean checkCell(int x,int y){
		for(int i = x-1; i >= x+1; i++){
			for(int j = y-1; j >= y+1; j++){
				if(x >= 0 & y >= 0 & x < 10 & y < 10 & cells[x][y] != Cell.SEA) return false;
			}
		}
		return true;
	}
	private boolean checkArea(int x, int y, int decksNumber, boolean direction){
		if(direction){
			for(int i=x; i< x+decksNumber;i++)
				if(!checkCell(i,y)) return false;
		}
		else{
			for(int j=y; j< y+decksNumber;j++)
				if(!checkCell(x,j)) return false;
		}
		return true;
	}
	
	public boolean setShip (int x, int y, int decksNumber, boolean direction){
		boolean result = false;
		if(x >= 0 & y >= 0 & x < 10 & y < 10)
			{
			if(direction){
				if(x + decksNumber < 10 & checkArea(x, y, decksNumber, direction)){
					
					for(int i=x; i< x+decksNumber;i++)
						cells[i][y] = Cell.SHIP;
					printField();
					System.out.println();
					result = true;
				}
			}
			else{
				if(y + decksNumber < 10 & checkArea(x, y, decksNumber, direction)){
					for(int j=y; j< y+decksNumber;j++)
						cells[x][j] = Cell.SHIP;
					result = true;
					printField();
					System.out.println();
				}
			}
		}
		return result;
	}
	
	public boolean checkEnd(){
		boolean end=false;
		int counter=0;
		for (int i=0; i<10;i++){
			for (int j=0;j<10;j++){
				if (cells[i][j]==Cell.SHIP)
					counter++;
			}
		}
		if (counter==0) end=true;
		return end;
	}
	public void deleteShip (int x, int y, int decksNumber, boolean direction){
		if(x >= 0 & y >= 0 & x < 10 & y < 10)
			{
			if(direction){
				if(x + decksNumber < 10)
					for(int i=x; i< x+decksNumber;i++)
						cells[i][y] = Cell.SEA;
			}
			else{
				if(y + decksNumber < 10)
					for(int j=y; j< y+decksNumber;j++)
						cells[x][j] = Cell.SEA;
			}
		}
	}

	public void turnShip (int x, int y, int decksNumber, boolean direction){
		if(x > 0 & y > 0 & x < 10 & y < 10){
			if(!direction){
				if(x + decksNumber < 10)
					deleteShip(x, y, decksNumber, direction);
					for(int i=x; i< x+decksNumber;i++)
						cells[i][y] = Cell.SHIP;
			}
			else{
				if(y + decksNumber < 10)
					deleteShip(x, y, decksNumber, direction);
					for(int j=y; j< y+decksNumber;j++)
						cells[x][j] = Cell.SHIP;
			}
		}
	}
	
	public void clear(){
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				cells[i][j] = Cell.SEA;
	}
	
	public void generateShips(){
		clear();
		
		Random rand = new Random();
		int x = 0;
		int y = 0;
		boolean direction = false;
		
		for(int decksNumber = 4; decksNumber >= 1; decksNumber --){
			for(int shipsNumber = 5 - decksNumber; shipsNumber >=1; shipsNumber--){
				do {
					x = rand.nextInt(10);
					y = rand.nextInt(10);
					direction = rand.nextBoolean();
					System.out.println(x + " " + y);
				} while (!setShip(x, y, decksNumber, direction));
			}
		}
	}
	
	public void printField(){
		char cord=' ';
		System.out.print("\t");
		for(int i=0; i<10;i++){
			cord=(char)(i+(int)'A');
		     System.out.print(cord);
		     System.out.print(' ');
		}
		System.out.println();
		for(int i=0;i<10;i++)
		{
			System.out.print(i+1);
			System.out.print("\t");
			for(int j=0;j<10;j++)
				if(cells[i][j] == Cell.SEA) System.out.print("o ");
				else if(cells[i][j]==Cell.MISS) System.out.print("∙ ");
				else if (cells[i][j] == Cell.STRICKEN) System.out.print("x ");
				else System.out.print("■ ");
			System.out.println();	
		}
	}
	
	public void testFillField(){
		cells[5][4] = Cell.SHIP;
		cells[3][6] = Cell.SHIP;
		cells[5][9] = Cell.SHIP;
		cells[9][6] = Cell.SHIP;
		int i=1;
		for(int j=1;j<5;j++)
			cells[i][j] = Cell.SHIP;
		i=4;
		for(int j=0;j<2;j++)
			cells[i][j] = Cell.SHIP;
		i=8;
		for(int j=8;j<10;j++)
			cells[i][j] = Cell.SHIP;
		int j=1;
		for ( i=7;i<10;i++)
			cells[i][j] = Cell.SHIP;
		 j=4;
		for ( i=8;i<10;i++)
			cells[i][j] = Cell.SHIP;
	     j=8;
		for ( i=1;i<4;i++)
			cells[i][j] = Cell.SHIP;
		
		
	}
	
	public void palub4 ()//расстановка 4-х палубного корабля
	{
		Random r =new Random();
		int i=0;//координата Х
		int j=0;//координата У
		int n=0;//направление корабля
		n=r.nextInt(2);
		i=r.nextInt(6);
		j=r.nextInt(6);
		cells[i][j]=Cell.SHIP;//Расстановка первой палубы
		if(n==0)
		{
			cells[i][j+1]=Cell.SHIP;//Расстановка первой палубы
			cells[i][j+2]=Cell.SHIP;//Расстановка второй палубы
			cells[i][j+3]=Cell.SHIP;//Расстановка третьей палубы
		}
		else
		{
			cells[i+1][j]=Cell.SHIP;//Расстановка первой палубы
			cells[i+2][j]=Cell.SHIP;//Расстановка второй палубы
			cells[i+3][j]=Cell.SHIP;//Расстановка третьей палубы
		}
	}
	public void palub3()//расстановка 4-х палубного корабля

	{
		Random r =new Random();
		boolean t=false;
		int i=0;//координата Х
		int j=0;//координата У
		int n=0;//направление корабля
		while(!t)
		{
			n=r.nextInt(2);
			i=r.nextInt(8);
			j=r.nextInt(8);
			if(n==0)
			{
				if(cells[i][j]==Cell.SEA&&cells[i][j+1]==Cell.SEA&&cells[i][j+2]==Cell.SEA&&//проверка будущего расположения корабля
						((i>0&&cells[i-1][j]==Cell.SEA&&cells[i-1][j+1]==Cell.SEA&&cells[i-1][j+2]==Cell.SEA)||(i==0))&&//проверка координат снизу
						((i<7&&cells[i+1][j]==Cell.SEA&&cells[i+1][j+1]==Cell.SEA&&cells[i+1][j+2]==Cell.SEA)||(i==7))&&//проверка координат сверху
						(((j>0)&&(cells[i][j-1]==Cell.SEA)||(j==0))&&//проверка координат слево
						((j<7)&&(cells[i][j+3]==Cell.SEA)||(j==7))&&//проверка координат справа
						((i<7&&j>0&&cells[i+1][j-1]==Cell.SEA)||(i==7||j==0))&&//проверка координат по диагонали
						((i<7&&j<7&&cells[i+1][j+3]==Cell.SEA)||(i==7||j==7))&&//проверка координат по диагонали
						((i>0&&j>0&&cells[i-1][j-1]==Cell.SEA)||(i==0||j==0))&&//проверка координат по диагонали
						((i>0&&j<7&&cells[i-1][j+3]==Cell.SEA)||(i==0||j==7))))//проверка координат по диагонали
				{
					cells[i][j]=Cell.SHIP;
					cells[i][j+1]=Cell.SHIP;
					cells[i][j+2]=Cell.SHIP;
					t=true;
					}
				else
				{
					t=false;
					}
				}
			else
			{
				if(cells[i][j]==Cell.SEA&&cells[i+1][j]==Cell.SEA&&cells[i+2][j]==Cell.SEA&&//проверка будущего расположения корабля
						((j>0&&cells[i][j-1]==Cell.SEA&&cells[i+1][j-1]==Cell.SEA&&cells[i+2][j-1]==Cell.SEA)||(i==0))&&//проверка координат снизу
						((j<7&&cells[i][j+1]==Cell.SEA&&cells[i+1][j+1]==Cell.SEA&&cells[i+2][j+1]==Cell.SEA)||(i==7))&&//проверка координат сверху
						(((i>0)&&(cells[i-1][j]==Cell.SEA)||(j==0))&&//проверка координат слево
						((i<7)&&(cells[i+3][j]==Cell.SEA)||(j==7))&&//проверка координат справа
						((i<7&&j>0&&cells[i+3][j-1]==Cell.SEA)||(i==7||j==0))&&//проверка координат по диагонали
						((i<7&&j<7&&cells[i+3][j+1]==Cell.SEA)||(i==7||j==7))&&//проверка координат по диагонали
						((i>0&&j>0&&cells[i-1][j-1]==Cell.SEA)||(i==0||j==0))&&//проверка координат по диагонали
						((i>0&&j<7&&cells[i-1][j+1]==Cell.SEA)||(i==0||j==7))))//проверка координат по диагонали
				{
					cells[i][j]=Cell.SHIP;
					cells[i+1][j]=Cell.SHIP;
					cells[i+2][j]=Cell.SHIP;
					t=true;
					}
			else
			{
				t=false;
				}
				}
			}
		}
	public void palub2()
	{
		Random r =new Random();
		boolean t=false;
		int i=0;//координата Х
		int j=0;//координата У
		int n=0;//направление корабля
		while(!t)
		{
			n=r.nextInt(2);
			i=r.nextInt(9);
			j=r.nextInt(9);
			if(n==0)
			{
				if(cells[i][j]==Cell.SEA&&cells[i][j+1]==Cell.SEA&&//проверка будущего расположения корабля
						((i>0&&cells[i-1][j]==Cell.SEA&&cells[i-1][j+1]==Cell.SEA)||(i==0))&&//проверка координат снизу
						((i<7&&cells[i+1][j]==Cell.SEA&&cells[i+1][j+1]==Cell.SEA)||(i==7))&&//проверка координат сверху
						(((j>0)&&(cells[i][j-1]==Cell.SEA)||(j==0))&&//проверка координат слево
						((j<7)&&(cells[i][j+2]==Cell.SEA)||(j==7))&&//проверка координат справа
						((i<7&&j>0&&cells[i+1][j-1]==Cell.SEA)||(i==7||j==0))&&//проверка координат по диагонали
						((i<7&&j<7&&cells[i+1][j+2]==Cell.SEA)||(i==7||j==7))&&//проверка координат по диагонали
						((i>0&&j>0&&cells[i-1][j-1]==Cell.SEA)||(i==0||j==0))&&//проверка координат по диагонали
						((i>0&&j<7&&cells[i-1][j+2]==Cell.SEA)||(i==0||j==7))))//проверка координат по диагонали
				{
					cells[i][j]=Cell.SHIP;
					cells[i][j+1]=Cell.SHIP;
					t=true;
					}
				else
				{
					t=false;
					}
				}
			else
			{
				if(cells[i][j]==Cell.SEA&&cells[i+1][j]==Cell.SEA&&//проверка будущего расположения корабля
						((j>0&&cells[i][j-1]==Cell.SEA&&cells[i+1][j-1]==Cell.SEA)||(i==0))&&//проверка координат снизу
						((j<7&&cells[i][j+1]==Cell.SEA&&cells[i+1][j+1]==Cell.SEA)||(i==7))&&//проверка координат сверху
						(((i>0)&&(cells[i-1][j]==Cell.SEA)||(j==0))&&//проверка координат слево
						((i<7)&&(cells[i+2][j]==Cell.SEA)||(j==7))&&//проверка координат справа
						((i<7&&j>0&&cells[i+2][j-1]==Cell.SEA)||(i==7||j==0))&&//проверка координат по диагонали
						((i<7&&j<7&&cells[i+2][j+1]==Cell.SEA)||(i==7||j==7))&&//проверка координат по диагонали
						((i>0&&j>0&&cells[i-1][j-1]==Cell.SEA)||(i==0||j==0))&&//проверка координат по диагонали
						((i>0&&j<7&&cells[i-1][j+1]==Cell.SEA)||(i==0||j==7))))//проверка координат по диагонали
				{
					cells[i][j]=Cell.SHIP;
					cells[i+1][j]=Cell.SHIP;
					t=true;
					}
			else
			{
				t=false;
				}
				}
			}
	}
	public void palub1()
	{
		Random r =new Random();
		boolean t=false;
		int i=0;//координата Х
		int j=0;//координата У
		int n=0;//направление корабля
		while(!t)
		{
			n=r.nextInt(2);
			i=r.nextInt(10);
			j=r.nextInt(10);
			if(n==0)
			{
				if(cells[i][j]==Cell.SEA&&//проверка будущего расположения корабля
						((i>0&&cells[i-1][j]==Cell.SEA)||(i==0))&&//проверка координат снизу
						((i<7&&cells[i+1][j]==Cell.SEA)||(i==7))&&//проверка координат сверху
						(((j>0)&&(cells[i][j-1]==Cell.SEA)||(j==0))&&//проверка координат слево
						((j<7)&&(cells[i][j+1]==Cell.SEA)||(j==7))&&//проверка координат справа
						((i<7&&j>0&&cells[i+1][j-1]==Cell.SEA)||(i==7||j==0))&&//проверка координат по диагонали
						((i<7&&j<7&&cells[i+1][j+1]==Cell.SEA)||(i==7||j==7))&&//проверка координат по диагонали
						((i>0&&j>0&&cells[i-1][j-1]==Cell.SEA)||(i==0||j==0))&&//проверка координат по диагонали
						((i>0&&j<7&&cells[i-1][j+1]==Cell.SEA)||(i==0||j==7))))//проверка координат по диагонали
				{
					cells[i][j]=Cell.SHIP;
					t=true;
					}
				else
				{
					t=false;
					}
				}
			else
			{
				if(cells[i][j]==Cell.SEA&&//проверка будущего расположения корабля
						((j>0&&cells[i][j-1]==Cell.SEA)||(i==0))&&//проверка координат снизу
						((j<7&&cells[i][j+1]==Cell.SEA)||(i==7))&&//проверка координат сверху
						(((i>0)&&(cells[i-1][j]==Cell.SEA)||(j==0))&&//проверка координат слево
						((i<7)&&(cells[i+1][j]==Cell.SEA)||(j==7))&&//проверка координат справа
						((i<7&&j>0&&cells[i+1][j-1]==Cell.SEA)||(i==7||j==0))&&//проверка координат по диагонали
						((i<7&&j<7&&cells[i+1][j+1]==Cell.SEA)||(i==7||j==7))&&//проверка координат по диагонали
						((i>0&&j>0&&cells[i-1][j-1]==Cell.SEA)||(i==0||j==0))&&//проверка координат по диагонали
						((i>0&&j<7&&cells[i-1][j+1]==Cell.SEA)||(i==0||j==7))))//проверка координат по диагонали
				{
					cells[i][j]=Cell.SHIP;
					t=true;
					}
			else
			{
				t=false;
				}
				}
			}
	}
	
	
	public void fillField()
	{
		palub4();
		palub3();
		palub3();
		palub2();
		palub2();
		palub2();
		palub1();
		palub1();
		palub1();
		palub1();
	}	

	public static void main(String[] args) throws InterruptedException{
		OldField field = new OldField();
		field.setShip(0, 2, 4, true);
		field.printField();
		field.setShip(0, 3, 3, false);
		field.printField();
////		for(int i = 0; i < 10; i++){
//			field.generateShips();
//			field.printField();Thread.sleep(2000);
//			System.out.println();
////		}
	}
}
