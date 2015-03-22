package ru.ssau.seabattle.ui;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.game.GameSettings;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.opponent.net.ServerFinder;
import ru.ssau.seabattle.resources.Textures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

public class ServerFinderUI implements Screen {

	private Stage stage;
	private Table background;
	private Skin skin;
	private Label header;
	private BitmapFont font, smallFont;
	private ScrollPane serverPane; 
	private List<String> serverList;
	private Table paneTable;
	private Image back;
	private TextButton  connect, menu;

	private SeaBatGame game;
	private GameSettings settings;
	private ServerFinder finder;
	private HashMap<InetAddress, String> servers;
	
	public ServerFinderUI(GameSettings settings, Field my) {
		finder = new ServerFinder();
		finder.start();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		servers = finder.getServers();
		if(serverList.getItems().size != 0)
			serverList.getItems().removeRange(0, serverList.getItems().size - 1);
		for(String value : servers.values()){
			serverList.getItems().add(value);
		}
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		
		//Stage and skin
		stage = new Stage();
		skin = new Skin();
		
//		skin.add("empty", new Texture("pane/empty.png"));
//		skin.add("selection", new Texture("pane/select.png"));
//		skin.add("hScrollKnob", new Texture("pane/horKnob.png"));
//		skin.add("vScrollKnob", new Texture("pane/verKnob.png"));
//		skin.add("paneBack", new Texture("pane/back.png"));		
//		skin.add("back", new Texture("back.png"));
//		skin.add("but_up", new Texture("button/but_up.png"));
//		skin.add("but_d", new Texture("button/but_d.png"));
		
		skinInit();
		
		//FONT
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		smallFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"), false);//Здесь false-y ось нарпавлена вверх
		
		//Background
		background = new Table(skin);
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setBackground(skin.getDrawable("back"));

		//HEADERS
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		header = new Label("Поиск серверов", labelStyle);

		//PANES
		ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle(
				skin.getDrawable("empty"),
				skin.getDrawable("empty"),
				skin.getDrawable("hScrollKnob"),
				skin.getDrawable("empty"),
				skin.getDrawable("vScrollKnob"));
		//modePane
		ListStyle listStyle = new ListStyle(smallFont, Color.WHITE, Color.BLACK, skin.getDrawable("empty"));
		serverList = new List<String>(listStyle);
		serverList.setItems(new String[]{"asldkjfal;dsfj;alksdjf;aklsdjf"});
		serverList.getItems().add("1");
		serverList.getItems().add("2");
		serverList.getItems().add("3");
		serverList.getItems().add("4");
		serverList.getItems().add("5");
		serverList.getItems().add("6");
		serverList.getItems().add("7");
		serverList.getItems().add("8");
		serverList.getItems().add("9");
		serverList.getItems().add("10");
		serverList.getItems().add("11");
		serverList.getItems().add("12");
		serverList.getItems().add("13");
		serverPane = new ScrollPane(serverList, scrollPaneStyle);

		
		back = new Image(skin.getDrawable("paneBack"));
		//modeTable
		paneTable = new Table(skin);
		paneTable.add(serverPane).fill();
		paneTable.setBounds(0, 0, 1000, 190);
		
		stage.addActor(background);
		
		stage.addActor(back);
		back.setBounds(230, 0, 560, 450);
		stage.addActor(paneTable);
		
		stage.addActor(header);
		header.setPosition(500 - header.getWidth()/2, 500);
		paneTable.setPosition(20, 140);
		
		buttons();
		
		Gdx.input.setInputProcessor(stage);
	
	}

	
	private void skinInit(){
		skin = new Skin();
		skin.add("back", Textures.background);
		skin.add("butUp", Textures.but_up);
		skin.add("butD", Textures.but_d);
		skin.add("textBack", Textures.diagBack);
		skin.add("empty", new Texture("pane/empty.png"));
		skin.add("selection", new Texture("pane/select.png"));
		skin.add("hScrollKnob", new Texture("pane/horKnob.png"));
		skin.add("vScrollKnob", new Texture("pane/verKnob.png"));
		skin.add("paneBack", new Texture("pane/back.png"));		
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
		
		connect = new TextButton("     Подключиться     ",textButtonStyle);
		connect.setPosition(700, 30);
		connect.padBottom(15);
		connect.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		

		menu = new TextButton("  Меню  ", textButtonStyle);
		menu.setPosition(30, 30);
		menu.padBottom(15);
		menu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				finder.interrupt();
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});		
		
		stage.addActor(connect);
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
