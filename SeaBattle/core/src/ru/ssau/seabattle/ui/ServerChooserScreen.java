package ru.ssau.seabattle.ui;

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

public class ServerChooserScreen implements Screen {

	private Stage stage;
	private ScrollPane pane;
	private ScrollPaneStyle style;
	private List<String> list;
	private Label header;
	private TextButton butContinue, butBack;
	private BitmapFont butFont, headerFont;
	
	int i = 30;
	float time = 0;;
	
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
	public void show() {
		stage = new Stage();
		
		//Textures
		Skin skin = new Skin();
		Texture texture = new Texture("pane/hScroll.png");
		skin.add("hScroll", texture);
		texture = new Texture("pane/vScroll.png");
		skin.add("vScroll", texture);
		texture = new Texture("pane/hScrollKnob.png");
		skin.add("hScrollKnob", texture);
		texture = new Texture("pane/vScrollKnob.png");
		skin.add("vScrollKnob", texture);
		texture = new Texture("pane/paneBack.png");
		skin.add("paneBack", texture);
		skin.add("back", new Texture("back.png"));
		skin.add("but_d", new Texture("button/but_d.png"));
		skin.add("but_up", new Texture("button/but_up.png"));
				
		//Fonts
		butFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"));
		headerFont = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		
		//Scroll pane
		style = new ScrollPaneStyle(
				skin.getDrawable("paneBack")
				, skin.getDrawable("hScroll")
				, skin.getDrawable("hScrollKnob")
				, skin.getDrawable("vScroll")
				, skin.getDrawable("vScrollKnob"));
		
		ListStyle listStyle = new ListStyle(butFont , Color.RED, Color.WHITE, skin.getDrawable("vScroll"));
		
		list = new List<String>(listStyle);
		list.setItems("sdf","sdf2","sdf3","sdf4","sdf5","sdf6","sdf7"
				,"sdf8","sdf9","sdf10","sdf11","sdf12"
				,"sdf13","sdf14","sdf15"
				,"sdf16","sdf17","sdf18"
				,"sdf19","sdf20","sdf21","sdf22","sdf23");
		
		pane = new ScrollPane(list, style);
		pane.setSize(700, 350);
			
		//Font and header
		LabelStyle labelStyle = new LabelStyle(headerFont , Color.WHITE);
		header = new Label("Загрузка игры", labelStyle);
		header.setFontScale(1);
		
		//Buttons
		
		Table table = new Table();
		table.setBounds(0, 0, 1000, 50);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("but_up");
		textButtonStyle.down = skin.getDrawable("but_d");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.font = butFont;
		textButtonStyle.fontColor = Color.BLACK;
		
		butContinue = new TextButton("    Продолжить игру    ", textButtonStyle);
		butContinue.pad(2, 100, 15, 100);
		butContinue.setBounds(0, 0, 400, 100);

		butBack = new TextButton("Назад", textButtonStyle);
		butBack.pad(0, 70, 4, 70);
		butBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});

		//Размещение элементов
//		table.pad(20);
//		table.add(butContinue);//.left().bottom();
//		table.add(butBack);//.right().bottom();
//		table.getCell(butContinue).spaceRight(300);
//		table.set
		
		Table background = new Table(skin);
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setBackground(skin.getDrawable("back"));

		stage.addActor(background);
		stage.addActor(pane);
		stage.addActor(header);
		
		stage.addActor(butContinue);
		butContinue.setBounds(20,15, 400, 100);
		
		stage.addActor(butBack);
		butBack.setBounds(580, 15, 400, 100);
		
		pane.setPosition(150, 135);
		header.setFontScale(1);
		header.setPosition(20, 20);
		header.setPosition(500 - header.getWidth()/2, 500);
	
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
		stage.dispose();
	}

}