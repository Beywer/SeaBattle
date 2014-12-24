package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FieldCreator implements Screen {

	private Stage stage;
	private Skin skin;
	private CellState cells[][];
	private SpriteBatch batch;
	private Table backgrond;
	
	private int count4 , count3, count2, count1;
	private Game game;
	private GameType type;
	
	
	public FieldCreator(ru.ssau.seabattle.game.Game game, GameType type) {
		count4 = 1;
		count3 = 2;
		count2 = 3;
		count1 = 4;
		
		this.game = game;
		this.type = type;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		stage.act();
		stage.draw();
		batch.begin();
		
		batch.draw(new Texture("field/sea.png"), 20, 20);
		
		batch.end();
	}

	@Override
	public void show() {
		stage = new Stage();
		
		skin = new Skin();
		skin.add("sea", new Texture("field/sea.png"));
		skin.add("inj", new Texture("field/inj.png"));
		skin.add("miss", new Texture("field/miss.png"));
		skin.add("ship", new Texture("field/ship.png"));
		skin.add("ship1", new Texture("field/ship1.png"));
		skin.add("ship2", new Texture("field/ship2.png"));
		skin.add("ship3", new Texture("field/ship3.png"));
		skin.add("ship4", new Texture("field/ship4.png"));
		skin.add("back", new Texture("back.png"));
		
		cells = new CellState[10][10];
		for(int i =0; i < 10; i++){
			for(int j =0; j < 10; j++){
				cells[i][j] = CellState.SEA;
			}
		}

		batch = new SpriteBatch();
		
		//Backgrond
		backgrond = new Table(skin);
		backgrond.setBackground(skin.getDrawable("back"));
		backgrond.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		final Ship ship = new Ship(4, true);
		ship.setPosition(20, 20);
		
		stage.addActor(backgrond);
		
//		stage.addActor(ship);
		
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
	}
}
