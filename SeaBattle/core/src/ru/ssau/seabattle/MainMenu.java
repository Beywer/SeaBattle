package ru.ssau.seabattle;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu implements Screen {

	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton buttonPlay,buttonExit, butDownload, butAbout, butTeam;
	private Label heading;
	private BitmapFont font, butFont;
	private TextureAtlas atlas;
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		tweenManager.update(delta);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, width, true);
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);//Здесь false-y ось нарпавлена вверх
		butFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"), false);//Здесь false-y ось нарпавлена вверх
		
		skin= new Skin();
		skin.add("back", new Texture("back.png"));
		skin.add("but_down", new Texture("button/but_d.png"));
		skin.add("but_up", new Texture("button/but_up.png"));
		
		stage = new Stage();

		ScreenViewport viewport = new ScreenViewport();
		viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.setViewport(viewport);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//Create buttons
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.fontColor = Color.BLACK;
		textButtonStyle.up = skin.getDrawable("but_up");
		textButtonStyle.down = skin.getDrawable("but_down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.font = butFont;
		
		buttonExit = new TextButton("        Выход        ", textButtonStyle);
		buttonExit.pad(0, 80, 20, 80);
		buttonExit.setSize(20, 20);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		buttonPlay = new TextButton("      Новая игра     ", textButtonStyle);
		buttonPlay.pad(0, 80, 25, 80);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new NewGame());
			}
		});

		butDownload = new TextButton("   Загрузить игру   ", textButtonStyle);
		butDownload.pad(0, 80, 25, 80);
		butDownload.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new LoadGame());
			}
		});
		

		butAbout = new TextButton("    О программе    ", textButtonStyle);
		butAbout.pad(0, 80, 25, 80);
		

		butTeam = new TextButton("  О разработчиках  ", textButtonStyle);
		butTeam.pad(0, 80, 25, 80);
		
		//Create heading
		LabelStyle headingStyle = new LabelStyle(font, Color.WHITE);
		heading = new Label("Морской бой", headingStyle);
		heading.setFontScale(1);
		heading.setColor(Color.WHITE);
		
		//Filling the table
		table.background(skin.getDrawable("back"));
		table.add(heading);
		table.add(buttonPlay).expandY();
		table.row();
		table.add().width(table.getWidth()/2);
		table.add(butDownload).expandY();
		table.row();
		table.add().width(table.getWidth()/2);
		table.add(butTeam).expandY();
		table.row();
		table.add().width(table.getWidth()/2);
		table.add(butAbout).expandY();
		table.row();
		table.add().width(table.getWidth()/2);
		table.add(buttonExit).expandY();
//		table.row();
//		table.add().height(200);
//		table.debug();		
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
		
		//Creating animation
		tweenManager = new TweenManager();
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
		atlas.dispose();
		font.dispose();
		skin.dispose();
	}

}
