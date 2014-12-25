package ru.ssau.seabattle.desktop;

import java.io.IOException;

import ru.ssau.seabattle.ui.SeaBattle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException, InterruptedException {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.width = 1000;
		config.height = 600;
		config.resizable = false;
		new LwjglApplication(new SeaBattle(), config);
	}
}
