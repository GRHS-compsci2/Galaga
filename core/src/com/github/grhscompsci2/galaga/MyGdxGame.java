package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Gdx;
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
	private AppPreferences pref;

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
				return menuScreen;
		}

	}

	@Override
	public void create(){
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
        arcadeScreen=new ArcadeScreen(this);
        preferencesScreen=new PreferencesScreen(this);
        loadingScreen=new LoadingScreen(this);
		Utility.loadTextureAtlasAsset();
		while(!Utility._assetManager.update()){
			System.out.println(Utility._assetManager.getQueuedAssets()+"|");
		}

		setScreen(arcadeScreen);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public AppPreferences getPreferences() {
		return pref;
	}

}
