package ru.ssau.seabattle;

import aurelienribon.tweenengine.Timeline;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
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
	private BitmapFont font;
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
		font = new BitmapFont(Gdx.files.internal("font/SeaBattleFont.fnt"), false);//Здесь false-y ось нарпавлена вверх
		
		atlas = new TextureAtlas("button/Buttons.pack");
		skin = new Skin(atlas);
		skin.add("back", new Texture("back.png"));
		
		stage = new Stage();

		ScreenViewport viewport = new ScreenViewport();
		viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.setViewport(viewport);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//Create buttons
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("but_up");
		textButtonStyle.down = skin.getDrawable("but_down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.font = font;
		
		buttonExit = new TextButton("        Выход        ", textButtonStyle);
		buttonExit.pad(0, 70, 4, 70);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		buttonPlay = new TextButton("      Новая игра     ", textButtonStyle);
		buttonPlay.pad(0, 70, 4, 70);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new NewGame());
			}
		});

		butDownload = new TextButton("   Загрузить игру   ", textButtonStyle);
		butDownload.pad(0, 70, 4, 70);
		butDownload.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new LoadGame());
			}
		});
		

		butAbout = new TextButton("    О программе    ", textButtonStyle);
		butAbout.pad(0, 70, 4, 70);
		

		butTeam = new TextButton("  О разработчиках  ", textButtonStyle);
		butTeam.pad(0, 70, 4, 70);
		
		//Create heading
		LabelStyle headingStyle = new LabelStyle(font, Color.WHITE);
		heading = new Label("Морской бой", headingStyle);
		heading.setFontScale(2);
		
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
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		Timeline.createSequence().beginSequence()
			.push(Tween.to(heading, ActorAccessor.RGB, .4f).target(0, 0, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, .4f).target(0, 1, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, .4f).target(0, 1, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, .4f).target(1, 0, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, .4f).target(1, 0, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, .4f).target(1, 1, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, .4f).target(1, 1, 1))
			.end()
			.repeat(Tween.INFINITY, 0)
			.start(tweenManager);
				
//		Tween.from(table, ActorAccessor.ALPHA, 1).target(0).start(tweenManager);
//		Tween.from(table, ActorAccessor.Y, 2).target(Gdx.graphics.getWidth()/4).start(tweenManager);
//		Gdx.app.log("sdf",String.valueOf(buttonPlay.getX()));
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
