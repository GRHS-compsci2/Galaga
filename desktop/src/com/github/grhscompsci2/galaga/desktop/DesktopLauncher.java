package com.github.grhscompsci2.galaga.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
<<<<<<< Updated upstream
import com.badlogic.gdx.graphics.Color;
=======
import com.github.grhscompsci2.galaga.MyAssetManager;
>>>>>>> Stashed changes
import com.github.grhscompsci2.galaga.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
<<<<<<< Updated upstream
		config.backgroundFPS = 10;
		config.foregroundFPS = 30;
		config.height = 244;
		config.initialBackgroundColor = Color.BLACK;
		config.resizable = false;
		config.title = "Galaga";
		config.vSyncEnabled = true;
		config.width = 288;
		new LwjglApplication(new MyGdxGame(), config);
=======
		new LwjglApplication(new MyAssetManager(), config);
>>>>>>> Stashed changes
	}
}
