package ru.ssau.seabattle.ui;

import java.util.ArrayList;

import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.Ship;
import ru.ssau.seabattle.game.GameSettings;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.game.TurnToken;
import ru.ssau.seabattle.opponent.AI;
import ru.ssau.seabattle.opponent.Level;
import ru.ssau.seabattle.opponent.Opponent;
import ru.ssau.seabattle.opponent.net.Server;
import ru.ssau.seabattle.resources.Textures;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameController implements Screen {
	
	private Stage stage;
	private SpriteBatch batch;
	private Table background;
	private Skin skin;
	private SeaBatGame game;
	private TextButton save, menu;
	private Label yourTurn,oppTurn,youWin, youLose;
	private BitmapFont font, smallFont;
	
	private int xFieldOffset = 20, yFieldOffset = 120, fieldsOffset = 350;
	
	private Field playerField;
	private Level level;
	private boolean clientWaiting;
	private Opponent opponent;
	
	public GameController(GameSettings settings, Field playerField, Server server) {
		this.level = settings.getLevel();
		this.playerField = playerField;
		game = new SeaBatGame();
		game.setMyField(playerField);
		if(settings.isSingleGame()) {
			opponent = new AI(game, level);
			Thread thr = new Thread((Runnable) opponent);	
			thr.start();
		}else if (settings.isServerGame()){
			opponent = server;
			server.start();
		}
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		drawHeader();
		
		stage.act(delta);
		stage.draw();
		
		drawFields();
	}
	
	@Override
	public void show() {
		stage = new Stage();
		skinInit();
		
		background = new Table();
		background.setBackground(skin.getDrawable("back"));
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batch = new SpriteBatch();
		
		fieldClickInit();
		
		stage.addActor(background);		
		labelInit();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	private void fieldClickInit(){
		stage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(game.getTurnToken() == TurnToken.MY &&
						x>xFieldOffset+fieldsOffset+30 && x <xFieldOffset+fieldsOffset+330 &&
						y>yFieldOffset&&y<yFieldOffset+300){
					x = x - xFieldOffset - fieldsOffset - 30;
					y = 300 - y + yFieldOffset;
					int coordX = (int)x/30;
					int coordY = (int)y/30;
					game.myShoot(coordX, coordY);
				}
			}
		});
	}
	
	private void labelInit(){
		yourTurn = new Label("Ваш ход", skin);
		oppTurn = new Label("Ход противника", skin);
		youLose = new Label("Вы проиграли!", skin);
		youWin = new Label("Вы выиграли!", skin);
		
		yourTurn.setVisible(false);
		oppTurn.setVisible(false);
		youLose.setVisible(false);
		youWin.setVisible(false);
		
		yourTurn.setPosition(500 - yourTurn.getWidth()/2, 500);
		oppTurn.setPosition(500 - oppTurn.getWidth()/2, 500);
		youLose.setPosition(500 - youLose.getWidth()/2, 500);
		youWin.setPosition(500 - youWin.getWidth()/2, 500);
		
		stage.addActor(yourTurn);
		stage.addActor(oppTurn);
		stage.addActor(youLose);
		stage.addActor(youWin);		
	}
	
	private void skinInit(){
		skin = new Skin();
		skin.add("back", Textures.background);
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		smallFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"));
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		skin.add("default", labelStyle);
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

	private void drawFields(){
		batch.begin();

		batch.draw(Textures.field, xFieldOffset, yFieldOffset);
		batch.draw(Textures.field, xFieldOffset + fieldsOffset, yFieldOffset);
		
		//Поле игрока
		ArrayList<Ship> playerShips = playerField.getShips();
		for(Ship ship : playerShips){
			int x = ship.getX();
			int y = ship.getY();
			int decksNum = ship.getDecksNumber();
			Texture tex = null;
			x = xFieldOffset + 30*x + 30;
			y = yFieldOffset - y*30 + 300 - 30;
			if(ship.isDirection()){
				if(decksNum == 1) tex = Textures.honeDirShip;
				if(decksNum == 2) tex = Textures.htwoDirShip;
				if(decksNum == 3) tex = Textures.hthreeDirShip;
				if(decksNum == 4) tex = Textures.hfourDirShip;
			}
			else{
				if(decksNum == 1) {tex = Textures.voneDirShip; y-=30*0;}
				if(decksNum == 2) {tex = Textures.vtwoDirShip; y-=30*1;}
				if(decksNum == 3) {tex = Textures.vthreeDirShip; y-=30*2;}
				if(decksNum == 4) {tex = Textures.vfourDirShip; y-=30*3;}
			}
			if(tex != null)
				batch.draw(tex, x, y);
		}
		
		
		Field oppField = game.getOpponentField();
		for(int i =0; i < 10;i++){
			for(int j = 0; j < 10; j++){
				if(playerField.getCell(i, j).getState() == CellState.MISS)
					batch.draw(Textures.miss, xFieldOffset + 30*i + 30, yFieldOffset - j*30 + 300 - 30);
				if(playerField.getCell(i, j).getState() == CellState.INJURED)
					batch.draw(Textures.inj, xFieldOffset + 30*i + 30, yFieldOffset - j*30 + 300 - 30);
				if(oppField != null){
					if(oppField.getCell(i, j).getState() == CellState.MISS)
						batch.draw(Textures.miss, xFieldOffset + 30*i + 30 + fieldsOffset, yFieldOffset - j*30 + 300 - 30);
					if(oppField.getCell(i, j).getState() == CellState.INJURED)
						batch.draw(Textures.inj, xFieldOffset + 30*i + 30 + fieldsOffset, yFieldOffset - j*30 + 300 - 30);
				}
			}
		}
		
		batch.end();
	}
	
	private void drawHeader(){
		if(game.getTurnToken() == TurnToken.MY){
			yourTurn.setVisible(true);
			oppTurn.setVisible(false);
		}else{
			yourTurn.setVisible(false);
			oppTurn.setVisible(true);
		}
		if(game.isGameEnded()){
			yourTurn.setVisible(false);
			oppTurn.setVisible(false);
			if(game.getWinner() == TurnToken.MY) {
				youWin.setVisible(true);
			}else if(game.getWinner() == TurnToken.OPPONENT){
				youLose.setVisible(true);
			}
		}
	}
	
}
