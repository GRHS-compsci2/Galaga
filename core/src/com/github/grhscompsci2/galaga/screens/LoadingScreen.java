package com.github.grhscompsci2.galaga.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;

public class LoadingScreen extends ScreenAdapter {
    private MyGdxGame parent;
    private Music shotFired;
    private Music galagaExplosion1;
    private Music galagaExplosion2;
    private Music galagaAttack;
    private int currentLoadingStage=0;

    public final int IMAGE=0;
    public final int FONT=1;
    public final int PARTY=2;
    public final int SOUND=3;
    public final int MUSIC=4;

    public LoadingScreen(MyGdxGame myGdxGame) {
        parent = myGdxGame;
        shotFired = Utility.getMusicAsset(Utility.shotFired);
        galagaExplosion1 = Utility.getMusicAsset(Utility.galagaExplosion1);
        galagaExplosion2 = Utility.getMusicAsset(Utility.galagaExplosion2);
        galagaAttack = Utility.getMusicAsset(Utility.galagaAttack);
    }
    
    public void show() {
        Utility.loadTextureAtlasAsset();
    }

    public void create() {

    }
    
    public void render() {
        if(Utility._assetManager.update()){
            currentLoadingStage+=1;
                switch (currentLoadingStage) {
                    case FONT:
                    System.out.println("Loading Fonts");
                    //Utility._assetManager.queueAddFonts();







                }
        }
    }
}
