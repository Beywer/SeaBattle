package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShootState;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.game.TurnToken;
import ru.ssau.seabattle.opponent.AI;
import ru.ssau.seabattle.opponent.Level;

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

public class GameScreen implements Screen {
	
	private Stage stage;
	private Table background;
	private Skin skin;
	private Texture sea, miss, inj, ship, back;
	private SpriteBatch batch;
	
	private SeaBatGame game;
	private int xOffset;
	private int yOffset;

	/**
	 * Создает экран, где будет оботбражаться состояние игры и пользователь сможет делать ходы.
	 * @param game Игра, состояние которой будет отображаться.
	 */
	public GameScreen(SeaBatGame game){
		this.game = game;
		
		AI ai = new AI(game, Level.LOW);
		Thread thr = new Thread(ai);
		thr.setDaemon(true);
		thr.start();
	}	
	
	@Override
	public void show() {
		stage = new Stage();
		
		stage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				shoot(x, y);
			}
		});
		
		batch = new SpriteBatch();
		
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
		background = new Table();
		background.setBackground(skin.getDrawable("back"));
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		stage.addActor(background);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		stage.act();
		stage.draw();	
		
		drawFields();
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
	/**
	 * Рисует свое и врежеское поля.
	 */
	private void drawFields(){
		batch.begin();
		
		Field field = game.getMyField();
		CellState state;

		xOffset = 50;
		yOffset = 420;
		
		for(int j = 0; j < 10; j ++){
			for(int i = 0; i < 10; i ++){
				state = field.getCell(i, j).getState();
				switch(state){
				case SEA:
					batch.draw(sea, (sea.getWidth()+4)*i + xOffset, yOffset - (sea.getHeight() + 4)*j ); 
					break;
				case INJURED:
					batch.draw(inj, (inj.getWidth()+4)*i + xOffset, yOffset - (inj.getHeight() + 4)*j ); 
					break;
				case MISS:
					batch.draw(miss, (miss.getWidth()+4)*i + xOffset, yOffset - (miss.getHeight() + 4)*j ); 
					break;
				case SHIP:
					batch.draw(ship, (ship.getWidth()+4)*i + xOffset, yOffset - (ship.getHeight() + 4)*j ); 
					break;
				}
			}
		}
		
		field = game.getOpponentField();
		
		xOffset = 400;
		
		for(int j = 0; j < 10; j ++){
			for(int i = 0; i < 10; i ++){
				state = field.getCell(i, j).getState();
				switch(state){
				case SEA:
					batch.draw(sea, (sea.getWidth()+4)*i + xOffset, yOffset - (sea.getHeight() + 4)*j ); 
					break;
				case INJURED:
					batch.draw(inj, (inj.getWidth()+4)*i + xOffset, yOffset - (inj.getHeight() + 4)*j ); 
					break;
				case MISS:
					batch.draw(miss, (miss.getWidth()+4)*i + xOffset, yOffset - (miss.getHeight() + 4)*j ); 
					break;
				case SHIP:
					batch.draw(sea, (sea.getWidth()+4)*i + xOffset, yOffset - (sea.getHeight() + 4)*j ); 
					break;
				}
			}
		}

		batch.end();
	}
	
	private void shoot(float x, float y){
		
		if(game.getTurnToken() == TurnToken.MY){
			int fx = (int)((x - xOffset)/30);
			int fy = (int)((yOffset - y + sea.getHeight())/30);
			if(fx >= 0 && fy >=0 && fx < 10 && fy < 10)
				game.myShoot(fx, fy);
//			yourTurn.setText("Ваш ход : " + (char)(fx + 'A') + " "+ (fy+1));
		}
	}
}
