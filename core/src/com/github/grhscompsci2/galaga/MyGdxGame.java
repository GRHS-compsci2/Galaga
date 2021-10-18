package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.github.grhscompsci2.galaga.screens.ArcadeScreen;
import com.github.grhscompsci2.galaga.screens.LoadingScreen;
import com.github.grhscompsci2.galaga.screens.MenuScreen;
import com.github.grhscompsci2.galaga.screens.PreferencesScreen;

public class MyGdxGame extends Game{
    private static ArcadeScreen arcadeScreen;
    private static PreferencesScreen preferencesScreen;
    private static LoadingScreen loadingScreen;
	private static MenuScreen menuScreen;

	public static enum ScreenType{
		Arcade,
		Preferences,
		Loading,
		Menu
	}

	public Screen getScreenType(ScreenType screenType){
		switch(screenType){
			case Arcade:
				return arcadeScreen;
			case Preferences:
				return preferencesScreen;
			case Loading:
				return loadingScreen;
			default:
				return menuScreen;
		}

	}

	@Override
	public void create(){
        arcadeScreen=new ArcadeScreen(this);
        preferencesScreen=new PreferencesScreen(this);
        loadingScreen=new LoadingScreen(this);
		menuScreen=new MenuScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void dispose(){
		arcadeScreen.dispose();
        preferencesScreen.dispose();
        loadingScreen.dispose();
		menuScreen.dispose();
	}

}