package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.core.Ship;
import ru.ssau.seabattle.game.GameSettings;
import ru.ssau.seabattle.game.SeaBatGame;
import ru.ssau.seabattle.resources.Textures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class FieldCreator implements Screen {

	//Элементы интерфейса
	private Stage stage;
	private Skin skin;
	private Label header;
	private TextButton create, generate, clean, menu;
	private BitmapFont smallFont, font;
	private SpriteBatch batch;
	
	//Раположение поля 
	private int xFieldoffset = 70, yFieldOffset = 150;
	private int shipsOffset = 100;
	
	//Заполнение поля
	private Label shipLabels[];
	private int count[];
	private ShipActor ships[];
	
	//Настройки игры
	private SeaBatGame game;
	private GameSettings settings;
	private Field my;

	public FieldCreator(GameSettings settings) {
		
		this.settings = settings;
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
		
//		int xOff = 500, yOff = 400;
//		for(int j =0; j < 10;j++){
//			for(int i=0; i < 10; i++){
//				if( my.getCell(i, j).getState() == CellState.SEA)
//					batch.draw(Textures.sea, xOff + 30*i, yOff - 30*j);
//				if( my.getCell(i, j).getState() == CellState.SHIP)
//					batch.draw(Textures.shi, xOff + 30*i, yOff - 30*j);
//			}
//		}
//
//		if( my.getCell(0, 0).getState() == CellState.SEA)
//			batch.draw(Textures.sea, xOff - 3  , yOff + 3 );
//		if( my.getCell(0, 0).getState() == CellState.SHIP)
//			batch.draw(Textures.shi, xOff - 3  , yOff + 3 );
		
		batch.end();
		
		stage.act(delta);
		stage.draw();
		
		if((count[0]+count[0]+count[0]+count[0]) == 0) create.setTouchable(Touchable.enabled);
		else create.setTouchable(Touchable.disabled);
	}
	
	@Override
	public void show() {
		
		stage = new Stage();
		
		skin = new Skin();
		skin.add("but_up", Textures.but_up);
		skin.add("but_d", Textures.but_d);
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		skin.add("default", labelStyle);
		
		batch = new SpriteBatch();
		
		header = new Label("Расставьте свои корабли", skin);
		
		smallFont = new BitmapFont(Gdx.files.internal("font/butFont.fnt"));
		
		buttonsInit();
		shipsInit();
		
		stage.addActor(header);
		header.setPosition(135, 500);
		
		stage.addActor(ships[3]);
		stage.addActor(ships[2]);
		stage.addActor(ships[1]);
		stage.addActor(ships[0]);
		
		stage.addActor(shipLabels[3]);
		stage.addActor(shipLabels[2]);
		stage.addActor(shipLabels[1]);
		stage.addActor(shipLabels[0]);
		
		stage.addActor(create);
		stage.addActor(generate);
		stage.addActor(clean);
		stage.addActor(menu);
		
		dragAndDropShips();
		
		Gdx.input.setInputProcessor(stage);
		
	}

	private void buttonsInit(){

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("but_up");
		textButtonStyle.down = skin.getDrawable("but_d");
		textButtonStyle.font = smallFont;
		textButtonStyle.fontColor = Color.BLACK;
		
		create = new TextButton("Создать", textButtonStyle);
		create.setPosition(700, 25);
		create.padBottom(15);
		create.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(settings.isSingleGame())
					((Game)Gdx.app.getApplicationListener()).setScreen(new GameController(settings,my,null));
				if(settings.isServerGame())
					((Game)Gdx.app.getApplicationListener()).setScreen(new ServerCreation(settings,my));	
				else ((Game)Gdx.app.getApplicationListener()).setScreen(new ServerFinderUI(settings,my));	
			}
		});
		
		menu = new TextButton("В меню", textButtonStyle);
		menu.setPosition(20, 20);
		menu.padBottom(15);
		menu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		clean = new TextButton("    Очистить    ", textButtonStyle);
		clean.setPosition(720, 200);
		clean.padBottom(15);
		clean.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				my.clear();
				Array<Actor> actors = stage.getActors();
				Array<ShipActor> stageShips = new Array<ShipActor>();
				for(Actor actor : actors)
					if( actor instanceof ShipActor) stageShips.add((ShipActor)actor);
				actors.removeAll(stageShips, true);
				actors.removeValue(shipLabels[3], true);
				actors.removeValue(shipLabels[2], true);
				actors.removeValue(shipLabels[1], true);
				actors.removeValue(shipLabels[0], true);
				shipsInit();
				stage.addActor(ships[3]);
				stage.addActor(ships[2]);
				stage.addActor(ships[1]);
				stage.addActor(ships[0]);
				stage.addActor(shipLabels[3]);
				stage.addActor(shipLabels[2]);
				stage.addActor(shipLabels[1]);
				stage.addActor(shipLabels[0]);
			}
		});
		
		generate = new TextButton("  Сгенерировать  ", textButtonStyle);
		generate.setPosition(720, 400);
		generate.padBottom(15);
		generate.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				my.generate();
				//удаление всех старых кораблей с экрана.
				for(int i = 0; i < 6; i++)
				for(Actor actor : stage.getActors()){
					if(actor instanceof ShipActor) stage.getActors().removeValue(actor, true);
					if(actor instanceof ShipActor) stage.getActors().removeValue(actor, false);
				}
				Object arr[] = my.getShips().toArray();
				for(int i = 0; i < arr.length; i++){
					Ship ship = (Ship) arr[i];
					ShipActor sh = new ShipActor(ship.getDecksNumber(), ship.isDirection());
					sh.setxCoord(ship.getX());
					sh.setyCoord(ship.getY());
					sh.debug();
					setShipOnField(sh);
					stage.addActor(sh);
				}
				for(int i =0; i < 4;i++){
					count[i] = 0;
					shipLabels[i].setText("0");
				}
			}
		});
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
						boolean dir = ((ShipActor)movedActor).isDirection();
						if(dir)
							movedActor.setPosition(x  - movedActor.getWidth()/2, y - movedActor.getHeight()/2);
						else 
							movedActor.setPosition(x  - movedActor.getWidth()/2, y - movedActor.getHeight()/2);
					}
				super.touchDragged(event, x, y, pointer);
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(movedActor == null && button == 0){
						movedActor = stage.hit(x, y, true);
						if(!(movedActor instanceof ShipActor)) movedActor = null;
					
				}
				else if(movedActor != null && button == 1){				
					((ShipActor)movedActor).changeDirection();
					boolean direction = ((ShipActor)movedActor).isDirection();
					if(direction) movedActor.setRotation(0);
					else movedActor.setRotation(0);
				}
				return super.touchDown(event, x, y, pointer, button);
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(movedActor != null){
					ShipActor ship = ((ShipActor)movedActor);
					setShip(ship);
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
		ships = new ShipActor[4];
		count = new int[4];
		LabelStyle labelStyle = new LabelStyle(font, Color.BLACK);
		shipLabels = new Label[4];
		
		for(int i =3; i >= 0; i--){
			ships[i] = new ShipActor(i+1, true);
			count[i] = 4 - i;
			shipLabels[i] = new Label(String.valueOf(count[i]), labelStyle);
			ships[i].setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 100 - 60*i);
			shipLabels[i].setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40,
					yFieldOffset + 320 - 100 - 60*i );
		}
		
	}
	
	/**
	 * Пытается постаить предвинутый корабль на поле.
	 * @param ship Корабль
	 * @return true - если успешно, fale - если не успешно.
	 */
	private boolean setShip(ShipActor ship){
		boolean result = false;
		boolean direction = ship.isDirection();
		float floatX = ship.getX(), floatY = ship.getY();
		int decksNum = ship.getDeckNuber();
		//Пересчитываем координаты так, чтобы попадание в клетку  велось не углом текстуры, а её краем.
		if(direction){
			floatX = floatX + ship.getWidth()/4;
			floatY = floatY + ship.getHeight()/2;
		}
		else{
			floatX = floatX + (int)ship.getWidth()/2;
			floatY = floatY + ship.getHeight() - (int)ship.getHeight()/4;
		}
		int x = (int)floatX;
		int y = (int)floatY;
		//если корабль попал по полю
		if(x >= (xFieldoffset  + 30) && y >= yFieldOffset &&
				x <= (xFieldoffset + 330) && y <= (yFieldOffset + 300)){
			//Переводим экранные координаты в координаты поля
			x = (int)(x - xFieldoffset - 30)/30;
			y = 9 - (int)(y - yFieldOffset)/30;
			//Если корабль до этого был на поле, он удаляется
			if(ship.getxCoord() != -1){ 
				my.deleteShip(ship.getxCoord(), ship.getyCoord(), decksNum, direction);}
			//После чего делается попытка поставить его заного
			result = my.setShip(x, y, decksNum, direction);
		}
		//В зависимости от результата выполняется различная постдоработка
		if(result){
			success(ship);
			ship.setxCoord(x);
			ship.setyCoord(y);
			setShipOnField(ship);
		}else{
			unsuccess(ship);
		}
		return result;
	}
	
	/**
	 * Вызывается, если результат постановки корабля на поле удачен.
	 * @param ship корабль, который поставлен.
	 */
	private void success(ShipActor ship){
		int deckNum = ship.getDeckNuber() - 1;
		//Если корабля не было ранее на поле над уменьшить счетчик
		if(ship.getxCoord()==-1){
			count[deckNum]--;
			//Если их все еще не 0, то на том месте надо создать еще 1 коабль.
			if(count[deckNum] > 0){
				ships[deckNum] = new ShipActor(deckNum + 1, true);
				ships[deckNum].setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 100 - 60*deckNum);
				shipLabels[deckNum].setPosition(xFieldoffset + 330 + shipsOffset + Textures.hfourDirShip.getWidth() + 40,
						yFieldOffset + 320 - 100 - 60*deckNum );
				stage.addActor(ships[deckNum]);
			}
			
		}else{//Если корабль удаляется с поля или была попытка поставить его рядом с другим
//			my.deleteShip(ship.getxCoord(), ship.getyCoord(), deckNum+1, ship.isDirection());
		}
		shipLabels[deckNum].setText(String.valueOf(count[deckNum]));
	}
	
	private void unsuccess(ShipActor ship){
		int deckNum = ship.getDeckNuber() - 1;
		//Если мы удаляем его с поля
		if(ship.getxCoord() != -1){
			//удаляем корабль с поля
			my.deleteShip(ship.getxCoord(), ship.getyCoord(), deckNum + 1, ship.isDirection());
			//удаляем корабль со сцены.
			stage.getActors().removeValue(ship, true);
			//увеличиваем количество нерасставленых кораблей этого типа.
			count[deckNum]++;
			//Если все корабли были расставлены
			if(ships[deckNum].getxCoord() != 0){
				//добавим в список корабль
				ships[deckNum] = new ShipActor(deckNum+1, true);
				stage.addActor(ships[deckNum]);
				ships[deckNum].setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 100 - 60*deckNum);
			} else {} // в противном случае корабль уже есть в списке
		}else{//Если же корабль был неудачно перетащен, то он просто возвращается на исходное место
			ship.setPosition(xFieldoffset + 330 + shipsOffset,	yFieldOffset + 330 - 100 - 60*deckNum);
		}
		shipLabels[deckNum].setText(String.valueOf(count[deckNum]));
	}
		
	private void setShipOnField(ShipActor ship){

		if(ship.isDirection()){
			ship.setX(ship.getxCoord()*30 + xFieldoffset + 30);
			ship.setY(270 - ship.getyCoord()*30 + yFieldOffset);
		}
		else{
			//Ты тупой. Он в логике ставит по верхней точке(направление вниз), а рисует по нижней вверх ДНАРЬ!!!!!
			ship.setX((ship.getxCoord()+1)*30 + xFieldoffset);
			ship.setY(300 - ship.getyCoord()*30 + yFieldOffset - ship.getHeight());
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
		skin.dispose();
		font.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}
}