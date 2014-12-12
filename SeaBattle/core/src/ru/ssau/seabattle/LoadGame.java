package ru.ssau.seabattle;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class LoadGame implements Screen {

	private Stage stage;
	private ScrollPane pane;
	private ScrollPaneStyle style;
	private List<String> list;
	private Label header;
	private TextButton butContinue, butBack;
	TweenManager tweenManager;
	
	int i = 30;
	float time = 0;;
	
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
		stage = new Stage();
		
		//Textures
		TextureAtlas atlas = new TextureAtlas("button/Buttons.pack");
		Skin skin = new Skin(atlas);
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
				
		//Scroll pane
		style = new ScrollPaneStyle(
				skin.getDrawable("paneBack")
				, skin.getDrawable("hScroll")
				, skin.getDrawable("hScrollKnob")
				, skin.getDrawable("vScroll")
				, skin.getDrawable("vScrollKnob"));
		
		ListStyle listStyle = new ListStyle(
				new BitmapFont(Gdx.files.internal("font/SeaBattleFont.fnt"), false)
				, Color.RED, Color.WHITE, skin.getDrawable("vScroll"));
		
		list = new List<String>(listStyle);
		list.setItems("sdf","sdf2","sdf3","sdf4","sdf5","sdf6","sdf7"
				,"sdf8","sdf9","sdf10","sdf11","sdf12"
				,"sdf13","sdf14","sdf15"
				,"sdf16","sdf17","sdf18"
				,"sdf19","sdf20","sdf21","sdf22","sdf23");
		
		pane = new ScrollPane(list, style);
		pane.setSize(700, 350);
			
		//Font and header
		BitmapFont font = new BitmapFont(Gdx.files.internal("font/SeaBattleFont.fnt"));
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		header = new Label("Загрузка игры", labelStyle);
		header.setFontScale(1.5f);
		
		//Buttons
		
		Table table = new Table();
		table.setBounds(0, 0, 1000, 50);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("but_up");
		textButtonStyle.down = skin.getDrawable("but_down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.font = font;
		
		butContinue = new TextButton("   Продолжить игру   ", textButtonStyle);
		butContinue.pad(0, 70, 4, 70);

		butBack = new TextButton("Назад", textButtonStyle);
		butBack.pad(0, 70, 4, 70);
		butBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});

		//Размещение элементов
		table.pad(20);
		table.add(butContinue).left().bottom();
		table.add(butBack).right().bottom();
		table.getCell(butContinue).spaceRight(300);
		
		Table background = new Table(skin);
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setBackground(skin.getDrawable("back"));

		stage.addActor(background);
		stage.addActor(pane);
		stage.addActor(header);
		stage.addActor(table);
		pane.setPosition(150, 150);
		header.setPosition(450 - header.getWidth()/2, 700);
		tweenManager = new TweenManager();
			
		Tween.to(header, ActorAccessor.Y, 1).target(540).start(tweenManager);		
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