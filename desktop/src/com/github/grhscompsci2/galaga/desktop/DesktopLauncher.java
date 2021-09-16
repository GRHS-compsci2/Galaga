package com.github.grhscompsci2.galaga.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.github.grhscompsci2.galaga.AssetManager;
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.backgroundFPS = 10;
		config.foregroundFPS = 30;
		config.height = 244;
		config.initialBackgroundColor = Color.BLACK;
		config.resizable = false;
		config.title = "Galaga";
		config.vSyncEnabled = true;
		config.width = 288;
		new LwjglApplication(new AssetManager(), config);
	}
}
