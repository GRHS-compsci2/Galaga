package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.github.grhscompsci2.galaga.screens.ArcadeScreen;
import com.github.grhscompsci2.galaga.screens.LoadingScreen;
import com.github.grhscompsci2.galaga.screens.MenuScreen;
import com.github.grhscompsci2.galaga.screens.PauseScreen;
import com.github.grhscompsci2.galaga.screens.PreferencesScreen;

public class MyGdxGame extends Game {
	private static ArcadeScreen arcadeScreen;
	private static PreferencesScreen preferencesScreen;
	private static LoadingScreen loadingScreen;
	private static MenuScreen menuScreen;
	private static PauseScreen pauseScreen;
	private AppPreferences pref;
	private static final String TAG = MyGdxGame.class.getSimpleName();

	public static enum ScreenType {
		Arcade, Preferences, Loading, Menu, Pause
	}

	private ScreenType lastScreen;
	private ScreenType currentScreen;

	public Screen getScreenType(ScreenType screenType) {
		switch (screenType) {
			case Arcade:
				return arcadeScreen;
			case Preferences:
				return preferencesScreen;
			case Loading:
				return loadingScreen;
			case Pause:
				return pauseScreen;
			default:
				return menuScreen;
		}

	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Utility.loadTextureAtlasAsset();
		while (!Utility._assetManager.update()) {
		}
		pref = new AppPreferences();
		arcadeScreen = new ArcadeScreen(this);
		preferencesScreen = new PreferencesScreen(this);
		loadingScreen = new LoadingScreen(this);
		menuScreen = new MenuScreen(this);
		pauseScreen = new PauseScreen(this);
		setScreen(ScreenType.Pause);
	}

	@Override
	public void dispose() {
		arcadeScreen.dispose();
		preferencesScreen.dispose();
		loadingScreen.dispose();
		pauseScreen.dispose();
		menuScreen.dispose();
	}

	public AppPreferences getPreferences() {
		return pref;
	}

	public void setScreen(ScreenType st) {
		lastScreen = currentScreen;
		currentScreen = st;
		if (lastScreen == null)
			lastScreen = currentScreen;
		Gdx.app.debug(TAG, lastScreen + " " + currentScreen);
		setScreen(getScreenType(st));
	}

	public ScreenType getlastScreen() {
		return lastScreen;
	}
}