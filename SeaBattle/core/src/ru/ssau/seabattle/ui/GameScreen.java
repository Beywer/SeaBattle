package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.Ship;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.game.TurnToken;
import ru.ssau.seabattle.opponent.AI;
import ru.ssau.seabattle.resources.Textures;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScreen implements Screen {
	
	private Stage stage;
	private Skin skin;
	private ScrollPane history;
	private SpriteBatch batch;
	private BitmapFont smallFont, font;
	private List list;
	private Thread opponent;
	
	private Label message;
	private TextButton mainMenu;
	
	private Field my,opp;
	
	private SeaBatGame game;
	private int xFieldoffset = 80;
	private int yFieldOffset = 120;

	/**
	 * Создает экран, где будет оботбражаться состояние игры и пользователь сможет делать ходы.
	 * @param game Игра, состояние которой будет отображаться.
	 */
	public GameScreen(SeaBatGame game, Field field1, Field field2){
		this.game = game;
		my = field1;
		opp = field2;
	}	
	
	public GameScreen(SeaBatGame game, boolean singleGame, boolean serverGame) {
		this.game = game;
	}

	public GameScreen(SeaBatGame game, Field field1, Field field2, Thread opponent) {
		this.game = game;
		my = field1;
		opp = field2;
		this.opponent = opponent;
	}

	@Override
	public void show() {
		stage = new Stage();
		skin = new Skin();
		skin.add("empty", new Texture("pane/empty.png"));
		skin.add("selection", new Texture("pane/select.png"));
		skin.add("hScrollKnob", new Texture("pane/horKnob.png"));
		skin.add("vScrollKnob", new Texture("pane/verKnob.png"));
		skin.add("paneBack", new Texture("pane/back.png"));
		skin.add("but_up", Textures.but_up);
		skin.add("but_d", Textures.but_d);
		batch = new SpriteBatch();
		
		smallFont= new BitmapFont(Gdx.files.internal("font/butFont.fnt"));
		System.out.println(game);
		System.out.println(game.getMyField());
		Object arr[] = game.getMyField().getShips().toArray();
		for(int i = 0; i < arr.length; i++){
			Ship ship = (Ship) arr[i];
			ShipActor sh = new ShipActor(ship.getDecksNumber(), ship.isDirection());
			sh.setxCoord(ship.getX());
			sh.setyCoord(ship.getY());
			sh.debug();
			setShipOnField(sh);
			stage.addActor(sh);
		}
		
		ScrollPaneStyle paneStyle = new ScrollPaneStyle(skin.getDrawable("empty"), 
				skin.getDrawable("empty"), 
				skin.getDrawable("hScrollKnob"), 
				skin.getDrawable("empty"), 
				skin.getDrawable("vScrollKnob"));
		ListStyle listStyle = new ListStyle(smallFont, Color.YELLOW, Color.BLACK, skin.getDrawable("empty"));
		list = new List<>(listStyle);
		history = new ScrollPane(list, paneStyle);
		history.setBounds(750, 150, 200, 300);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("but_up");
		textButtonStyle.down = skin.getDrawable("but_d");
		textButtonStyle.font = smallFont;
		textButtonStyle.fontColor = Color.BLACK;
		
		mainMenu = new TextButton("Возврат в меню", textButtonStyle);
		mainMenu.setPosition(30, 40);
		mainMenu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				opponent.interrupt();
				game = null;
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				super.clicked(event, x, y);
			}
		});
		
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		message = new Label("", labelStyle);
		message.setPosition(250, 500);
		
		stage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				shoot(x, y);
			}
		});
		stage.addActor(mainMenu);		
		stage.addActor(message);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		

//		list.setItems(game.getHist());
		if(game.getWinner()!= null)
			if(game.getWinner() == TurnToken.MY) message.setText("Вы выиграли !!!");
			else message.setText("Вы проиграли !!!");
		
		batch.begin();
		batch.draw(Textures.background, 0, 0);
		
		batch.draw(Textures.field,xFieldoffset,yFieldOffset);
		batch.draw(Textures.field,xFieldoffset + 400,yFieldOffset);
		batch.end();
		
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
		
		
		//my
		Field field = game.getMyField();
		for(int j =0; j < 10; j++){
			for(int i = 0; i< 10; i++){
				if(my.getCell(i, j).getState() == CellState.MISS)
					batch.draw(Textures.miss, 30*i+xFieldoffset+30, 270 - 30*j+yFieldOffset);
				if(my.getCell(i, j).getState() == CellState.INJURED)
					batch.draw(Textures.inj, 30*i+xFieldoffset+30, 270 - 30*j+yFieldOffset);
			}
		}
		field = game.getOpponentField();
		for(int j =0; j < 10; j++){
			for(int i = 0; i< 10; i++){
				if(opp.getCell(i, j).getState() == CellState.MISS)
					batch.draw(Textures.miss, 30*i+xFieldoffset+30+ 400, 270 - 30*j+yFieldOffset);
				if(opp.getCell(i, j).getState() == CellState.INJURED)
					batch.draw(Textures.inj, 30*i+xFieldoffset+30+400, 270 - 30*j+yFieldOffset);
			}
		}
		
		batch.end();
	}

	private void setShipOnField(ShipActor ship){

		if(ship.isDirection()){
			ship.setX(ship.getxCoord()*30 + xFieldoffset + 30);
			ship.setY(270 - ship.getyCoord()*30 + yFieldOffset);
		}
		else{
			//Ты тупой. Он в логике ставит по верхней точке(направление вниз), а рисует по нижней вверх ДНАРЬ!!!!!
			ship.setX((ship.getxCoord()+1)*30 + xFieldoffset);
			ship.setY(300 - ship.getyCoord()*30 + yFieldOffset - ship.getHeight());
		}
	}
	
	private void shoot(float x, float y){
		System.out.print(x + " " + y);
//		if(x >= 420 + 30 && y >= 120 && x<= 750 && y<= 420)
		if(x >= xFieldoffset +400 + 30 && y >= yFieldOffset && x<=xFieldoffset +400 + 330 && y <= yFieldOffset + 300)
		if(!game.isGameEnded() && game.getTurnToken() == TurnToken.MY){
			int fx = (int)((x - xFieldoffset - 430)/30);
			int fy = 9 - (int)((y - yFieldOffset)/30);
			if(fx >= 0 && fy >=0 && fx < 10 && fy < 10)
				game.myShoot(fx, fy);
		}
	}
}
