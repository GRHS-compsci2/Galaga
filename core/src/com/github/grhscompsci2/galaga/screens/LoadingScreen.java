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

    public LoadingScreen(MyGdxGame myGdxGame) {
        parent = myGdxGame;
        /*shotFired = Utility.getMusicAsset(Utility.shotFired);
        galagaExplosion1 = Utility.getMusicAsset(Utility.galagaExplosion1);
        galagaExplosion2 = Utility.getMusicAsset(Utility.galagaExplosion2);
        galagaAttack = Utility.getMusicAsset(Utility.galagaAttack);*/
    }

}
