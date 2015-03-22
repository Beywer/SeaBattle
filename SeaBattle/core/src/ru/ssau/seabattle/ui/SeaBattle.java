package ru.ssau.seabattle.ui;

import ru.ssau.seabattle.core.Field;
import ru.ssau.seabattle.game.GameSettings;
import ru.ssau.seabattle.opponent.Level;

import com.badlogic.gdx.Game;

public class SeaBattle extends Game  {

	@Override
	public void create() {
		setScreen(new MainMenu());
//		Field playerField = new Field();
//		playerField.generate();
//		GameSettings settings = new GameSettings();
//		settings.mackeItSingleGame(Level.LOW);
//		setScreen(new GameController(settings,playerField));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
