package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.game.GameSettings;
import ru.ssau.seabattle.opponent.Level;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.TweenUtils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.TouchableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	
	private Image  modeBack , netBack;

	private boolean netSelected,singleSelected;	
	private Level level;
	private boolean clientSelected,serverSelected;
	
	public NewGame(){
		netSelected = false;
		singleSelected = false;
		clientSelected = false;
		serverSelected = false;
		level = null;
	}
	
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
		
		skin.add("empty", new Texture("pane/empty.png"));
		skin.add("selection", new Texture("pane/select.png"));
		skin.add("hScrollKnob", new Texture("pane/horKnob.png"));
		skin.add("vScrollKnob", new Texture("pane/verKnob.png"));
		skin.add("paneBack", new Texture("pane/back.png"));		
		skin.add("back", new Texture("back.png"));
		skin.add("but_up", new Texture("button/but_up.png"));
		skin.add("but_d", new Texture("button/but_d.png"));
		
		//FONT
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		butFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"), false);//Здесь false-y ось нарпавлена вверх
		
		modeBack = new Image(skin.getPatch("paneBack"));
		netBack = new Image(skin.getPatch("paneBack"));
		
		//Background
		Table background = new Table(skin);
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setBackground(skin.getDrawable("back"));

		//HEADERS
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		label = new Label("Новая игра", labelStyle);
		//modeHeader
		LabelStyle labelStyleSmallFont = new LabelStyle(butFont, Color.YELLOW);
		Label modeHeader = new Label("Режим игры", labelStyleSmallFont);
		modeHeader.setColor(Color.WHITE);
		//levelHeader
		Label levelHeader = new Label("Сложность игры", labelStyleSmallFont);
		//netHeader
		Label netHeader = new Label("Создать/подключиться", labelStyleSmallFont);

		//PANES
		ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle(
				skin.getDrawable("empty"),
				skin.getDrawable("empty"),
				skin.getDrawable("hScrollKnob"),
				skin.getDrawable("empty"),
				skin.getDrawable("vScrollKnob"));
		//modePane
		ListStyle listStyle = new ListStyle(butFont, Color.WHITE, Color.BLACK, skin.getDrawable("empty"));
		final List<String> modeList = new List<String>(listStyle);
		modeList.setItems(new String[]{" Одиночная ","  Сетевая"});
		modeList.setSelectedIndex(-1);
		modePane = new ScrollPane(modeList, scrollPaneStyle);
		//LevelPane
		final List<String> levellist = new List<String>(listStyle);
		levellist.setItems(new String[]{"     Легкий","    Средний","   Сложный"});
		levellist.setSelectedIndex(-1);
		levelPane = new ScrollPane(levellist, scrollPaneStyle);
		//netPane
		final List<String> netList = new List<String>(listStyle);
		netList.setItems(new String[]{"         Создать","     Подключиться"});
		netList.setSelectedIndex(-1);
		netPane = new ScrollPane(netList, scrollPaneStyle);
		
		
		//PANE CLICK
		//modePane
		modePane.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!stage.getActors().contains(netBack, true)){
					stage.addActor(netBack);
					Tween.set(netBack, ActorAccessor.ALPHA).target(0).start(tweenManager);
					Tween.to(netBack, ActorAccessor.ALPHA, 0.15f).target(1).start(tweenManager);
				}
				String mode = modeList.getSelected();
				switch(mode){
					case " Одиночная " :
						netSelected = false;
						singleSelected = true;//выбрана одиночная
						stage.addActor(levelTable);//добавляется список уровней
						Tween.set(netTable, ActorAccessor.ALPHA).target(1).start(tweenManager);
						Tween.to(netTable, ActorAccessor.ALPHA, 0.15f).target(0).start(tweenManager);
						Tween.set(levelTable, ActorAccessor.ALPHA).target(0).start(tweenManager); //исчезает сетевая игра
						Tween.to(levelTable, ActorAccessor.ALPHA, 0.15f).target(1).start(tweenManager);
						stage.getActors().removeValue(netTable, true);//удаляется сетевая игра со сцены
						netList.setSelectedIndex(-1); //снимается выделение сетевой игры и флаги сетевой игры
						serverSelected = false;
						clientSelected = false;
						break;
					case "  Сетевая" :  //Все аналогично тому, что выше, только убирются уровни, а сетевая игра добавляется
						netSelected = true;
						singleSelected = false;
						stage.addActor(netTable);
						Tween.set(netTable, ActorAccessor.ALPHA).target(0).start(tweenManager);
						Tween.to(netTable, ActorAccessor.ALPHA, 0.15f).target(1).start(tweenManager);
						Tween.set(levelTable, ActorAccessor.ALPHA).target(1).start(tweenManager);
						Tween.to(levelTable, ActorAccessor.ALPHA, 0.15f).target(0).start(tweenManager);
						stage.getActors().removeValue(levelTable, true);
						levellist.setSelectedIndex(-1);
						level = null;
						break;
					default : assert false;
				}
				checkNewGame();
			}
		});
		//levelPane
		levelPane.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String lev = levellist.getSelected();
				switch(lev){
					case "     Легкий" :
						level = Level.LOW;
						break;
					case "    Средний" :
						level = Level.MIDDLE;
						break;
					case "   Сложный" :
						level = Level.HARD;
						break;
					default : assert false;
				}
				checkNewGame();
			}
		});
		//netPane
		netPane.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String mode = netList.getSelected();
				//switch не работает Оо
				if(mode.equals("         Создать")){ serverSelected = true; clientSelected = false ;}
				else if(mode.equals("     Подключиться")) {clientSelected = true; serverSelected = false;}
				checkNewGame();
			}
		});
		
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
		butNewGame.setTouchable(Touchable.disabled);
		butNewGame.setColor(.5f, .5f, .5f,1);
		butNewGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSettings settings = new GameSettings();
				if(singleSelected) settings.mackeItSingleGame(level);
				else if(serverSelected) settings.mackeItServer();
				else settings.mackeItClient();
				((Game)Gdx.app.getApplicationListener()).setScreen(new FieldCreator(settings));
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

		netBack.setBounds(430, 105, 480, 420);
		netTable.setPosition(538, 210); 
		
		levelTable.setPosition(555, 210);
		

		stage.addActor(modeBack);
		modeBack.setBounds(95, 145, 260, 340);
		stage.addActor(modeTable);
		modeTable.setPosition(100, 235);
		
		
		stage.addActor(label);
		label.setPosition(350, 500);
				
		stage.addActor(butBack);
		butBack.setPosition(20, 20);
		
		stage.addActor(butNewGame);
		butNewGame.setPosition(600, 20);
	
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		Gdx.input.setInputProcessor(stage);
	}
	
	private void checkNewGame(){
		if( (singleSelected && level!=null) || (netSelected && (serverSelected||clientSelected) ) ){
			butNewGame.setTouchable(Touchable.enabled);
			Tween.to(butNewGame,ActorAccessor.RGB,0.15f).target(1,1,1).start(tweenManager);
		}
		else {
			butNewGame.setTouchable(Touchable.disabled);
			Tween.to(butNewGame,ActorAccessor.RGB,0.15f).target(.5f,.5f,.5f).start(tweenManager);
		}
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
	
}
