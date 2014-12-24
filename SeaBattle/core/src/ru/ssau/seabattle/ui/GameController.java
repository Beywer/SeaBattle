package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.ShootState;
import ru.ssau.seabattle.opponent.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameController implements Screen {
	
	private Stage stage;
	private SpriteBatch batch;
	private Table background;
	private Skin skin;
	private ru.ssau.seabattle.game.Game game;
	private Texture sea,inj,miss,ship;
	private Label yourTurn,oppTurn;
	private BitmapFont font;
	
	private GameType type;
	private Level level;
	private int xOffset, yOffset;
	private boolean myTurn;
	
	public GameController(GameType type, Level level, ru.ssau.seabattle.game.Game game){
		this.level = level;
		this.type = type;
		this.game = game;
	}
	
	
	@Override
	public void show() {
		
		batch = new SpriteBatch();
		
		stage = new Stage();
		
		
		skin = new Skin();
		skin.add("back", new Texture("back.png"));
		
		sea = new Texture("field/sea.png");	
		inj = new Texture("field/inj.png");		
		miss = new Texture("field/miss.png");		
		ship = new Texture("field/ship.png");			
		
		
		background = new Table();
		background.setBackground(skin.getDrawable("back"));
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		myTurn = true;		
		
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		yourTurn = new Label("Ваш ход : ",	labelStyle);
		oppTurn = new Label("Ход противника : ", labelStyle);
		
		stage.addActor(background);
		stage.addActor(yourTurn);
		yourTurn.setPosition(200, 500);
		stage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				makeTurn(x, y);
			}
		});
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		stage.act();
		stage.draw();			
		
//		batch.begin();
//		
//		Field field = game.getMyField();
//		CellState state;
//
//		xOffset = 50;
//		yOffset = 420;
//		
//		for(int j = 0; j < 10; j ++){
//			for(int i = 0; i < 10; i ++){
//				state = field.getCell(i, j).getState();
//				switch(state){
//				case SEA:
//					batch.draw(sea, (sea.getWidth()+4)*i + xOffset, yOffset - (sea.getHeight() + 4)*j ); 
//					break;
//				case INJURED:
//					batch.draw(inj, (inj.getWidth()+4)*i + xOffset, yOffset - (inj.getHeight() + 4)*j ); 
//					break;
//				case MISS:
//					batch.draw(miss, (miss.getWidth()+4)*i + xOffset, yOffset - (miss.getHeight() + 4)*j ); 
//					break;
//				case SHIP:
//					batch.draw(ship, (ship.getWidth()+4)*i + xOffset, yOffset - (ship.getHeight() + 4)*j ); 
//					break;
//				}
//			}
//		}
//		
//		field = game.getOpponentField();
//		
//		xOffset = 400;
//		
//		for(int j = 0; j < 10; j ++){
//			for(int i = 0; i < 10; i ++){
//				state = field.getCell(i, j).getState();
//				switch(state){
//				case SEA:
//					batch.draw(sea, (sea.getWidth()+4)*i + xOffset, yOffset - (sea.getHeight() + 4)*j ); 
//					break;
//				case INJURED:
//					batch.draw(inj, (inj.getWidth()+4)*i + xOffset, yOffset - (inj.getHeight() + 4)*j ); 
//					break;
//				case MISS:
//					batch.draw(miss, (miss.getWidth()+4)*i + xOffset, yOffset - (miss.getHeight() + 4)*j ); 
//					break;
//				case SHIP:
//					batch.draw(sea, (sea.getWidth()+4)*i + xOffset, yOffset - (sea.getHeight() + 4)*j ); 
//					break;
//				}
//			}
//		}
//
//		batch.end();
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
	
	private void makeTurn(float x, float y){
		if(myTurn){
			int fx = (int)((x - xOffset)/30);
			int fy = (int)((yOffset - y + sea.getHeight())/30);
			ShootState state = ShootState.MISS;
			if(fx >= 0 && fy >=0 && fx < 10 && fy < 10)
				state = game.myShoot(fx, fy);
			yourTurn.setText("Ваш ход : " + (char)(fx + 'A') + " "+ (fy+1));
			if(state == ShootState.INJURED || state == ShootState.DEAD)
				myTurn = true;
			else  
				myTurn = false;
			if(!myTurn){
				state = game.opponentShoot();
				while(state == ShootState.INJURED || state == ShootState.DEAD){
					state = game.opponentShoot();
				}
				myTurn = true;
			}
		}
	}
}
