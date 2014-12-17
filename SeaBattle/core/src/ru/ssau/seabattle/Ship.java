package ru.ssau.seabattle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ship extends  Actor {

	Texture tex;
	
	public Ship(int deckNum, boolean direction){
		tex = new Texture("field/ship4.png");
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if(x>=0 &&y >=0 && x < getWidth() && y < getHeight())
			return this;
		else
			return super.hit(x, y, touchable);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(tex, getX(), getY());
	}
	
}
