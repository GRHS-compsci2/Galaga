package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.github.grhscompsci2.galaga.screens.ArcadeScreen;
import com.github.grhscompsci2.galaga.screens.LoadingScreen;
import com.github.grhscompsci2.galaga.screens.PreferencesScreen;

public class MyGdxGame extends Game{
    private static ArcadeScreen arcadeScreen;
    private static PreferencesScreen preferencesScreen;
    private static LoadingScreen loadingScreen;

	public static enum ScreenType{
		Arcade,
		Preferences,
		Loading
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
				return arcadeScreen;
		}

	}

	@Override
	public void create(){
        arcadeScreen=new ArcadeScreen(this);
        preferencesScreen=new PreferencesScreen(this);
        loadingScreen=new LoadingScreen(this);
		setScreen(arcadeScreen);
	}

	@Override
	public void dispose(){
		arcadeScreen.dispose();
        preferencesScreen.dispose();
        loadingScreen.dispose();
	}

}