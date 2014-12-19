package ru.ssau.seabattle;

import ru.ssau.seabattle.game.SeaBatGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameScreen implements Screen {
	
	private Stage stage;
	private Table background;
	private Skin skin;
	private Texture sea, miss, inj, ship, back;
	
	private SeaBatGame game;

	/**
	 * Создает экран, где будет оботбражаться состояние игры и пользователь сможет делать ходы.
	 * @param game Игра, состояние которой будет отображаться.
	 */
	public GameScreen(SeaBatGame game){
		this.game = game;
	}	
	
	@Override
	public void show() {
		stage = new Stage();
		
		//TEXTURES
		sea = new Texture("field/sea.png");
		inj = new Texture("field/inj.png");
		ship = new Texture("field/ship.png");
		miss = new Texture("field/miss.png");
		back = new Texture("back.png");
		
		//SKIN
		skin = new Skin();
		skin.add("sea", sea);
		skin.add("inj", inj);
		skin.add("ship", ship);
		skin.add("miss", miss);
		skin.add("back", back);

		//BACKGROUND
		background = new Table(skin);
		background.setBackground(skin.getDrawable("back"));
		
		
		stage.addActor(background);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		stage.act();
		stage.draw();			
		
	}
	
	@Override
	public void resize(int width, int height) {
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void hide() {
	}
	
	@Override
	public void dispose() {
	}
	
	@Override
	public void pause() {
	}
	
}
