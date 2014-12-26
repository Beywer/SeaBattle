package ru.ssau.seabattle.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import ru.ssau.seabattle.game.SeaBatGame;

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

public class LoadGame implements Screen {

	private Stage stage;
	private ScrollPane pane;
	private ScrollPaneStyle style;
	private List<String> list;
	private List<File> listWithFileNames;
	private Label header;
	private TextButton butContinue, butBack;
	private BitmapFont butFont, headerFont;
	private String saved=".\\saved\\";
	
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
		/*
		String fileName="."+"1.txt";
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(fileName, "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.writeChars("Йо-хо-хо");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//
		//Textures
		Skin skin = new Skin();
		Texture texture = new Texture("pane/select.png");
		skin.add("hScroll", texture);
		texture = new Texture("pane/empty.png");
		skin.add("vScroll", texture);
		texture = new Texture("pane/hScrollKnob.png");
		skin.add("hScrollKnob", texture);
		texture = new Texture("pane/verKnob.png");
		skin.add("vScrollKnob", texture);
		texture = new Texture("pane/back.png");
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
		
		ListStyle listStyle = new ListStyle(butFont , Color.WHITE, Color.BLACK, skin.getDrawable("vScroll"));

		listWithFileNames = new List<File>(listStyle);//список файлов из папки
		listWithFileNames=getListFiles(saved,listWithFileNames);
		pane =new ScrollPane(listWithFileNames,style);
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
/**
 * 
 * @param s Сохраняемый объект
 * @param name Место где лежат все сохранения
 * @throws IOException
 */
	public void saveGame(SeaBatGame s, String name) throws IOException//Черновое сохранение
	{
		FileOutputStream fos = new FileOutputStream("C:\\1\\"+name+".sav");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		SeaBatGame ts =s;
		oos.writeObject(ts);
		oos.flush();
		oos.close();
	}
	/**
	 * 
	 * @param name Место где лежат все сохранения
	 * @return Объект SeaBatGame
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public SeaBatGame loadGame(String name)throws IOException, ClassNotFoundException//Черновая загрузка
	{
		FileInputStream fis = new FileInputStream("C:\\1\\"+name+".sav");
		ObjectInputStream oin = new ObjectInputStream(fis);
		SeaBatGame ts = (SeaBatGame) oin.readObject();
		return ts;
	}
	/**
	 * 
	 * @param str путь к папке
	 */
	public List<File> getListFiles(String str, List<File> listWithFileNames) {//сохраняет список всех файлов из папки в глобальную переменную
        File f = new File(str);
        for (File s : f.listFiles()) {
            if (s.isFile()) {
            	if(s.getName().endsWith(".sav"))
                listWithFileNames.getItems().add(s);;
            } else if (s.isDirectory()) {
            	listWithFileNames=getListFiles(s.getAbsolutePath(),listWithFileNames);      
            }
        }
		return listWithFileNames;
        
    }
	/**
	 * 
	 * @param s путь к папке
	 */
	public void printListFile(String s,List<File> listWithFileNames)//печатает список всех файлов из папки
	{
		listWithFileNames=getListFiles(s,listWithFileNames);
        
        for (File fil : listWithFileNames.getItems()) {
            System.out.println(fil.getName());
        }
	}

}