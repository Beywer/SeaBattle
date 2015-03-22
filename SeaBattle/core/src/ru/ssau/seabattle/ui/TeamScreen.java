package ru.ssau.seabattle.ui;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TeamScreen implements Screen {

	private Stage stage;
	private Skin skin;
	private TextButton back;
	private BitmapFont font,smallFont;
	private Label l1,l2,l3,l4,l5,l6;
	
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
		skin.add("up", Textures.but_up);
		skin.add("down", Textures.but_d);
		skin.add("back", Textures.diagBack);
		
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		
		l1 = new Label("Эта программа разработана студентами СГАУ", labelStyle);
		l1.setFontScale(.5f);
		l2 = new Label("факультата Информатики, группы 6402 Б 358", labelStyle);
		l2.setFontScale(.5f);
		l3 = new Label("Киселевой А.В., Мирошниковым Д.Ю., Нагуловым Ю.А.", labelStyle);
		l3.setFontScale(.5f);
		l5 = new Label("под руководством доцента кафедры Программных систем", labelStyle);
		l5.setFontScale(.5f);
		l6 = new Label("Зеленко Л.С.", labelStyle);
		l6.setFontScale(0.5f);
		
		l4 = new Label("Самара 2014", labelStyle);
		l4.setFontScale(.5f);
		
		l1.setPosition(150, 400);
		l2.setPosition(175, 350);
		l3.setPosition(20, 300);
		l5.setPosition(80, 250);
		l6.setPosition(365, 200);
		l4.setPosition(20, 100);
		
		smallFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"));
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("up");
		buttonStyle.down = skin.getDrawable("down");
		buttonStyle.font = smallFont;
		buttonStyle.fontColor = Color.BLACK;
		
		back = new TextButton("Назад", buttonStyle);
		back.padBottom(15);
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		back.setPosition(20, 20);
		
		stage.addActor(l1);
		stage.addActor(l2);
		stage.addActor(l3);
		stage.addActor(l4);
		stage.addActor(l5);
		stage.addActor(l6);
		stage.addActor(back);
		Gdx.input.setInputProcessor(stage);
		
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
