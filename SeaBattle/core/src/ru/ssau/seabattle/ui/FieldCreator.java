package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.core.CellState;
import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.opponent.Level;
import ru.ssau.seabattle.resources.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FieldCreator implements Screen {

	//Элементы интерфейса
	private Stage stage;
	private Skin skin;
	
	private BitmapFont smallFont;
	private SpriteBatch batch;
	
	//Раположение поля 
	private int xFieldoffset = 70, yFieldOffset = 150;
	private int shipsOffset = 100;
	
	//Заполнение поля
	private Label one,two,three,four;
	private int count[];
	private Ship ship[];
	
	//Настройки игры
	private SeaBatGame game;
	private boolean singleGame;
	private boolean serverGame;
	private Level level;
	private Field my;

	public FieldCreator(Level level, boolean singleGame2, boolean serverGame2) {
		this.singleGame = singleGame;
		this.serverGame = serverGame;
		this.level = level;
		
		game = new SeaBatGame();
		my = new Field();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		

		batch.begin();

		batch.draw(Textures.background, 0, 0);
		batch.draw(Textures.field, xFieldoffset, yFieldOffset);
		
		int xOff = 500, yOff = 400;
		for(int j =0; j < 10;j++){
			for(int i=0; i < 10; i++){
				if( my.getCell(i, j).getState() == CellState.SEA)
					batch.draw(Textures.sea, xOff + 30*i, yOff - 30*j);
				if( my.getCell(i, j).getState() == CellState.SHIP)
					batch.draw(Textures.shi, xOff + 30*i, yOff - 30*j);
			}
		}

		if( my.getCell(0, 0).getState() == CellState.SEA)
			batch.draw(Textures.sea, xOff - 3  , yOff + 3 );
		if( my.getCell(0, 0).getState() == CellState.SHIP)
			batch.draw(Textures.shi, xOff -3 , yOff +3 );
		
		batch.end();
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void show() {
		
		stage = new Stage();
		
		skin = new Skin();
		
		batch = new SpriteBatch();
		
		smallFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"));
		
		shipsInit();
		labelsInit();
		
		stage.addActor(ship[3]);
		stage.addActor(ship[2]);
		stage.addActor(ship[1]);
		stage.addActor(ship[0]);
		
		stage.addActor(one);
		stage.addActor(two);
		stage.addActor(three);
		stage.addActor(four);
		
		dragAndDropShips();
		
		Gdx.input.setInputProcessor(stage);
		
	}

	private void labelsInit(){
		LabelStyle labelStyle = new LabelStyle(smallFont, Color.BLACK);
		one = new Label(String.valueOf(count[0]), labelStyle);
		one.setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40,yFieldOffset + 320 -  280 );
		
		two = new Label(String.valueOf(count[1]), labelStyle);
		two.setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40, yFieldOffset + 320 - 220 );
		
		three = new Label(String.valueOf(count[2]), labelStyle);
		three.setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40,yFieldOffset + 320 -  160 );
		
		four = new Label(String.valueOf(count[3]), labelStyle);
		four.setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40, yFieldOffset + 320 - 100 );
		
	}
	
	/**
	 * Инициализирует выполнение всех необходимых действий по перетаскиванию кораблей
	 */
	private void dragAndDropShips(){
		stage.addListener(new ClickListener(){
			Actor movedActor = null;
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
					if(movedActor != null){
						boolean dir = ((Ship)movedActor).isDirection();
						if(dir)
							movedActor.setPosition(x  - movedActor.getWidth()/2, y - movedActor.getHeight()/2);
						else 
							movedActor.setPosition(x  + movedActor.getHeight()/2, y - movedActor.getWidth()/2);
					}
				super.touchDragged(event, x, y, pointer);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(movedActor == null && button == 0){
						movedActor = stage.hit(x, y, true);
						if(!(movedActor instanceof Ship)) movedActor = null;
					
				}
				else if(movedActor != null && button == 1){				
					((Ship)movedActor).changeDirection();
					boolean direction = ((Ship)movedActor).isDirection();
					if(direction) movedActor.setRotation(0);
					else movedActor.setRotation(90);
				}
				return super.touchDown(event, x, y, pointer, button);
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(movedActor != null){
					Ship ship = ((Ship)movedActor);
					//Это добавил Мирош после комита
//					if(ship.isDirection())
						//TODO
//						setShip(ship ,ship.getX() + ship.getWidth()/40, ship.getY() + ship.getHeight()/2);
//					else 
						//TODO
//						setShip(ship.getX(), ship.getY(), ship.isDirection(), ship.getDeckNuber());
					movedActor = null;
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	/**
	 * Задает начальное расположение кораблей для перетаскивания.
	 */
	private void shipsInit(){
		ship[3] = new Ship(4, true);
		ship[3].setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 100);
		
		ship[2] = new Ship(3, true);
		ship[2].setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 160);
		
		ship[1] = new Ship(2, true);
		ship[1].setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 220);
		
		ship[0] = new Ship(1, true);
		ship[0].setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 280);
		
	}
	
	/**
	 * Ставит предвинутый корабль на поле.
	 * @param x координата, куда был поставлен коарбль.
	 * @param y координата, куда был поставлен коарбль.
	 * @param direction направление корабля.
	 * @param decksNumber количество палуб корабля.
	 * @return true, если корабль будет поставлен на поле, false, если нет.
	 */
//	private boolean setShip(Ship ship, float x, float y){ 
//
//	}
	
	private void afterSetShip(Ship ship, boolean result){
		
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
		smallFont.dispose();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}
}
