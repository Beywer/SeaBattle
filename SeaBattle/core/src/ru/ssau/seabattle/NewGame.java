package ru.ssau.seabattle;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.game.OLDSingleGame;
import ru.ssau.seabattle.opponent.OLDAI;
import ru.ssau.seabattle.opponent.Level;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class NewGame implements Screen {

	private Stage stage;
	private Skin skin;
	private TweenManager tweenManager;
	private Table modeTable, levelTable, netTable;
	private ScrollPane modePane, levelPane, netPane;
	private BitmapFont font;
	private Label label;
	private BitmapFont butFont;
	private TextButton butNewGame, butBack;
	
	private enum Net{SERVER, CLIENT};
	private Level level;
	private GameType type;
	private Net net;
	private ru.ssau.seabattle.game.Game game;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		
		//Stage and skin
		stage = new Stage();
		skin = new Skin();
		skin.add("hScroll", new Texture("pane/hScroll.png"));
		skin.add("vScroll", new Texture("pane/vScroll.png"));
		skin.add("hScrollKnob", new Texture("pane/hScrollKnob.png"));
		skin.add("vScrollKnob", new Texture("pane/vScrollKnob.png"));
		skin.add("paneBack", new Texture("pane/paneBack.png"));
		skin.add("back", new Texture("back.png"));
		skin.add("but_up", new Texture("button/but_up.png"));
		skin.add("but_d", new Texture("button/but_d.png"));
		
		//FONT
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		butFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"), false);//Здесь false-y ось нарпавлена вверх
		
		
		//Background
		Table background = new Table(skin);
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setBackground(skin.getDrawable("back"));

		//HEADERS
		//HEADERS
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		label = new Label("Новая игра", labelStyle);
		//modeHeader
		LabelStyle labelStyleSmallFont = new LabelStyle(butFont, Color.WHITE);
		Label modeHeader = new Label("Режим игры", labelStyleSmallFont);
		modeHeader.setColor(Color.WHITE);
		//levelHeader
		Label levelHeader = new Label("Сложность игры", labelStyleSmallFont);
		//netHeader
		Label netHeader = new Label("Создать/подключиться", labelStyleSmallFont);

		//PANES
		ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle(
				skin.getDrawable("paneBack"),
				skin.getDrawable("hScroll"),
				skin.getDrawable("hScrollKnob"),
				skin.getDrawable("vScroll"),
				skin.getDrawable("vScrollKnob"));
		//modePane
		ListStyle listStyle = new ListStyle(butFont, Color.RED, Color.BLACK, skin.getDrawable("hScroll"));
		List<String> modeList = new List<String>(listStyle);
		modeList.setItems(new String[]{" Одиночная ","  Сетевая"});
		modeList.setSelectedIndex(-1);
		modePane = new ScrollPane(modeList, scrollPaneStyle);
		//LevelPane
		List<String> levellist = new List<String>(listStyle);
		levellist.setItems(new String[]{"     Легкий","    Средний","   Сложный"});
		levelPane = new ScrollPane(levellist, scrollPaneStyle);
		//netPane
		List<String> netList = new List<String>(listStyle);
		netList.setItems(new String[]{"Созадть","Подключиться"});
		netPane = new ScrollPane(netList, scrollPaneStyle);
		
		
		//PANE CLICK
		level = Level.MIDDLE;
		type = GameType.SINGLE;
		net = Net.SERVER;
		
		
		
		//BUTTONS
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.fontColor = Color.BLACK;
		textButtonStyle.up = skin.getDrawable("but_up");
		textButtonStyle.down = skin.getDrawable("but_d");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.font = butFont;
		//newGame
		butNewGame = new TextButton("Новая игра", textButtonStyle);
		butNewGame.pad(2, 0, 15, 0);
		butNewGame.setBounds(0, 0, 330, 55);
		
		butNewGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				createGame();
//				dispose();
				hide();
//				((Game)Gdx.app.getApplicationListener()).setScreen(new FieldCreator(null,null));
				((Game)Gdx.app.getApplicationListener()).setScreen(new GameController(type, level, game));
			}
		});
		
		//back
		butBack = new TextButton("Назад", textButtonStyle);
		butBack.pad(2, 0, 15, 0);
		butBack.setBounds(0, 0, 330, 55);
		butBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());  
			}
		});
		
		//TABLES
		//modeTable
		modeTable = new Table(skin);
		modeTable.add(modeHeader);
		modeTable.row();
		modeTable.add(modePane).fill();
		modeTable.setBounds(0, 0, 250, 200);
		//levelTable
		levelTable = new Table(skin);
		levelTable.add(levelHeader);
		levelTable.row();
		levelTable.add(levelPane).fill();
		levelTable.setBounds(0, 0, 250, 250);
		//netTable
		netTable = new Table(skin);
		netTable.add(netHeader);
		netTable.row();
		netTable.add(netPane).fill();
		netTable.setBounds(0, 0, 250, 250);
		
		stage.addActor(background);
		
		stage.addActor(levelTable);
		levelTable.setPosition(300, 225);
		
		stage.addActor(label);
		label.setPosition(350, 500);
		
		stage.addActor(modeTable);
		modeTable.setPosition(20, 250);
		
		stage.addActor(netTable);
		netTable.setPosition(650, 225); 
		
		stage.addActor(butNewGame);
		butNewGame.setPosition(20, 20);
		
		stage.addActor(butBack);
		butBack.setPosition(600, 20);
		
		tweenManager = new TweenManager();
		
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
		font.dispose();
		butFont.dispose();
	}
	
	private void createGame(){
		switch(type)
		{
		case SINGLE:
			createSingleGame();
			break;
		case NET:
			break;
		default : assert false;
		}
	}
	
	private void createSingleGame(){
		Field myField, opponentField;
		myField = new Field();
		myField.generate();
		opponentField = new Field();
		opponentField.generate();
		OLDAI opponent = new OLDAI(level, myField);
		game = new OLDSingleGame(opponent, myField, opponentField);
	}
}
