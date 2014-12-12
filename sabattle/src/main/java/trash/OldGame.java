package trash;
import java.io.*;
import java.util.Random;

import trash.OldField.Cell;

public class OldGame{

	public void easyModeStep(OldField field){
		int x=0;
		int y=0;
		Random rand = new Random();
		x = rand.nextInt(10);
		y = rand.nextInt(10);
		//x=1;
		//y=1;
		char y1=' ';
		y1=(char)(y+(int)'A');
		int x1=x+1;
		System.out.println("Ход соперника:"+y1 +""+x1);
		if (field.cells[x][y]==Cell.SHIP){
			field.cells[x][y] = Cell.STRICKEN;
			System.out.println("Результат: Ранен");
		}
		else {
			field.cells[x][y] = Cell.MISS;
			System.out.println("Результат: Мимо");
		}
			
	}
	
	public void middleModeStep(OldField field){
		int x=0;
		int y=0;
		boolean found=false;
		Random rand = new Random();
		if (!found){
		  x = rand.nextInt(10);
		  y = rand.nextInt(10);
		  //x=1;
		  //y=1;
		  System.out.println("Ход соперника:"+ x+":"+y);
		  if (field.cells[x][y]==Cell.SHIP){
			  field.cells[x][y] = Cell.STRICKEN;
			  found=true;
			  System.out.println("Результат: Ранен");
		  }
		  else {
			  //field.cells[x][y] = Cell.MISS;
			  System.out.println("Результат: Мимо");
		  }
		}
		else{
			System.out.println("Ход соперника:"+ x+":"+y);
		}
	}
	
	public static int[] coordinate(String step){
		step=step.trim();
		step=step.toUpperCase();
		char[] step1=step.toCharArray();
		int[] coord = new int[2];
		int step12=5;
		if(step1.length==3){step12 = Character.getNumericValue(step1[2]);}
		int step11 = Character.getNumericValue(step1[1]);
		
		if (step1[0]=='A'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=0;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=0;}
		}
		else if (step1[0]=='B'){
			for(int i=1; i<10;i++){
				if (step11==i){
					coord[0]=i-1; 
					coord[1]=1;}
			}
			 if(step11==1 && step12==0){coord[0]=9; coord[1]=1;}
		}
		else if (step1[0]=='C'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=2;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=2;}
		}
		else if (step1[0]=='D'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=3;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=3;}
		}
		else if (step1[0]=='E'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=4;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=4;}
		}
		else if (step1[0]=='F'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=5;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=5;}
		}
		else if (step1[0]=='G'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=6;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=6;}
		}
		else if (step1[0]=='H'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=7;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=7;}
		}
		else if (step1[0]=='I'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=8;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=8;}
		}
		else if (step1[0]=='J'){
			for(int i=1; i<10;i++){
				if (step11==i){coord[0]=i-1; coord[1]=9;}
			}
			if(step11==1 && step12==0){coord[0]=9; coord[1]=9;}
		}
		return coord;
	}
	
	public void step(OldField field) throws IOException{
		DataInputStream dis = new DataInputStream(System.in);
		System.out.println("Ваш ход:");
		String step=dis.readLine();
		int[] coord=OldGame.coordinate(step);
		int x=0;
		int y=0;
		x=coord[0];
		y=coord[1];
		if (field.cells[x][y]==Cell.SHIP){
			field.cells[x][y] = Cell.STRICKEN;
			System.out.println("Результат: Ранен");
		}
		else {
			field.cells[x][y] = Cell.MISS;
			System.out.println("Результат: Мимо");
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		OldField yourField = new OldField();
		OldField anotherField = new OldField();
		
		
		yourField.clear();
		yourField.fillField();
		anotherField.fillField();
		System.out.println("Your field");
		yourField.printField();
		System.out.println("--------------------------");
		System.out.println("Another field");
		anotherField.printField();
		OldGame firstGame=new OldGame();
		boolean win=false;
		boolean gameover=false;
		while(!win && !gameover){
			System.out.println("--------------------------");
		firstGame.easyModeStep(yourField);
		System.out.println("Your field");
		yourField.printField();
		firstGame.step(anotherField);
		System.out.println("Another field");
		anotherField.printField();
		win=anotherField.checkEnd();
		gameover=yourField.checkEnd();
		System.out.println("--------------------------");
		}
	}
	
	
	
}
