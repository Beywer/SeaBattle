package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.game.GameSettings;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.opponent.net.Server;
import ru.ssau.seabattle.resources.Textures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ServerCreation implements Screen {

	private Stage stage;
	private Table background;
	private Skin skin;
	private SeaBatGame game;
	private Label header;
	private TextButton create, menu;
	private TextArea serverName;
	private BitmapFont font, smallFont;
	private GameSettings settings;
	
	public ServerCreation(GameSettings settings, Field playerField){
		game = new SeaBatGame();
		game.setMyField(playerField);
		this.settings = settings;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		stage.act(delta);
		stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		stage = new Stage();
		skin = new Skin();
		skinInit();
		
		
		background = new Table();
		background.setBackground(skin.getDrawable("back"));
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		TextFieldStyle textFieldStyle = new TextFieldStyle(smallFont, Color.WHITE,
				skin.getDrawable("cursor"), skin.getDrawable("selection"), skin.getDrawable("textBack"));
		serverName = new TextArea("", textFieldStyle);
		serverName.setMaxLength(30);
		serverName.setBounds(200, 250, 600, 50);
		
		
		header = new Label("Введите имя сервера", skin);
		header.setPosition(500 - header.getWidth()/2, 450);

		stage.addActor(background);	
		stage.addActor(serverName);		
		stage.addActor(header);
		buttons();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	private void skinInit(){
		skin = new Skin();
		skin.add("back", Textures.background);
		skin.add("butUp", Textures.but_up);
		skin.add("butD", Textures.but_d);
		skin.add("cursor", Textures.cursor);
		skin.add("selection", Textures.selection);
		skin.add("textBack", Textures.diagBack);
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		smallFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"));
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		skin.add("default", labelStyle);
	}
	
	private void buttons(){
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("butUp");
		textButtonStyle.down = skin.getDrawable("butD");
		textButtonStyle.font = smallFont;
		textButtonStyle.fontColor = Color.BLACK;
		
		create = new TextButton("  Создать  ",textButtonStyle);
		create.setPosition(700, 30);
		create.padBottom(15);
		create.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Server server = new Server(serverName.getText(), game, settings);
				((Game)Gdx.app.getApplicationListener()).setScreen(new GameController(settings, game.getMyField(), server));
			}
		});
		

		menu = new TextButton("  Меню  ", textButtonStyle);
		menu.setPosition(30, 30);
		menu.padBottom(15);
		menu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});		
		
		stage.addActor(create);
		stage.addActor(menu);
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
