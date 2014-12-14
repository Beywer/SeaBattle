package ru.ssau.seabattle;

import aurelienribon.tweenengine.TweenManager;

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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class NewGame implements Screen {

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private TweenManager tweenManager;
	private Table modeTable, levelTable;
	private ScrollPane modePane, levelPane;
	private BitmapFont font;
	Label label;
	
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
		atlas = new TextureAtlas("button/Buttons.pack");
		skin = new Skin(atlas);
		skin.add("hScroll", new Texture("pane/hScroll.png"));
		skin.add("vScroll", new Texture("pane/vScroll.png"));
		skin.add("hScrollKnob", new Texture("pane/hScrollKnob.png"));
		skin.add("vScrollKnob", new Texture("pane/vScrollKnob.png"));
		skin.add("paneBack", new Texture("pane/paneBack.png"));
		skin.add("back", new Texture("back.png"));
		
		//FONT
		font = new BitmapFont(Gdx.files.internal("font/SeaBattleFont.fnt"));
		
		//Background
		Table background = new Table(skin);
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setBackground(skin.getDrawable("back"));
				
		//HEADERS
		final LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		label = new Label("Новая игра", labelStyle);
		label.setFontScale(1.5f);
		//modeHeader
		Label modeHeader = new Label("Режим игры", labelStyle);
		modeHeader.setFontScale(1.5f);
		//levelHeader
		Label levelHeader = new Label("Сложность игры", labelStyle);
		levelHeader.setFontScale(1.5f);
		
		//PANES
		ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle(
				skin.getDrawable("paneBack"),
				skin.getDrawable("hScroll"),
				skin.getDrawable("hScrollKnob"),
				skin.getDrawable("vScroll"),
				skin.getDrawable("vScrollKnob"));
		//modePane
		ListStyle listStyle = new ListStyle(font, Color.RED, Color.BLACK, skin.getDrawable("hScroll"));
		List<String> modeList = new List<String>(listStyle);
		modeList.setItems(new String[]{"    Одиночная ","      Сетевая"});
		modeList.setSelectedIndex(-1);
		modePane = new ScrollPane(modeList, scrollPaneStyle);
		modePane.setBounds(0, 0, 10, 10);
		//LevelPane
		List<String> levellist = new List<String>(listStyle);
		levellist.setItems(new String[]{"        Легкий","       Средний","       Сложный"});
		levelPane = new ScrollPane(levellist, scrollPaneStyle);
		
		//TABLES
		//modeTable
		modeTable = new Table(skin);
		modeTable.add(modeHeader);
		modeTable.row();
		modeTable.add(modePane).fill();
		modeTable.setBounds(0, 0, 250, 100);
		//levelTable
		levelTable = new Table(skin);
		levelTable.add(levelHeader);
		levelTable.row();
		levelTable.add(levelPane).fill();
		levelTable.setBounds(0, 0, 250, 150);
		
		modeHeader.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				labelStyle.background = skin.getDrawable("hScrollKnob");
			}
		});
		
		stage.addActor(background);
		
		stage.addActor(levelTable);
		levelTable.setPosition(300, 225);
		
		stage.addActor(label);
		label.setBounds(0, 0, 202, 26);
		label.setPosition(410, 550);
		
		stage.addActor(modeTable);
		modeTable.setPosition(20, 250);
		
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

	}
}
