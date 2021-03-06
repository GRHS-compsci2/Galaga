package com.github.grhscompsci2.galaga.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.MyGdxGame.ScreenType;

public class LoadingScreen extends ScreenAdapter {

    private Stage _stage;
    private MyGdxGame parent;

    public LoadingScreen(MyGdxGame game) {
        parent = game;
        _stage = new Stage(new FitViewport(Utility.SCREEN_WIDTH, Utility.SCREEN_HEIGHT, new OrthographicCamera()));
    }

    @Override
    public void render(float delta) {
        if (!Utility._assetManager.update()) {

        } else {
            parent.setScreen(ScreenType.Menu);
        }
    }

    @Override
    public void show() {
        Utility.loadAllMusicAsset();
        Utility.loadAllSoundAsset();
        Utility.loadTextureAtlasAsset();
    }

    @Override
    public void dispose() {
        _stage.dispose();
    }
}
