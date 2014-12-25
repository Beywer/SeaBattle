package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.resources.Textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ship extends  Actor {

	private Texture tex;
	private boolean direction;
	private int deckNum;
	
	public Ship(int deckNum, boolean direction){
		this.direction = direction;
		this.deckNum = deckNum;

		tex = getShipTexture();
		
		setWidth(tex.getWidth());
		setHeight(tex.getHeight());
	}
	
	public void changeDirection(){
		direction = !direction;
		tex = getShipTexture();
	}
	
	private Texture getShipTexture(){
		if(direction){
			switch(deckNum){
			case 1:
				return Textures.honeDirShip;
			case 2:
				return Textures.htwoDirShip;
			case 3:
				return Textures.hthreeDirShip;
			case 4: 
				return Textures.hfourDirShip;
			default: 
				assert false;
			}
		}else{
			switch(deckNum){
			case 1:
				return Textures.voneDirShip;
			case 2:
				return Textures.vtwoDirShip;
			case 3:
				return Textures.vthreeDirShip;
			case 4: 
				return Textures.vfourDirShip;
			default: 
				assert false;
			}
		}
		assert false;
		return null;
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if(x>=0 &&y >=0 && x < getWidth() && y < getHeight()){
			return this;}
		else
			return super.hit(x, y, touchable);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(direction)
			batch.draw(tex, getX(), getY());
		else
			batch.draw(tex, getX() - getHeight(), getY());
	}

	public boolean isDirection() {
		return direction;
	}

	public int getDeckNuber() {
		return deckNum;
	}
	
	
}
