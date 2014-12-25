package ru.ssau.seabattle.ui;

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
import com.badlogic.gdx.utils.Array;

public class FieldCreator implements Screen {

	//Элементы интерфейса
	private Stage stage;
	private Skin skin;
	
	private BitmapFont smallFont;
	private SpriteBatch batch;
	
	private int xFieldoffset = 70, yFieldOffset = 150;
	private int shipsOffset = 100;
	
	//Заполнение поля
	private Label one,two,three,four;
	private int count1 = 4, count2 = 3, count3 = 2, count4 = 1;
	private Ship ship4, ship3, ship2, ship1;
	
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
		
		stage.addActor(ship4);
		stage.addActor(ship3);
		stage.addActor(ship2);
		stage.addActor(ship1);
		
		stage.addActor(one);
		stage.addActor(two);
		stage.addActor(three);
		stage.addActor(four);
		
		dragAndDropShips();
		
		Gdx.input.setInputProcessor(stage);
		
	}

	private void labelsInit(){
		LabelStyle labelStyle = new LabelStyle(smallFont, Color.BLACK);
		one = new Label(String.valueOf(count1), labelStyle);
		one.setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40,yFieldOffset + 320 -  280 );
		
		two = new Label(String.valueOf(count2), labelStyle);
		two.setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40, yFieldOffset + 320 - 220 );
		
		three = new Label(String.valueOf(count3), labelStyle);
		three.setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40,yFieldOffset + 320 -  160 );
		
		four = new Label(String.valueOf(count4), labelStyle);
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
				Ship ship = ((Ship)movedActor);
				setShip(ship.getX(), ship.getY(), ship.isDirection(), ship.getDeckNuber());
				movedActor = null;
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	/**
	 * Задает начальное расположение кораблей для перетаскивания.
	 */
	private void shipsInit(){
		ship4 = new Ship(4, true);
		ship4.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 100);
		
		ship3 = new Ship(3, true);
		ship3.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 160);
		
		ship2 = new Ship(2, true);
		ship2.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 220);
		
		ship1 = new Ship(1, true);
		ship1.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 280);
		
	}
	
	/**
	 * Проверяет: поставлен ли передвинутый корабль на поле или нет.
	 * @param x координата, куда был поставлен коарбль.
	 * @param y координата, куда был поставлен коарбль.
	 * @param direction направление корабля.
	 * @param decksNumber количество палуб корабля.
	 * @return true, если корабль будет поставлен на поле, false, если нет.
	 */
	private boolean setShip(float x, float y, boolean direction, int decksNumber){
		boolean result = false;
		if(x >= (xFieldoffset + 30) && y >= yFieldOffset && x<=(xFieldoffset + 330) && y<=(yFieldOffset + 300)){
			x-=xFieldoffset+30; // х получится в интервале от 0 до 300
			y-=yFieldOffset;    // у получится в интервале от 0 до 300
			int coordX = (int)x/30;
			int coordY = (int)y/30;
			result = my.setShip(coordX, coordY, decksNumber, direction);
		}
		else{ 
			result = false;
		}
		afterSetShip(result,decksNumber);
		return result;
	}
	
	private void afterSetShip(boolean result, int decksNumber){
		if(result){ //Если корабль успешно поставлен на поле, то в списке доступных кораблей создается новый
			switch(decksNumber){
			case 1:
				if(count1 > 0) count1--;
				one.setText(String.valueOf(count1));
				if(count1 > 0) {
					ship1 = new Ship(1, true);
					ship1.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 280);
					stage.addActor(ship1);
				}
				break;
			case 2:
				if(count2 > 0) count2--;
				two.setText(String.valueOf(count2));
				if(count2 > 0) {
					ship2 = new Ship(2, true);
					ship2.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 220);
					stage.addActor(ship1);
				}
				break;
			case 3:
				if(count3 > 0) count3--;
				three.setText(String.valueOf(count3));
				if(count3 > 0) {
					ship3 = new Ship(3, true);
					ship3.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 160);
					stage.addActor(ship1);
				}
				break;
			case 4: 
				if(count4 > 0) count1--;
				four.setText(String.valueOf(count1));
				if(count4 > 0) {
					ship4 = new Ship(4, true);
					ship4.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 100);
					stage.addActor(ship1);
				}
				break;
			default : assert false;
			}
		}else{ //Если результат неудачен, уничножаем тот корабль и создаем на месте старого новый.
			Array<Actor> array =  stage.getActors();
			switch(decksNumber){
			case 1:
				array.removeValue(ship1, true);
				ship1 = new Ship(1, true);
				ship1.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 280);
				break;
			case 2:
				array.removeValue(ship2, true);
				ship2 = new Ship(2, true);
				ship2.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 220);
				break;
			case 3:
				array.removeValue(ship3, true);
				ship3 = new Ship(3, true);
				ship3.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 160);
				break;
			case 4:
				array.removeValue(ship4, true);
				ship4 = new Ship(4, true);
				ship4.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 100);
				break;
			default: assert false;
			}
		}
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
